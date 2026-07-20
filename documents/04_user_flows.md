# Kube Social — User Flows

## 1. Đăng nội dung Feed với Privacy Mode
```mermaid
flowchart TD
    A[Member/Admin mở màn hình đăng bài] --> B{Chọn Privacy Mode}
    B -->|Private mặc định| C[Chỉ member trong group xem]
    B -->|Public| D[Group Quen xem được]
    B -->|Public + Except| E[Chọn group Quen loại trừ]
    C & D & E --> F[Submit -> lưu group_feed]
    F --> G{User bị mute?}
    G -->|Có, muted_until > now| H[Chặn đăng, hiện thời gian hết mute]
    G -->|Không| I[Đăng thành công, cache invalidate]
```

## 2. Group Story (TTL 24h)
```mermaid
flowchart TD
    A[User đăng Story] --> B[Lưu media + expires_at = now+24h]
    B --> C[Hiển thị trong group]
    C --> D{expires_at đã qua?}
    D -->|Rồi| E[Tự ẩn/xóa - TTL job/Redis]
    D -->|Chưa| C
    F[User khác comment Story] --> G[Comment route vào Group Chat, không phải comment trực tiếp trên story]
```

## 3. Vote cấm đăng bài (bảo vệ Member trước Admin)
```mermaid
flowchart TD
    A[Bất kỳ Member mở vote cấm đăng bài] --> B[Chọn target: bất kỳ ai kể cả Admin]
    B --> C[Set thời gian cấm]
    C --> D[Broadcast vote realtime cho toàn group]
    D --> E[Member vote Agree/Disagree]
    E --> F{Kết thúc vote: Agree > 50%?}
    F -->|Có| G[target.muted_until = now + duration -> áp dụng ngay]
    F -->|Không| H[Vote thất bại, không áp dụng]
```

## 4. Group Connection ("Group Quen")
```mermaid
flowchart TD
    A[Admin nhóm A bấm Kết bạn nhóm B] --> B[Tạo group_connection status=PENDING]
    B --> C[Nhóm B tự động mở Group Vote nội bộ, type=GROUP_CONNECTION]
    C --> D[Member nhóm B vote Agree/Disagree]
    D --> E{Agree >= 80% tổng Member?}
    E -->|Có| F[status=ACTIVE -> 2 nhóm thấy nội dung Public của nhau]
    E -->|Không| G[status=REJECTED]
```

## 5. Only Privacy Group Mode
```mermaid
flowchart TD
    A[Admin set Group = ONLY_PRIVACY] --> B[Group ẩn khỏi tìm kiếm/discovery]
    B --> C[Không ai gửi được connection-request trực tiếp]
    C --> D[Chỉ member trong group thấy friend_request_link trong Group Profile]
    D --> E[Member share link ra ngoài cho người/nhóm mong muốn]
    E --> F[Người nhận link -> request join hoặc request kết Quen qua link]
```

## 6. Phân quyền xóa content (Edge-case quan trọng)
```mermaid
flowchart TD
    A[User bấm Xóa Feed/Story] --> B{User = tác giả content?}
    B -->|Có| C[Cho phép xóa]
    B -->|Không, User = Admin| D{Tác giả đã rời group?}
    D -->|Đã out group| C
    D -->|Vẫn còn trong group| E[Từ chối - Admin không được xóa content của member đang active]
    B -->|Không, User = Member thường| F[Từ chối]
```
