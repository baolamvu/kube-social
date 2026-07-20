# Kube Social - Technical & Business Glossary

| Thuật ngữ | Định nghĩa nghiệp vụ | Lưu ý kỹ thuật |
| :--- | :--- | :--- |
| **Group Feed** | Bảng tin chung của nhóm, chứa ảnh/video ngắn | cache tối ưu |
| **Group Story** | Video/Ảnh ngắn hiển thị trong 24 giờ của nhóm | Tự động hủy/ẩn sau 24h (TTL Index) |
| **Group Quen** | Mối quan hệ liên kết song phương giữa 2 nhóm | Graph database hoặc bảng liên kết Many-to-Many |
| **Mute Content** | Trạng thái bị chặn quyền đăng content/story | Cần lưu `muted_until` (timestamp) trong DB |
| **Group Vote** | Cuộc biểu quyết nội bộ (Cấm đăng bài, Kết giao nhóm) | Cần Real-time cập nhật tỉ lệ %, giới hạn thời gian vote |
