package com.example.manage.Model;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DatabaseUtil {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=manage; trustServerCertificate = TRUE ";
    private static final String USER = "sa";
    private static final String PASSWORD = "123@";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM tblAdmin WHERE username = ? AND password = ?";
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
    public static void loadduanData(ObservableList<Map<String, String>> duanData) {


        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {


            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery("SELECT * FROM tblProject")) {

                while (rs.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("id", rs.getString("projectId"));
                    row.put("tenduan", rs.getString("ProjectName"));
                    row.put("tennv", rs.getString("employeeName"));

                    duanData.add(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addproject(Map<String, String> project) {


        String query = "INSERT INTO tblProject (ProjectId, ProjectName, employeeName) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.get("id"));
            preparedStatement.setString(2, project.get("tenduan"));
            preparedStatement.setString(3, project.get("tennv"));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void deleteduan(Map<String, String> project) {

        String query = "DELETE FROM tblProject WHERE ProjectId = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.get("id"));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
