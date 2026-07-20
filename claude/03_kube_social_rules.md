# Kube Social - Development & System Rules

## 1. Định Hướng Kiến Trúc & Công Nghệ (Java Ecosystem)
- **Frontend:** Cross-platform (Flutter hoặc React Native).
- **Backend:** Java 17+ kết hợp với **Spring Boot** làm framework chủ đạo.
- **Real-time Engine:** Spring WebFlux / WebSockets phục vụ Chat và Voting thời gian thực.
- **Database:** PostgreSQL (Quan hệ chặt chẽ giữa User, Group, Member) và Redis (Caching, Session, Real-time queue).

## 2. Chiến Lược DevOps & Triển Khai (Container & CI/CD)
- **Containerization:** Toàn bộ dịch vụ (Backend, DB, Redis) phải được Docker hóa. Sử dụng Multi-stage build cho Java Dockerfile để tối ưu dung lượng Image.
- **CI/CD Pipeline:** Sử dụng **GitHub Actions** để tự động hóa quy trình. Luồng hoạt động: Mỗi khi có code commit/merge vào branch `main` -> Tự động chạy Unit Test -> Build Docker Image -> Push lên Docker Registry -> Tự động SSH vào Server để kéo Image mới về và deploy (Rolling update/Restart container).
- **Môi trường:** Docker Compose phục vụ Local Dev và Staging.

## 3. Quy Chuẩn Tài Liệu & Code Kiến Trúc
- Sơ đồ CI/CD, Luồng phân phối hạ tầng BẮT BUỘC phải viết bằng cú pháp **Mermaid.js**.
- Mã cấu hình (Dockerfile, docker-compose.yml, GitHub Workflow .github/workflows/*.yml) phải được viết sạch, phân tách rõ ràng giữa biến môi trường (Environment Variables) và mã nguồn cứng (Hardcoded values).
