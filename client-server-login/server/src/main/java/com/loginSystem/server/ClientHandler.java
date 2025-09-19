package com.loginSystem.server;

import com.google.gson.Gson;
import com.loginSystem.common.*;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Server server;
    private BufferedReader input;
    private PrintWriter output;
    private String currentUser;
    private Gson gson;

    public ClientHandler(Socket socket, Server server) {
        this.clientSocket = socket;
        this.server = server;
        this.gson = new Gson();

        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error setting up client handler: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String inputLine;
            while ((inputLine = input.readLine()) != null) {
                Message request = gson.fromJson(inputLine, Message.class);
                Message response = processRequest(request);
                output.println(gson.toJson(response));
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } finally {
            cleanup();
        }
    }
    public void pushNotification(String text) {
        Message m = new Message(RequestType.BROADCAST, text);
        m.setSuccess(true);
        output.println(new Gson().toJson(m));
    }

    private Message processRequest(Message request) {
        Message response = new Message();
        response.setType(request.getType());

        switch (request.getType()) {
            case RequestType.LOGIN:
                response = handleLogin(request);
                break;
            case RequestType.REGISTER:
                response = handleRegister(request);
                break;
            case RequestType.GET_USER_INFO:
                response = handleGetUserInfo(request);
                break;
            case RequestType.UPDATE_USER:
                response = handleUpdateUser(request);
                break;
            case RequestType.GET_ALL_USERS:
                response = handleGetAllUsers();
                break;
            case RequestType.DELETE_USER:
                response = handleDeleteUser(request);
                break;
            case RequestType.LOCK_USER:
                response = handleLockUser(request, true);
                break;
            case RequestType.UNLOCK_USER:
                response = handleLockUser(request, false);
                break;
            case RequestType.SET_ADMIN:
                response = handleSetAdmin(request);
                break;
            case RequestType.GET_STATS:
                response = handleGetStats();
                break;
            case RequestType.GET_LOGIN_HISTORY:
                response = handleGetLoginHistory(request); break;
            case RequestType.BROADCAST:
                response = handleBroadcast(request); break;
            case RequestType.UPLOAD_FILE:
                response = handleUploadFile(request); break;
            case RequestType.DOWNLOAD_FILE:
                response = handleDownloadFile(request); break;

            case RequestType.LOGOUT:
                response = handleLogout();
                break;
            default:
                response.setSuccess(false);
                response.setError("Unknown request type");
                break;
        }

        return response;
    }

    private Message handleLogin(Message request) {
        Message response = new Message(RequestType.LOGIN, "");
        User loginUser = request.getUser();

        User authenticatedUser = server.getDbManager().authenticate(
                loginUser.getUsername(), loginUser.getPassword()
        );

        if (authenticatedUser != null) {
            currentUser = authenticatedUser.getUsername();
            server.addActiveClient(currentUser, this);

            response.setSuccess(true);
            response.setUser(authenticatedUser);
            response.setContent("Đăng nhập thành công");
        } else {
            response.setSuccess(false);
            response.setError("Sai thông tin hoặc tài khoản bị khóa");
        }
        return response;
    }
