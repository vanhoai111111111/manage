package com.example.manage.Model;

import java.sql.*;

import static com.example.manage.Model.database.*;

public class LoginModel {
    public static int getRole(String username, String password) {
        String query = "SELECT role FROM tblAdmin WHERE username = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                return rs.getInt("role"); // Trả về giá trị của cột role
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không tìm thấy người dùng hoặc mật khẩu không khớp
    }
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM tblAdmin WHERE username = ? AND password = ? ";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
