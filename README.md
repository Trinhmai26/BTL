<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>

<div align="center">
<img width="170" alt="aiotlab_logo" src="https://github.com/user-attachments/assets/41ef702b-3d6e-4ac4-beac-d8c9a874bca9" />
<img width="180" alt="fitdnu_logo" src="https://github.com/user-attachments/assets/ec4815af-e477-480b-b9fa-c490b74772b8" />
<img width="200" alt="dnu_logo" src="https://github.com/user-attachments/assets/2bcb1a6c-774c-4e7d-b14d-8c53dbb4067f" />

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)
</div>

# <div align="center">HỆ THỐNG ĐĂNG NHẬP CLIENT-SERVER</div>

---

## 📖 1. Giới thiệu

### 📝 Giới thiệu đề tài
Trong thời đại công nghệ thông tin phát triển mạnh mẽ, việc xây dựng các ứng dụng mạng an toàn và hiệu quả ngày càng quan trọng. Một trong những chức năng cơ bản là **hệ thống đăng nhập (Login System)**, nhằm xác thực và quản lý người dùng.

Đề tài **“Hệ thống đăng nhập Client–Server bằng giao thức TCP ngôn ngữ Java”** mô phỏng quá trình giao tiếp giữa **client** và **server** qua giao thức **TCP/IP**:

- **Server**: Quản lý kết nối, xử lý yêu cầu và xác thực tài khoản người dùng.  
- **Client**: Gửi thông tin đăng nhập (username, password) đến server để kiểm tra.  
- **Cơ sở dữ liệu MySQL**: Lưu trữ thông tin tài khoản, đảm bảo quản lý tập trung và bảo mật.  

Hệ thống hỗ trợ **đa luồng**, cho phép nhiều client kết nối đồng thời mà không ảnh hưởng hiệu năng. Đây là ứng dụng thực tiễn giúp sinh viên nắm vững:

- Lập trình mạng trong Java  
- Giao thức TCP  
- Cơ chế đa luồng (multithreading)  
- Kỹ thuật kết nối cơ sở dữ liệu (JDBC – MySQL)

---

### 🎯 Mục tiêu của đề tài
- Xây dựng hệ thống đăng nhập client–server bằng Java sử dụng TCP/IP  
- Cho phép nhiều client kết nối đồng thời  
- Server xử lý và kiểm tra dữ liệu trong MySQL  
- Củng cố kiến thức lập trình mạng, đa luồng, JDBC  
- Tạo nền tảng mở rộng ứng dụng (broadcast, phân quyền, …)

---

## 🔧 2. Công nghệ sử dụng

### 🌐 Ngôn ngữ lập trình
- **Java SE 17+**: Hướng đối tượng, đa luồng, socket

### 🎨 Giao diện
- **Java Swing**: Desktop UI  
- Thành phần chính: `JFrame`, `JPanel`, `JButton`, `JTextField`, `JPasswordField`, `JTable`  
- Xử lý sự kiện: `ActionListener`, `MouseListener`

### 🗄️ Lưu trữ dữ liệu
- **MySQL**: Thông tin tài khoản (username, password, role, …)  
- Thao tác CRUD qua JDBC  
- Mật khẩu được mã hóa, hạn chế truy cập trực tiếp

### 🔄 Đa luồng
- **Java Multithreading**: Thread riêng cho từng client  
- Đồng bộ hóa khi đọc/ghi dữ liệu

---

## 🖼️ 3. Hình ảnh chức năng

<div align="center">

**Giao diện Đăng nhập**  
<img width="524" height="730" src="https://github.com/user-attachments/assets/f93a2a07-cace-4e34-914c-6928d0bac685" />

**Giao diện Đăng kí**  
<img width="496" height="911" src="https://github.com/user-attachments/assets/a89e7a32-a6d7-4cfe-87e2-a224ce89eb1b" />

**Giao diện Admin – Dashboard**  
<img width="1920" height="1080" src="https://github.com/user-attachments/assets/d539e9e0-1805-483e-a903-149cd448ed5a" />

**Quản lý User**  
<img width="1920" height="1080" src="https://github.com/user-attachments/assets/2d573ca3-db32-4473-b572-8547864696cc" />

**Lịch sử đăng nhập Admin**  
<img width="1920" height="1080" src="https://github.com/user-attachments/assets/ea215a57-c5fe-472a-aae9-534d15c5c6cd" />

**Chi tiết hoạt động**  
<img width="1920" height="1080" src="https://github.com/user-attachments/assets/be6bc595-471c-41bc-8130-c573879b7b7b" />

**Giao diện User – Thông tin cá nhân**  
<img width="1920" height="1080" src="https://github.com/user-attachments/assets/0b0b04b7-da61-44b0-bd60-90d04c61081f" />

**Chỉnh sửa thông tin cá nhân**  
<img width="1920" height="1080" src="https://github.com/user-attachments/assets/a7d03c87-750a-48fa-a5bb-2aac95897ba4" />

**Lịch sử đăng nhập User**  
<img width="1920" height="1080" src="https://github.com/user-attachments/assets/3e62d41c-d590-4edb-9870-5fd3a7e6939a" />

</div>

---

## ⚙️ 4. Các bước cài đặt

### 🔹 Bước 1: Chuẩn bị môi trường
- Cài đặt **JDK 8+**: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) / [OpenJDK](https://jdk.java.net/)  
```bash
java -version
javac -version
IDE khuyên dùng: IntelliJ IDEA, Eclipse, NetBeans

File dữ liệu users.csv để lưu tài khoản

🔹 Bước 2: Lưu trữ dữ liệu MySQL
Tạo cơ sở dữ liệu và bảng users (username, password, email, fullname, role, status, createdAt)

Kết nối ứng dụng qua JDBC

CRUD: Thêm, đọc, sửa, xóa tài khoản

Mật khẩu mã hóa, hạn chế truy cập trực tiếp

🔹 Bước 3: Biên dịch source
bash
Sao chép mã
javac BTL/*.java
🔹 Bước 4: Chạy hệ thống
Chạy ServerMain

Chạy ClientApp

🔹 Bước 5: Kiểm thử
Kết nối TCP giữa client và server

Đăng nhập với các tài khoản khác nhau

Kiểm tra CRUD trên MySQL

Thử nhiều client đồng thời

Kiểm tra phân quyền admin/user và bảo mật

📞 5. Liên hệ
👨‍🎓 Sinh viên thực hiện: Trịnh Thị Yến Mai

🎓 Khoa: Công nghệ thông tin – Đại học Đại Nam

📧 Email: Trinhyenmai26@.com
