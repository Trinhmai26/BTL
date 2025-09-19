<img width="1238" height="877" alt="image" src="https://github.com/user-attachments/assets/a1eb2c99-8fd3-4bdc-816c-21aed30511f4" /><h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>
<h2 align="center">
   NETWORK PROGRAMMING
</h2>
<div align="center">
    <p align="center">
        <img src="docs/aiotlab_logo.png" alt="AIoTLab Logo" width="170"/>
        <img src="docs/fitdnu_logo.png" alt="AIoTLab Logo" width="180"/>
        <img src="docs/dnu_logo.png" alt="DaiNam University Logo" width="200"/>
    </p>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)

<h1 align="center">HỆ THỐNG ĐĂNG NHẬP CLIENT SERVER </h1>
</div>


## 📖 1. Giới thiệu

Hệ thống đăng nhập Client-Server là một mô hình phần mềm cho phép quản lý và xác thực người dùng thông qua giao thức TCP. Với cơ chế này, hệ thống đảm bảo khả năng truyền thông ổn định, an toàn và đáng tin cậy trong các hoạt động đăng ký, đăng nhập và quản lý tài khoản.

Trong kiến trúc này:  

- **Server**: Chịu trách nhiệm xử lý logic nghiệp vụ, quản lý cơ sở dữ liệu và duy trì tính bảo mật.  
- **Client**: Cung cấp giao diện người dùng trực quan, hỗ trợ thao tác dễ dàng và thuận tiện.  

### 📊 Mục tiêu của đề tài

- Xây dựng hệ thống đăng nhập client–server bằng Java sử dụng giao thức TCP/IP.
- Cho phép nhiều client kết nối đồng thời, gửi thông tin đăng nhập tới server.
- Server xử lý, kiểm tra dữ liệu trong cơ sở dữ liệu và phản hồi kết quả.
- Củng cố kiến thức về lập trình mạng, đa luồng và kết nối cơ sở dữ liệu trong Java.
- Tạo nền tảng để mở rộng vớBoadcast

## 🖼️ 3. Hình ảnh chức năng
<p align="center">
  <img src="https://github.com/user-attachments/assets/efa7ae7c-7b8a-47d3-a3ae-ad32414ef0cd"/>
</p>


<p align="center">
  <em>Hình 1: Giao diện đăng nhập </em>
</p>

3.1. Giao diện User
<p align="center">
 src="https://github.com/user-attachments/assets/c5e72b93-c202-4894-9223-dd01311deeae" />
<p align="center">
  <em>Hình 2: Giao diện Dashboard </em>
</p>
<p align="center">
 src="https://github.com/user-attachments/assets/6f304ba0-25d0-4550-baa5-4592b528f1bb" />

<p align="center">
  <em>Hình 3: Giao diện User </em>
</p>
<p align="center">
src="https://github.com/user-attachments/assets/a18a21a6-a0f7-4106-a94e-feaf6f434058" />
<p align="center">
  <em>Hình 4: Giao diện Lgin history </em>
</p>
<p align="center">
src="https://github.com/user-attachments/assets/743960ac-5bbe-49a3-a7af-8cda83a33a58" />

<p align="center">
  <em>Hình 5: Giao diện Lgin Broadcast </em>
</p>
3.2. Giao diện Admin
<p align="center">
src="https://github.com/user-attachments/assets/110c15c2-ba81-4224-bd12-58f89ae3d72d" />

<p align="center">
  <em>Hình 6: Giao diện đăng kí </em>
</p>
<p align="center">
src="https://github.com/user-attachments/assets/0dda6f9e-802d-42e7-a90a-8980ba6396ac" />
<p align="center">
  <em>Hình 7: Giao diện Thông tin cá nhân </em>
</p>
<p align="center">
 src="https://github.com/user-attachments/assets/9d954d31-d772-47b4-bfcb-fc97d196a8a0" />
<p align="center">
  <em>Hình 8: Giao diện Lịch sử đăng nhập </em>
</p>
<p align="center">
 src="https://github.com/user-attachments/assets/363c9d35-a329-4211-b496-6a3da5446921" />
<p align="center">
  <em>Hình 9: Giao diện tải file </em>
</p>
</p>
<p align="center">
src="https://github.com/user-attachments/assets/c2fff05e-bfae-43b3-9b45-bc2fd3b4b54c" />

<p align="center">
  <em>Hình 9: Giao diện tải file </em>
</p>







## ⚙️ 4. Các bước cài đặt


### 🔹 Bước 1: Chuẩn bị môi trường  
- Cài đặt **Java Development Kit (JDK 8 trở lên)**  
  - Tải tại: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html) hoặc [OpenJDK](https://jdk.java.net/)  
  - Kiểm tra cài đặt:  
    ```bash
    java -version
    javac -version
    ```  

- Cài đặt một IDE hỗ trợ Java (khuyến nghị):  
  - [IntelliJ IDEA](https://www.jetbrains.com/idea/)  
  - [Eclipse](https://www.eclipse.org/)  
  - [NetBeans](https://netbeans.apache.org/)  

- Cài đặt MySQL

---

### 🔹 Bước 2: Lưu trữ dữ liệu trong MySQL
Khởi tạo CSDL: Tạo cơ sở dữ liệu MySQL để quản lý thông tin đăng nhập.
Thiết kế bảng: Xây dựng bảng users với các cột: username, password, email, fullname, role, status, createdAt…
Lưu trữ dữ liệu: Mỗi tài khoản là một bản ghi trong bảng.
K3:Chạy hệ thống
```
    Chạy ServerMain
```
```
    Chạy ClientApp
```
### 🔹 Bước 5:Kiểm Thử
- Đăng ký: tạo tài khoản mới → tự động ghi vào MySQL

- Đăng nhập: kiểm tra tài khoản → trạng thái chuyển off → onl.

- Đăng xuất: hệ thống cập nhật lại trạng thái onl → off.
  
- Admin: Bảng điều khiển (Dashboard) tóm tắt: số lượng user, số user đang online, số lần đăng nhập thất bại…
## 📞5. Liên hệ
Nếu bạn có bất kỳ thắc mắc hoặc cần hỗ trợ về dự án **Hệ Thống Đăng Nhập Client-Server**, vui lòng liên hệ:  

- 👨‍🎓 **Sinh viên thực hiện**: Trịnh Thị Yến Mai 
- 🎓 **Khoa**: Công nghệ thông tin – Đại học Đại Nam  
- 📧 **Email**: trinhyenmai26@@gmail.com








