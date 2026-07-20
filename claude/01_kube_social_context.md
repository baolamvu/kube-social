# Kube Social - Project Context & Core Specs

## 1. Tổng Quan Dự Án
- **Tên dự án:** Kube Social
- **Loại hình:** Ứng dụng mạng xã hội di động (Mobile App).
- **Triết lý cốt lõi:** Định hướng lấy Nhóm làm trung tâm (Group-centric). Không có bảng tin cá nhân (User Feed/Story), mọi nội dung đều thuộc sở hữu và phân phối bên trong hoặc giữa các Nhóm.

## 2. Các Thực Thể Chính (Core Entities)
- **User (Người dùng):** Đăng ký tài khoản, có thể tạo nhóm, tham gia hoặc rời nhóm theo lời mời. Không có tường nhà riêng.
- **Group (Nhóm):** Thực thể tối cao chứa nội dung. Gồm 2 luồng hiển thị: Group Feed (ảnh, video ngắn) và Group Story (Nội dung biến mất sau 24h).

## 3. Phân Quyền Trong Nhóm (Role Management)
- **Admin (Chủ nhóm):** Người tạo nhóm. Có quyền tối cao: mời, đuổi thành viên, cấm đăng bài tạm thời/vĩnh viễn, gửi lời mời kết nối với nhóm khác. Không có quyền xóa story hoặc content feed của user khác đăng lên, trừ khi user đó đã out group. Admin được quyền sửa, xem group profile.
- **Member (Thành viên):** Chỉ có quyền đăng nội dung (Feed/Story) vào nhóm, tương tác như thả emoji đối với các story, feed content không phải do user post lên, comment với content trên feed, comment story vào group chat đối với các content không phải do user post lên, chat trong Group Chat và tham gia biểu quyết (Voting). Chỉ có quyển xóa các story hoặc content trên feed do user đó đăng lên. Member chỉ được xem group profile.

## 4. Cơ Chế Quản Trị Phi Tập Trung (Governance & Voting)
- **Bảo vệ Member trước Admin:** Thành viên có quyền mở cuộc Vote cấm đăng bài tạm thời đối với bất kỳ ai (kể cả Admin). Nếu số phiếu đồng ý > 50%, lệnh cấm có hiệu lực trong thời gian chỉ định.
- **Mối quan hệ giữa các Nhóm (Group Connection):**
  - Nhóm A và Nhóm B có thể trở thành "Group Quen" (Friend Groups).
  - Admin phát lệnh kết bạn -> Nhóm nhận lệnh phải mở cuộc Vote trong nội bộ -> Phải đạt tối thiểu 80% Member đồng ý thì kết nối mới thành công.

## 5. Cơ Chế Hiển Thị & Quyền Riêng Tư (Privacy Modes)
- **Posting Private Mode(Mặc định):** Content được post sẽ chỉ thành viên trong nhóm mới xem và tương tác được.
- **Posting Public Mode:** Các nhóm "Quen" có thể nhìn thấy và tương tác nội dung của nhau.
- **Posting Exception Mode:** Khi đăng Public, có thể chọn loại trừ (Except) một hoặc vài nhóm "Quen" cụ thể không cho xem.
- **Mode Adjusting:** Có thể mass changing mode cho các content đã được đăng trên feed bằng cách tick chọn tất cả, hoặc chọn những content admin muốn chỉ định.
- **Only Privacy Group Mode:** Các group chỉ có 1 chế độ privacy. Nếu ko phải group quen hoặc member của group, không ai có thể xem story và feed của group đó. Không user nào có thể tìm được group đó để gửi request làm group quen. Mỗi group sẽ có 1 friend request link, và chỉ có member của group mới thấy được link đó trong group profile.