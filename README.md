Skip to content
Navigation Menu
Trinhmai26
BTL

Type / to search
Code
Issues
Pull requests
Actions
Projects
Wiki
Security
Insights
Settings
BTL
/
README.md
in
main

Edit

Preview
Indent mode

Spaces
Indent size

4
Line wrap mode

Soft wrap
Editing README.md file contents
Selection deleted
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
33
34
35
36
37
38
39
40
41
42
43
44
45
46
47
48
49
50
51
52
53
54
55
56
57
58
59
60
61
62
63
64
65
66
67
68
69
70
71
72
73
74
75
76
77
78
79
80
81
82
<h2 align="center">
    <a href="https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin">
    🎓 Faculty of Information Technology (DaiNam University)
    </a>
</h2>


<div align="center">
    <p align="center">
        <img width="170" alt="aiotlab_logo" src="https://github.com/user-attachments/assets/41ef702b-3d6e-4ac4-beac-d8c9a874bca9" />
        <img width="180" alt="fitdnu_logo" src="https://github.com/user-attachments/assets/ec4815af-e477-480b-b9fa-c490b74772b8" />
        <img width="200" alt="dnu_logo" src="https://github.com/user-attachments/assets/2bcb1a6c-774c-4e7d-b14d-8c53dbb4067f" />
    </p>

[![AIoTLab](https://img.shields.io/badge/AIoTLab-green?style=for-the-badge)](https://www.facebook.com/DNUAIoTLab)
[![Faculty of Information Technology](https://img.shields.io/badge/Faculty%20of%20Information%20Technology-blue?style=for-the-badge)](https://dainam.edu.vn/vi/khoa-cong-nghe-thong-tin)
[![DaiNam University](https://img.shields.io/badge/DaiNam%20University-orange?style=for-the-badge)](https://dainam.edu.vn)

</div>
<h1 align="center">HỆ THỐNG ĐĂNG NHẬP CLIENT SERVER </h1>
</div>

## 📖 1. Giới thiệu


### 📖 Giới thiệu đề tài

Trong thời đại công nghệ thông tin phát triển mạnh mẽ, việc xây dựng các ứng dụng mạng an toàn và hiệu quả ngày càng trở nên quan trọng. Một trong những chức năng cơ bản và phổ biến nhất trong các hệ thống phần mềm chính là **chức năng đăng nhập (Login System)**, nhằm xác thực và quản lý người dùng.

Đề tài **“Hệ thống đăng nhập Client–Server bằng giao thức TCP ngôn ngữ Java”** được xây dựng với mục tiêu mô phỏng quá trình giao tiếp giữa **client** và **server** thông qua giao thức **TCP/IP**. Trong đó:

* **Server** có nhiệm vụ quản lý kết nối, xử lý yêu cầu và xác thực tài khoản người dùng.
* **Client** đóng vai trò gửi thông tin đăng nhập (username, password) đến server để kiểm tra.
* **Cơ sở dữ liệu MySQL** được sử dụng để lưu trữ thông tin tài khoản, đảm bảo khả năng quản lý dữ liệu tập trung, an toàn và dễ mở rộng.

Hệ thống hỗ trợ **đa luồng**, cho phép nhiều client kết nối và làm việc đồng thời mà không ảnh hưởng đến hiệu năng. Đây là một ứng dụng thực tiễn giúp sinh viên nắm vững kiến thức về:

* lập trình mạng trong Java,
* giao thức TCP,
* cơ chế đa luồng (multithreading),
* và kỹ thuật kết nối cơ sở dữ liệu (JDBC – MySQL).


Trong kiến trúc này:  

- **Server**: Chịu trách nhiệm xử lý logic nghiệp vụ, quản lý cơ sở dữ liệu và duy trì tính bảo mật.  
- **Client**: Cung cấp giao diện người dùng trực quan, hỗ trợ thao tác dễ dàng và thuận tiện.  

### 📊 Mục tiêu của đề tài

- Xây dựng hệ thống đăng nhập client–server bằng Java sử dụng giao thức TCP/IP.
- Cho phép nhiều client kết nối đồng thời, gửi thông tin đăng nhập tới server.
- Server xử lý, kiểm tra dữ liệu trong cơ sở dữ liệu và phản hồi kết quả.
- Củng cố kiến thức về lập trình mạng, đa luồng và kết nối cơ sở dữ liệu trong Java.
- Tạo nền tảng để mở rộng vớBoadcast
## 🔧 2. Công nghệ sử dụng

### 🌐 Ngôn Ngữ Lập Trình
- **Java SE 17+**: Ngôn ngữ lập trình chính  
- Hỗ trợ lập trình hướng đối tượng, đa luồng, lập trình socket  

### 🎨 Giao Diện Người Dùng
- **Java Swing**: Xây dựng giao diện desktop  
- Các thành phần chính: `JFrame`, `JPanel`, `JButton`, `JTextField`, `JPasswordField`, `JTable`  
- Xử lý sự kiện: `ActionListener`, `MouseListener`  

### 🗄️ Lưu Trữ Dữ Liệu
- Sử dụng My SQL
- Chứa thông tin tài khoản (username, password, role, …)  
- Thao tác: đọc, ghi, cập nhật, xóa tài khoản bằng Java I/O  

### 🔄 Xử Lý Đa Luồng
- **Java Multithreading**: Cho phép nhiều client kết nối đồng thời  
- Thread riêng cho từng client để tránh xung đột  
- Đồng bộ hóa khi ghi/đọc dữ liệu từ MySQL

## 🖼️ 3. Hình ảnh chức năng
<p align="center">
 <img src="https://github.com/user-attachments/assets/393881c9-8b2c-4084-aaa4-b1c0d9e188b0" />

</p>

Use Control + Shift + m to toggle the tab key moving focus. Alternatively, use esc then tab to move to the next interactive element on the page.
No file chosen
Attach files by dragging & dropping, selecting or pasting them.
Editing BTL/README.md at main · Trinhmai26/BTL
