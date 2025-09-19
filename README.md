<h2 align="center">
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
 <img src="https://github.com/user-attachments/assets/393881c9-8b2c-4084-aaa4-b1c0d9e188b0" />

</p>


<p align="center">
  <em>Hình 1: Giao diện đăng nhập </em>
</p>

3.1. Giao diện User
<p align="center">
<img src="https://github.com/user-attachments/assets/ee52dd65-8c54-4091-b63a-761d809fc2da" />

<p align="center">
  <em>Hình 2: Giao diện Dashboard </em>
</p>
<p align="center">
  
<img src="https://github.com/user-attachments/assets/1c99b6d3-0ff7-4500-9454-8579771f5535" />
<p align="center">
  <em>Hình 3: Giao diện User </em>
</p>
<p align="center">
<img src="https://github.com/user-attachments/assets/2002da79-cadd-427b-831a-3da85740f2b2" />

<p align="center">
  <em>Hình 4: Giao diện Lgin history </em>
</p>
<p align="center">

<p align="center">
  <em>Hình 5: Giao diện Lgin Broadcast </em>
</p>
3.2. Giao diện Admin
<p align="center">
<img src="https://github.com/user-attachments/assets/68bb77db-bf29-400c-8176-1588610ffbaf" />

<p align="center">
  <em>Hình 6: Giao diện đăng kí </em>
</p>
<p align="center">
<img src="https://github.com/user-attachments/assets/aba940dd-1908-48b6-8587-c91ad614bedc" />

<p align="center">
  <em>Hình 7: Giao diện Thông tin cá nhân </em>
</p>
<p align="center">
 <img  src="https://github.com/user-attachments/assets/20fe375a-dc62-472a-8472-07e9e4d60150" />

<p align="center">
  <em>Hình 8: Giao diện Lịch sử đăng nhập </em>
</p>
<p align="center">
  <img src="https://github.com/user-attachments/assets/83ce3292-c27f-466e-bf4d-19391ef52e94" />

  <em>Hình 9: Giao diện tải file </em>
</p>
</p>
<p align="center">
 <img src="https://github.com/user-attachments/assets/1a27e741-847a-4b58-9b57-fa6f62575e6e" />

<p align="center">
  <em>Hình 10: Giao diện tải file </em>
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








