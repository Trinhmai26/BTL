<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Khoa Công nghệ Thông tin - Đại học Đại Nam
    </a>
</h2>

<div align="center">

<img width="170" alt="AIoTLab Logo" src="https://github.com/user-attachments/assets/41ef702b-3d6e-4ac4-beac-d8c9a874bca9"/>
<img width="180" alt="FIT DNU Logo" src="https://github.com/user-attachments/assets/ec4815af-e477-480b-b9fa-c490b74772b8"/>
<img width="200" alt="DNU Logo" src="https://github.com/user-attachments/assets/2bcb1a6c-774c-4e7d-b14d-8c53dbb4067f"/>

<br><br>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-28a745?style=for-the-badge&logo=facebook&logoColor=white)](https://www.facebook.com/DNUAIoTLab)
[![Khoa CNTT](https://img.shields.io/badge/Khoa_Công_nghệ_Thông_tin-0066cc?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![Đại học Đại Nam](https://img.shields.io/badge/Đại_học_Đại_Nam-ff6200?style=for-the-badge)](https://dainam.edu.vn)

</div>

# <center>🖥️ HỆ THỐNG ĐĂNG NHẬP CLIENT-SERVER (Java TCP + MySQL + Swing)</center>

<div align="center">
  <strong>Đồ án môn Lập trình mạng</strong><br>
  Sinh viên thực hiện: <strong>Trịnh Thị Yến Mai</strong><br>
  Khoa Công nghệ Thông tin – Đại học Đại Nam
</div>

---

## 📖 1. Giới thiệu đề tài

Trong thời đại số hóa hiện nay, **hệ thống xác thực người dùng (Login System)** là thành phần không thể thiếu trong hầu hết các ứng dụng mạng. Đồ án này xây dựng một **hệ thống đăng nhập Client-Server hoàn chỉnh** sử dụng giao thức **TCP/IP**, ngôn ngữ **Java**, kết hợp cơ sở dữ liệu **MySQL** và giao diện **Java Swing**.

### 🎯 Mục tiêu chính
- Xây dựng hệ thống đăng nhập/đăng ký Client-Server sử dụng giao thức TCP.
- Hỗ trợ **đa kết nối đồng thời** nhờ cơ chế đa luồng (Multithreading).
- Phân quyền rõ ràng: **Admin** và **User**.
- Quản lý tài khoản tập trung qua **MySQL**.
- Ghi lại lịch sử đăng nhập và hoạt động của người dùng.
- Tạo nền tảng để mở rộng thành các hệ thống lớn hơn (chat, quản lý, broadcast...).

### 🏗️ Kiến trúc hệ thống## 🔧 2. Công nghệ sử dụng

| Thành phần               | Công nghệ sử dụng                 |
|--------------------------|-----------------------------------|
| Ngôn ngữ lập trình       | Java SE 17+                       |
| Giao diện người dùng     | Java Swing                        |
| Giao thức mạng           | TCP/IP Socket                     |
| Đa luồng                 | `Thread`, `ExecutorService`       |
| Cơ sở dữ liệu            | MySQL + JDBC                      |
| Mã hóa mật khẩu          | BCrypt / SHA-256 (tùy chọn)       |
| IDE khuyến nghị          | IntelliJ IDEA, NetBeans, Eclipse  |

---
