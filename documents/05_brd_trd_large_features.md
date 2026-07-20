# Kube Social — BRD/TRD: Large Features

## Feature 1: Governance & Voting System

### BRD
| Mục | Nội dung |
|---|---|
| Mục tiêu | Trao quyền phản biện cho Member, kể cả với Admin, để tránh lạm quyền |
| User Story | "Là Member, tôi muốn mở vote cấm đăng bài với bất kỳ ai (kể cả Admin) khi họ vi phạm quy tắc nhóm" |
| Điều kiện thắng | BAN_MEMBER: Agree > 50% member. GROUP_CONNECTION: Agree ≥ 80% member |
| Edge-case | Member rời group giữa lúc đang vote → loại phiếu khỏi mẫu số tính %; Vote hết hạn mà chưa đủ % tối thiểu tham gia → tính trên số đã vote hay toàn bộ member? **Cần quyết định business** (đề xuất: tính trên tổng member hiện tại, không vote = Disagree ngầm định) |
| Edge-case | Admin duy nhất bị mute vĩnh viễn → nhóm không còn ai quản trị? Cần rule: Admin không thể tự mute chính mình qua vote, hoặc nhóm cần tối thiểu 1 Admin luôn active |

### TRD
- Thực thể: `group_vote`, `vote_ballot` (chi tiết ở file 02_database_schema.md).
- Tính % realtime: `VoteCalculationService` — mỗi lần có ballot mới, publish Redis channel `vote:{voteId}`, WS handler push xuống client %.
- Kết thúc vote: scheduled job (Spring `@Scheduled` hoặc Redis TTL trigger) tại `end_at` → gọi `VoteService.finalize(voteId)` → cập nhật `status`, side-effect (mute user / activate connection).
- Idempotent: `unique(vote_id, user_id)` chặn double vote.

---

## Feature 2: Group Connection ("Group Quen")

### BRD
| Mục | Nội dung |
|---|---|
| Mục tiêu | Tạo mạng lưới liên kết giữa các nhóm để mở rộng phân phối nội dung Public |
| User Story | "Là Admin, tôi muốn gửi lời mời kết bạn tới nhóm khác, và nhóm đó phải đồng thuận nội bộ trước khi kết nối" |
| Điều kiện | Nhóm nhận phải đạt ≥80% Member đồng ý (không phải Admin quyết một mình) |
| Edge-case | Nhóm ONLY_PRIVACY không thể bị gửi connection-request trực tiếp, chỉ qua `friend_request_link` |
| Edge-case | Hủy kết Quen: cần thêm flow (chưa có trong spec gốc) — đề xuất Admin 1 trong 2 bên có thể unlink, cần xác nhận business |

### TRD
- Thực thể: `group_connection` (status PENDING/ACTIVE/REJECTED).
- Trigger: `GroupConnectionService.requestConnection()` tạo record + tự động gọi `VoteService.createVote(type=GROUP_CONNECTION)`.
- Query hiệu năng: khi render Feed Public cho user, cần join `group_connection where status=ACTIVE and (group_a=X or group_b=X)` — cache danh sách connected_group_ids theo group vào Redis, invalidate khi connection thay đổi.

---

## Feature 3: Privacy & Visibility Engine

### BRD
| Mục | Nội dung |
|---|---|
| Mục tiêu | Cho phép Admin kiểm soát chi tiết ai xem được nội dung group |
| Modes | Private (default), Public (cho Group Quen), Public+Except, Only Privacy Group |
| User Story | "Là Admin, tôi muốn đăng Public nhưng loại trừ 1 vài nhóm Quen cụ thể không cho xem" |
| User Story | "Là Admin, tôi muốn mass-change privacy mode cho nhiều content đã đăng cùng lúc" |
| Edge-case | Group đổi từ PUBLIC sang ONLY_PRIVACY → các connection đang ACTIVE có tự hủy không? Cần quyết định (đề xuất: giữ connection nhưng ẩn hoàn toàn nội dung, không cho tạo connection mới) |

### TRD
- `FeedVisibilityService.canView(viewerGroupId, feedId)`:
  1. Nếu `feed.visibility_mode = PRIVATE` → chỉ member cùng `group_id`.
  2. Nếu `PUBLIC` → member cùng group HOẶC `group_connection ACTIVE` giữa 2 group.
  3. Nếu `EXCEPT` → như PUBLIC nhưng loại trừ `feed_exception_group`.
  4. Nếu `group.privacy_mode = ONLY_PRIVACY` → bỏ qua toàn bộ rule trên, luôn trả `false` cho viewer ngoài group.
- Mass update: API nhận `feed_id[]` + `new_mode` → batch update, invalidate cache theo `group_id`.
