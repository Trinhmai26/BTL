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
- Tạo nền tảng để mở rộng với các chức năng như đăng ký tài khoản, phân quyền và bảo mật nâng cao.

## 🔧 2. Công nghệ sử dụng

### 🌐 Ngôn Ngữ Lập Trình
- **Java SE 17+**: Ngôn ngữ lập trình chính  
- Hỗ trợ lập trình hướng đối tượng, đa luồng, lập trình socket  

### 🎨 Giao Diện Người Dùng
- **Java Swing**: Xây dựng giao diện desktop  
- Các thành phần chính: `JFrame`, `JPanel`, `JButton`, `JTextField`, `JPasswordField`, `JTable`  
- Xử lý sự kiện: `ActionListener`, `MouseListener`  

### 🌐 Truyền Thông Mạng
- **Giao thức TCP/IP**: Truyền dữ liệu đáng tin cậy  
- `Socket` & `ServerSocket`: Kết nối client-server  
- Luồng đối tượng: `ObjectInputStream` & `ObjectOutputStream` để gửi/nhận dữ liệu  

### 🗄️ Lưu Trữ Dữ Liệu
- MySQL Workbench: Sử dụng hệ quản trị cơ sở dữ liệu MySQL để lưu trữ thông tin người dùng.
Bảng users chứa các thông tin tài khoản (username, password, role, ...).
Các thao tác chính: thêm, sửa, xóa, tìm kiếm, xác thực tài khoản thông qua Java JDBC kết nối MySQL.

### 🔄 Xử Lý Đa Luồng
Java Multithreading + ExecutorService: Cho phép nhiều client kết nối và hoạt động đồng thời.
Mỗi client được quản lý bởi một thread riêng (ClientHandler) trong Thread Pool, tránh lãng phí tài nguyên.
Sử dụng ConcurrentHashMap để lưu trữ danh sách client đang hoạt động, đảm bảo an toàn luồng khi truy cập dữ liệu.
Đồng bộ hóa khi ghi/đọc dữ liệu từ MySQL thông qua JDBC.
## 🖼️ 3. Hình ảnh chức năng
<p align="center">
  <img src="https://github.com/user-attachments/assets/d83e3f48-8c50-4da1-8c5d-6b1287ad1f62" />

</p>

<p align="center">
  <em>Hình 1: Giao diện đăng kí </em>
</p>
3.1. Giao diện của user
<p align="center">
      <img src="https://github.com/user-attachments/assets/7df41678-0213-4c9c-803e-5acb57233908" />

</p>
<p align="center">
  <em> Hình 2: Đăng kí tài khoản</em>
</p>


<p align="center">
       <img src="https://github.com/user-attachments/assets/7df41678-0213-4c9c-803e-5acb57233908" />

</p>
<p align="center">
  <em> Hình 3: Giao diện lịch sử đăng nhập
  <p align="center">
  <img src="https://github.com/user-attachments/assets/934e73ee-5364-42d9-a65c-f4c81a8e75a1" />
" width="600"/>
 
</p>
<p align="center">
  <em> Hình 3: Giao diện tải file
  <p align="center">
  <img src="https://github.com/user-attachments/assets/cfb535db-3d2f-4ad0-973a-2783c3942a57" /> 

</p>
<p align="center">
  <em> Hình 3: Giao diện hỗ trợ




