package com.loginSystem.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loginSystem.common.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private Gson gson;
    private User currentUser;

    // GUI
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Client() {
        this.gson = new Gson();
        applyLookAndFeel();
        connectToServer();
        setupGUI();
    }

    private void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
            UIManager.put("Button.arc", 15);   // bo góc nút
            UIManager.put("TextComponent.arc", 10);
            UIManager.put("TabbedPane.showTabSeparators", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connected to server");
            startListener();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Không thể kết nối tới server: " + e.getMessage(),
                    "Lỗi kết nối", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void setupGUI() {
        mainFrame = new JFrame("🔒 Hệ thống đăng nhập");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 700);
        mainFrame.setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createRegisterPanel(), "REGISTER");
        mainPanel.add(createUserPanel(), "USER");
        mainPanel.add(createAdminPanel(), "ADMIN");

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);

        cardLayout.show(mainPanel, "LOGIN");
    }

    // ---------------- LOGIN PANEL ----------------
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 247, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        JLabel titleLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(33, 33, 99));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 0, 20, 0);
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Tên đăng nhập:"), gbc);
        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Mật khẩu:"), gbc);
        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Đăng nhập");
        JButton registerButton = new JButton("Đăng ký");
        JButton exitButton = new JButton("Thoát");

        loginButton.setPreferredSize(new Dimension(120, 35));
        registerButton.setPreferredSize(new Dimension(120, 35));
        exitButton.setPreferredSize(new Dimension(120, 35));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(exitButton);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 10, 0);
        panel.add(buttonPanel, gbc);

        JLabel statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        gbc.gridy = 4;
        panel.add(statusLabel, gbc);

        // Action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            User loginUser = new User();
            loginUser.setUsername(username);
            loginUser.setPassword(password);

            Message request = new Message(RequestType.LOGIN, "");
            request.setUser(loginUser);

            Message response = sendRequest(request);
            if (response != null && response.isSuccess()) {
                currentUser = response.getUser();
                if (currentUser.isAdmin()) {
                    cardLayout.show(mainPanel, "ADMIN");
                } else {
                    cardLayout.show(mainPanel, "USER");
                }
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        response != null ? response.getError() : "Lỗi kết nối",
                        "Đăng nhập thất bại",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "REGISTER"));
        exitButton.addActionListener(e -> {
            cleanup();
            System.exit(0);
        });

        return panel;
    }

    // ---------------- REGISTER PANEL ----------------
    private JPanel createRegisterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 40, 20, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel titleLabel = new JLabel("ĐĂNG KÝ TÀI KHOẢN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField fullNameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        String[] labels = {"Tên đăng nhập:", "Mật khẩu:", "Họ tên:", "Số điện thoại:", "Email:"};
        JComponent[] fields = {usernameField, passwordField, fullNameField, phoneField, emailField};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = i + 1;
            panel.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            panel.add(fields[i], gbc);
        }

        JButton okButton = new JButton("Đăng ký");
        JButton backButton = new JButton("Quay lại");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        okButton.addActionListener(e -> {
            User newUser = new User(
                    usernameField.getText().trim(),
                    new String(passwordField.getPassword()),
                    fullNameField.getText().trim(),
                    phoneField.getText().trim(),
                    emailField.getText().trim()
            );
            Message request = new Message(RequestType.REGISTER, "");
            request.setUser(newUser);
            Message response = sendRequest(request);

            if (response != null && response.isSuccess()) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Đăng ký thành công!",
                        "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        response != null ? response.getError() : "Lỗi kết nối",
                        "Đăng ký thất bại", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));

        return panel;
    }

    // ---------------- USER PANEL ----------------
    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("👤 Thông tin cá nhân", createUserInfoTab());
        tabbedPane.addTab("📜 Lịch sử đăng nhập", createUserHistoryTab());
        tabbedPane.addTab("📂 Files", createUserFilesTab());
        tabbedPane.addTab("💬 Hỗ trợ", createSupportTab());

        panel.add(tabbedPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.addActionListener(e -> {
            sendRequest(new Message(RequestType.LOGOUT, ""));
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        panel.add(logoutButton, BorderLayout.SOUTH);

        return panel;
    }
    
    
    

    private JPanel createUserInfoTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JTextField nameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passField = new JPasswordField(20);

        panel.add(new JLabel("Họ tên:"), gbc); gbc.gridx = 1; panel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Điện thoại:"), gbc); gbc.gridx = 1; panel.add(phoneField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Email:"), gbc); gbc.gridx = 1; panel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Mật khẩu mới:"), gbc); gbc.gridx = 1; panel.add(passField, gbc);

        JButton updateBtn = new JButton("Cập nhật");
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(updateBtn, gbc);

        updateBtn.addActionListener(e -> {
            User u = new User();
            u.setUsername(currentUser.getUsername());
            u.setFullName(nameField.getText().trim());
            u.setPhone(phoneField.getText().trim());
            u.setEmail(emailField.getText().trim());
            u.setPassword(new String(passField.getPassword()));

            Message req = new Message(RequestType.UPDATE_USER, "");
            req.setUser(u);
            Message resp = sendRequest(req);
            JOptionPane.showMessageDialog(mainFrame,
                    resp != null && resp.isSuccess() ? "Cập nhật OK" : "Cập nhật lỗi");
        });

        return panel;
    }

    private JPanel createUserHistoryTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea(); 
        area.setEditable(false);

        // Auto refresh mỗi 5 giây
        Timer timer = new Timer(5000, e -> {
            if (currentUser != null) {
                Message req = new Message(RequestType.GET_LOGIN_HISTORY, currentUser.getUsername());
                Message resp = sendRequest(req);
                if (resp != null && resp.isSuccess()) {
                    // 👇 In ra JSON server trả về
//                    System.out.println("DEBUG JSON (user history): " + resp.getContent());

                    List<String> logs = gson.fromJson(
                            resp.getContent(),
                            new TypeToken<List<String>>(){}.getType()
                    );

                    StringBuilder sb = new StringBuilder();
                    for (String r : logs) {
                        sb.append(r).append("\n");
                    }
                    area.setText(sb.toString());
                }
            }
        });
        timer.start();

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }



    private JPanel createUserFilesTab() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton upload = new JButton("Upload");
        JButton download = new JButton("Download");
        panel.add(upload); panel.add(download);

        upload.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(mainFrame) == JFileChooser.APPROVE_OPTION) {
                try {
                    File f = fc.getSelectedFile();
                    byte[] data = java.nio.file.Files.readAllBytes(f.toPath());
                    String b64 = java.util.Base64.getEncoder().encodeToString(data);
                    Message req = new Message(RequestType.UPLOAD_FILE, f.getName() + "|" + b64);
                    Message resp = sendRequest(req);
                    JOptionPane.showMessageDialog(mainFrame, resp.getContent());
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        download.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Tên file:");
            if (name != null) {
                Message req = new Message(RequestType.DOWNLOAD_FILE, name);
                Message resp = sendRequest(req);
                if (resp != null && resp.isSuccess()) {
                    try {
                        String[] parts = resp.getContent().split("\\|",2);
                        byte[] data = java.util.Base64.getDecoder().decode(parts[1]);
                        JFileChooser fc = new JFileChooser(); fc.setSelectedFile(new File(parts[0]));
                        if (fc.showSaveDialog(mainFrame)==JFileChooser.APPROVE_OPTION)
                            java.nio.file.Files.write(fc.getSelectedFile().toPath(), data);
                    } catch (Exception ex) { ex.printStackTrace(); }
                }
            }
        });
        return panel;
    }

    private JPanel createSupportTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea msg = new JTextArea(); msg.setBorder(BorderFactory.createTitledBorder("Yêu cầu hỗ trợ"));
        JButton send = new JButton("Gửi");
        send.addActionListener(e -> {
            Message req = new Message(RequestType.BROADCAST, "[Support] " + currentUser.getUsername() + ": " + msg.getText());
            sendRequest(req);
            msg.setText("");
        });
        panel.add(new JScrollPane(msg), BorderLayout.CENTER);
        panel.add(send, BorderLayout.SOUTH);
        return panel;
    }

    // ---------------- ADMIN PANEL ----------------
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", createDashboardTab());
        tabs.addTab("Users", createUserManagementTab());
        tabs.addTab("Login history", createAllHistoryTab());
        tabs.addTab("Broadcast", createBroadcastTab());

        panel.add(tabs, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Đăng xuất");
        logoutButton.addActionListener(e -> {
            sendRequest(new Message(RequestType.LOGOUT, ""));
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        panel.add(logoutButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDashboardTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);

        panel.add(new JScrollPane(area), BorderLayout.CENTER);

        // Timer auto-refresh mỗi 3 giây
        Timer timer = new Timer(3000, e -> {
            Message resp = sendRequest(new Message(RequestType.GET_STATS, ""));
            if (resp != null && resp.isSuccess()) {
                area.setText(resp.getContent());
            }
        });
        timer.start();

        return panel;
    }


    private JPanel createUserManagementTab() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Username","Họ tên","Email","Điện thoại","Admin","Locked"},0);
        JTable table = new JTable(model);

        // Auto refresh users mỗi 5 giây
        Timer timer = new Timer(5000, e -> loadUsers(model));
        timer.start();

        JButton edit = new JButton("Sửa");
        JButton del = new JButton("Xóa");

        edit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                String username = (String) model.getValueAt(row,0);
                String fullName = (String) model.getValueAt(row,1);
                String email = (String) model.getValueAt(row,2);
                String phone = (String) model.getValueAt(row,3);
                boolean isAdmin = (Boolean) model.getValueAt(row,4);
                boolean isLocked = (Boolean) model.getValueAt(row,5);

                JTextField fnField = new JTextField(fullName);
                JTextField emailField = new JTextField(email);
                JTextField phoneField = new JTextField(phone);
                JCheckBox adminBox = new JCheckBox("Admin", isAdmin);
                JCheckBox lockedBox = new JCheckBox("Bị khóa", isLocked);

                JPanel form = new JPanel(new GridLayout(5,2));
                form.add(new JLabel("Họ tên:")); form.add(fnField);
                form.add(new JLabel("Email:")); form.add(emailField);
                form.add(new JLabel("Điện thoại:")); form.add(phoneField);
                form.add(adminBox); form.add(lockedBox);

                int ok = JOptionPane.showConfirmDialog(mainFrame, form,
                        "Sửa user: " + username, JOptionPane.OK_CANCEL_OPTION);
                if (ok == JOptionPane.OK_OPTION) {
                    // 1. Update info
                    User u = new User();
                    u.setUsername(username);
                    u.setFullName(fnField.getText().trim());
                    u.setEmail(emailField.getText().trim());
                    u.setPhone(phoneField.getText().trim());
                    u.setPassword(""); // không đổi mật khẩu

                    Message req = new Message(RequestType.UPDATE_USER, "");
                    req.setUser(u);
                    sendRequest(req);

                    // 2. Update role
                    Message roleReq = new Message(
                        RequestType.SET_ADMIN,
                        username + "," + adminBox.isSelected()
                    );
                    sendRequest(roleReq);

                    // 3. Lock/unlock
                    if (lockedBox.isSelected()) {
                        sendRequest(new Message(RequestType.LOCK_USER, username));
                    } else {
                        sendRequest(new Message(RequestType.UNLOCK_USER, username));
                    }

                    // Refresh lại bảng
                    loadUsers(model);
                }

            }
        });

        del.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row>=0) {
                String username = (String) model.getValueAt(row,0);
                sendRequest(new Message(RequestType.DELETE_USER, username));
            }
        });

        JPanel top = new JPanel(); top.add(edit); top.add(del);
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Lần đầu load
        loadUsers(model);
        return panel;
    }
    private JPanel createLoginHistoryTab() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Username","Time","Success","IP"},0);
        JTable table = new JTable(model);

        // Auto refresh mỗi 5 giây
        Timer timer = new Timer(5000, e -> {
            Message resp = sendRequest(new Message("GET_LOGIN_HISTORY",""));
            if (resp!=null && resp.isSuccess()) {
                model.setRowCount(0);
                List<LoginRecord> list = gson.fromJson(resp.getContent(),
                        new com.google.gson.reflect.TypeToken<List<LoginRecord>>(){}.getType());
                for (LoginRecord r: list) {
                    model.addRow(new Object[]{r.getUsername(),r.getTime(),r.isSuccess(),r.getIp()});
                }
            }
        });
        timer.start();

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void loadUsers(DefaultTableModel model) {
        Message resp = sendRequest(new Message(RequestType.GET_ALL_USERS,""));
        if (resp!=null && resp.isSuccess()) {
            model.setRowCount(0);
            List<User> list = gson.fromJson(resp.getContent(),
                    new com.google.gson.reflect.TypeToken<List<User>>(){}.getType());
            for (User u: list) {
                model.addRow(new Object[]{u.getUsername(),u.getFullName(),u.getEmail(),
                        u.getPhone(),u.isAdmin(),u.isLocked()});
            }
        }
    }


    private JPanel createAllHistoryTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea(); 
        area.setEditable(false);

        // Auto refresh mỗi 5 giây
        Timer timer = new Timer(5000, e -> {
            Message req = new Message(RequestType.GET_LOGIN_HISTORY, "ALL");
            Message resp = sendRequest(req);
            if (resp != null && resp.isSuccess()) {
            	List<String> logs = gson.fromJson(resp.getContent(),
            	        new TypeToken<List<String>>(){}.getType());
            	StringBuilder sb = new StringBuilder();
            	for (String r : logs) {
            	    sb.append(r).append("\n");
            	}
            	area.setText(sb.toString());

            }
        });
        timer.start();

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }


    private JPanel createBroadcastTab() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea msg = new JTextArea(); 
        msg.setBorder(BorderFactory.createTitledBorder("Thông báo"));
        JButton send = new JButton("Gửi");

        send.addActionListener(e -> {
            Message req = new Message(RequestType.BROADCAST, msg.getText());
            Message resp = sendRequest(req);
            msg.setText("");

            // ✅ Thêm xử lý cho phản hồi từ server
            if (resp != null && resp.isSuccess()) {
                JOptionPane.showMessageDialog(mainFrame,
                        resp.getContent(),     // chỉ lấy string thôi
                        "Kết quả Broadcast",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(mainFrame,
                        resp != null ? resp.getError() : "Lỗi kết nối",
                        "Broadcast thất bại",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JScrollPane(msg), BorderLayout.CENTER);
        panel.add(send, BorderLayout.SOUTH);
        return panel;
    }


    // ---------------- UTILITIES ----------------
 // Hàng đợi để gửi kết quả response về cho sendRequest
    private BlockingQueue<Message> responseQueue = new ArrayBlockingQueue<>(10);

    private Message sendRequest(Message request) {
        try {
            output.println(gson.toJson(request));
            // chờ tối đa 2 giây để lấy response
            Message resp = responseQueue.poll(2, TimeUnit.SECONDS);
            if (resp == null) {
                System.err.println("Timeout waiting for response");
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void startListener() {
        Thread listener = new Thread(() -> {
            try {
                String line;
                while ((line = input.readLine()) != null) {
                    Message msg = gson.fromJson(line, Message.class);

                    if (RequestType.BROADCAST.equals(msg.getType())) {
                        if (msg.isSuccess() && msg.getContent().startsWith("Đã gửi")) {
                            // Đây là response cho admin
                            responseQueue.offer(msg);
                        } else {
                            // Đây là broadcast push
                            SwingUtilities.invokeLater(() ->
                                JOptionPane.showMessageDialog(mainFrame,
                                        msg.getContent(),
                                        "📢 Thông báo",
                                        JOptionPane.INFORMATION_MESSAGE));
                        }
                    } else {
                        responseQueue.offer(msg);
                    }

                }
            } catch (IOException e) {
                System.err.println("Listener stopped: " + e.getMessage());
            }
        });
        listener.setDaemon(true);
        listener.start();
    }



    private void cleanup() {
        try {
            if (socket != null) socket.close();
            if (input != null) input.close();
            if (output != null) output.close();
        } catch (IOException e) {
            System.err.println("Error cleaning up client: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Client::new);
    }
}