//    private Message handleGetLoginHistory(Message request) {
//        Message res = new Message(RequestType.GET_LOGIN_HISTORY, "");
//        String who = request.getContent(); // "ALL" hoặc username
//        List<String> logs = server.getDbManager().getLoginHistory(who);
//        res.setSuccess(true);
//        res.setContent(new Gson().toJson(logs));
//        return res;
//    }
    private Message handleGetLoginHistory(Message request) {
        Message res = new Message(RequestType.GET_LOGIN_HISTORY, "");
        String who = request.getContent();
        List<String> logs = server.getDbManager().getLoginHistory(who);
        res.setSuccess(true);

        // Gửi logs dạng JSON array luôn, không bọc thành string
        res.setContent(new Gson().toJson(logs));
        return res;
    }


    private Message handleBroadcast(Message request) {
        Message res = new Message(RequestType.BROADCAST, "");
        if (currentUser == null) { res.setSuccess(false); res.setError("Not logged in"); return res; }
        // (Option) kiểm tra quyền admin ở DB:
        // lấy user hiện tại từ DB cho chắc
        List<User> users = server.getDbManager().getAllUsers();
        boolean isAdmin = users.stream().anyMatch(u -> u.getUsername().equals(currentUser) && u.isAdmin());
        if (!isAdmin) { res.setSuccess(false); res.setError("Permission denied"); return res; }
        server.broadcast(request.getContent());
        res.setSuccess(true);
        res.setContent("Đã gửi thông báo đến tất cả user online");
        return res;
    }

    private Message handleUploadFile(Message request) {
        Message res = new Message(RequestType.UPLOAD_FILE, "");
        try {
            // content định dạng: "filename.ext|BASE64_DATA"
            String[] parts = request.getContent().split("\\|", 2);
            String filename = parts[0];
            String b64 = parts[1];
            byte[] data = java.util.Base64.getDecoder().decode(b64);
            java.nio.file.Path dir = java.nio.file.Paths.get("uploads", currentUser == null ? "anonymous" : currentUser);
            java.nio.file.Files.createDirectories(dir);
            java.nio.file.Files.write(dir.resolve(filename), data);
            res.setSuccess(true);
            res.setContent("Upload thành công");
        } catch (Exception e) {
            res.setSuccess(false);
            res.setError("Upload lỗi: " + e.getMessage());
        }
        return res;
    }

    private Message handleDownloadFile(Message request) {
        Message res = new Message(RequestType.DOWNLOAD_FILE, "");
        try {
            String filename = request.getContent(); // chỉ tên file
            java.nio.file.Path path = java.nio.file.Paths.get("uploads",
                    currentUser == null ? "anonymous" : currentUser, filename);
            byte[] data = java.nio.file.Files.readAllBytes(path);
            String b64 = java.util.Base64.getEncoder().encodeToString(data);
            res.setSuccess(true);
            res.setContent(filename + "|" + b64);
        } catch (Exception e) {
            res.setSuccess(false);
            res.setError("Download lỗi: " + e.getMessage());
        }
        return res;
    }

    private Message handleRegister(Message request) {
        Message response = new Message(RequestType.REGISTER, "");
        User newUser = request.getUser();

        if (server.getDbManager().addUser(newUser)) {
            response.setSuccess(true);
            response.setContent("Đăng ký thành công");
        } else {
            response.setSuccess(false);
            response.setError("Đăng ký thất bại, username có thể đã tồn tại");
        }
        return response;
    }

    private Message handleGetUserInfo(Message request) {
        Message response = new Message(RequestType.GET_USER_INFO, "");
        List<User> users = server.getDbManager().getAllUsers();

        User user = users.stream()
                .filter(u -> u.getUsername().equals(request.getContent()))
                .findFirst().orElse(null);

        if (user != null) {
            response.setSuccess(true);
            response.setUser(user);
        } else {
            response.setSuccess(false);
            response.setError("Không tìm thấy người dùng");
        }
        return response;
    }

    private Message handleUpdateUser(Message request) {
        Message response = new Message(RequestType.UPDATE_USER, "");
        if (server.getDbManager().updateUser(request.getUser())) {
            response.setSuccess(true);
            response.setContent("Cập nhật thành công");
        } else {
            response.setSuccess(false);
            response.setError("Cập nhật thất bại");
        }
        return response;
    }

    private Message handleGetAllUsers() {
        Message response = new Message(RequestType.GET_ALL_USERS, "");
        List<User> users = server.getDbManager().getAllUsers();
        response.setSuccess(true);
        response.setContent(gson.toJson(users));
        return response;
    }

    private Message handleDeleteUser(Message request) {
        Message response = new Message(RequestType.DELETE_USER, "");
        if (server.getDbManager().deleteUser(request.getContent())) {
            response.setSuccess(true);
            response.setContent("Xóa thành công");
        } else {
            response.setSuccess(false);
            response.setError("Xóa thất bại");
        }
        return response;
    }

    private Message handleLockUser(Message request, boolean lock) {
        Message response = new Message(lock ? RequestType.LOCK_USER : RequestType.UNLOCK_USER, "");
        if (server.getDbManager().lockUser(request.getContent(), lock)) {
            response.setSuccess(true);
            response.setContent((lock ? "Khóa" : "Mở khóa") + " thành công");
        } else {
            response.setSuccess(false);
            response.setError((lock ? "Khóa" : "Mở khóa") + " thất bại");
        }
        return response;
    }

    private Message handleSetAdmin(Message request) {
        Message response = new Message(RequestType.SET_ADMIN, "");
        String[] parts = request.getContent().split(",");
        String username = parts[0];
        boolean isAdmin = Boolean.parseBoolean(parts[1]);

        if (server.getDbManager().setAdminRole(username, isAdmin)) {
            response.setSuccess(true);
            response.setContent("Cập nhật quyền thành công");
        } else {
            response.setSuccess(false);
            response.setError("Cập nhật quyền thất bại");
        }
        return response;
    }

    private Message handleGetStats() {
        Message response = new Message(RequestType.GET_STATS, "");
        String stats = server.getDbManager().getSystemStats();
        stats += "\nSố người dùng đang online: " + server.getActiveClientCount();

        response.setSuccess(true);
        response.setContent(stats);
        return response;
    }

    private Message handleLogout() {
        Message response = new Message(RequestType.LOGOUT, "");
        if (currentUser != null) {
            server.removeActiveClient(currentUser);
            currentUser = null;
        }
        response.setSuccess(true);
        response.setContent("Đăng xuất thành công");
        return response;
    }

    private void cleanup() {
        try {
            if (currentUser != null) server.removeActiveClient(currentUser);
            if (input != null) input.close();
            if (output != null) output.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Cleanup error: " + e.getMessage());
        }
    }
}
