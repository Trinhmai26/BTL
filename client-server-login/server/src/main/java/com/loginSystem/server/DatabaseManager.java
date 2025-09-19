package com.loginSystem.server;

import com.loginSystem.common.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/login_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456"; // ⚠️ đổi theo cấu hình MySQL của bạn

    private Connection connection;

    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            createTables();
            createDefaultAdmin();
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    private void createTables() {
        try (Statement stmt = connection.createStatement()) {
            String createUsersTable =
                    "CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "full_name VARCHAR(100) NOT NULL, " +
                    "phone VARCHAR(15), " +
                    "email VARCHAR(100), " +
                    "is_admin BOOLEAN DEFAULT FALSE, " +
                    "is_locked BOOLEAN DEFAULT FALSE, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "last_login TIMESTAMP NULL" +
                    ")";

            String createLoginHistoryTable =
                    "CREATE TABLE IF NOT EXISTS login_history (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50), " +
                    "login_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "success BOOLEAN, " +
                    "ip_address VARCHAR(45)" +
                    ")";

            stmt.execute(createUsersTable);
            stmt.execute(createLoginHistoryTable);
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }


    private void createDefaultAdmin() {
        try {
            String checkAdmin = "SELECT COUNT(*) FROM users WHERE is_admin = TRUE";
            PreparedStatement pstmt = connection.prepareStatement(checkAdmin);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                User admin = new User("admin", "admin123", "Administrator",
                        "0123456789", "admin@system.com");
                admin.setAdmin(true);
                addUser(admin);
                System.out.println("Default admin created: admin/admin123");
            }
        } catch (SQLException e) {
            System.err.println("Error creating default admin: " + e.getMessage());
        }
    }

    public User authenticate(String username, String password) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getBoolean("is_locked")) {
                    return null; // bị khóa
                }

                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("full_name"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setLocked(rs.getBoolean("is_locked"));

                updateLastLogin(username);
                logLoginAttempt(username, true, "127.0.0.1");

                return user;
            } else {
                logLoginAttempt(username, false, "127.0.0.1");
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            return null;
        }
    }

    public boolean addUser(User user) {
        try {
            String sql = "INSERT INTO users (username, password, full_name, phone, email, is_admin) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getFullName());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getEmail());
            pstmt.setBoolean(6, user.isAdmin());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(User user) {
        try {
            boolean updatePassword = (user.getPassword() != null && !user.getPassword().isEmpty());
            String sql = updatePassword
                    ? "UPDATE users SET full_name=?, phone=?, email=?, password=? WHERE username=?"
                    : "UPDATE users SET full_name=?, phone=?, email=? WHERE username=?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getEmail());
            if (updatePassword) {
                pstmt.setString(4, user.getPassword());
                pstmt.setString(5, user.getUsername());
            } else {
                pstmt.setString(4, user.getUsername());
            }
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    public List<String> getLoginHistory(String usernameOrAll) {
        List<String> logs = new ArrayList<>();
        try {
            PreparedStatement pstmt;
            if (usernameOrAll == null || usernameOrAll.isEmpty() || "ALL".equalsIgnoreCase(usernameOrAll)) {
                pstmt = connection.prepareStatement(
                    "SELECT username, login_time, success, ip_address FROM login_history ORDER BY id DESC LIMIT 200");
            } else {
                pstmt = connection.prepareStatement(
                    "SELECT username, login_time, success, ip_address FROM login_history WHERE username=? ORDER BY id DESC LIMIT 200");
                pstmt.setString(1, usernameOrAll);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String line = String.format("%s | %s | %s | %s",
                        rs.getString("username"),
                        rs.getTimestamp("login_time"),
                        rs.getBoolean("success") ? "OK" : "FAIL",
                        rs.getString("ip_address"));
                logs.add(line);
            }
        } catch (SQLException e) {
            System.err.println("Error get login history: " + e.getMessage());
        }
        return logs;
    }

    public boolean deleteUser(String username) {
        try {
            String sql = "DELETE FROM users WHERE username = ? AND is_admin = FALSE";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    public boolean lockUser(String username, boolean lock) {
        try {
            String sql = "UPDATE users SET is_locked = ? WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setBoolean(1, lock);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error locking/unlocking user: " + e.getMessage());
            return false;
        }
    }


    public boolean setAdminRole(String username, boolean isAdmin) {
        try {
            String sql = "UPDATE users SET is_admin = ? WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setBoolean(1, isAdmin);
            pstmt.setString(2, username);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error setting admin role: " + e.getMessage());
            return false;
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users ORDER BY username";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setFullName(rs.getString("full_name"));
                user.setPhone(rs.getString("phone"));
                user.setEmail(rs.getString("email"));
                user.setAdmin(rs.getBoolean("is_admin"));
                user.setLocked(rs.getBoolean("is_locked"));

                Timestamp lastLogin = rs.getTimestamp("last_login");
                if (lastLogin != null) {
                    user.setLastLogin(lastLogin.toString());
                }

                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    private void updateLastLogin(String username) {
        try {
            String sql = "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating last login: " + e.getMessage());
        }
    }

    private void logLoginAttempt(String username, boolean success, String ipAddress) {
        try {
            String sql = "INSERT INTO login_history (username, success, ip_address) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setBoolean(2, success);
            pstmt.setString(3, ipAddress);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error logging login attempt: " + e.getMessage());
        }
    }

    public String getSystemStats() {
        try {
            StringBuilder stats = new StringBuilder();

            String totalUsersSQL = "SELECT COUNT(*) FROM users";
            PreparedStatement pstmt = connection.prepareStatement(totalUsersSQL);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                stats.append("Tổng số người dùng: ").append(rs.getInt(1)).append("\n");
            }

            String failedLoginsSQL = "SELECT COUNT(*) FROM login_history WHERE success = FALSE AND DATE(login_time) = CURDATE()";
            pstmt = connection.prepareStatement(failedLoginsSQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                stats.append("Đăng nhập thất bại hôm nay: ").append(rs.getInt(1)).append("\n");
            }

            String lockedAccountsSQL = "SELECT COUNT(*) FROM users WHERE is_locked = TRUE";
            pstmt = connection.prepareStatement(lockedAccountsSQL);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                stats.append("Tài khoản bị khóa: ").append(rs.getInt(1));
            }

            return stats.toString();
        } catch (SQLException e) {
            System.err.println("Error getting system stats: " + e.getMessage());
            return "Lỗi lấy thống kê hệ thống";
        }
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
