package com.example.manage.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.manage.Model.database.PASSWORD;
import static com.example.manage.Model.database.URL;
import static com.example.manage.Model.database.USER;
import static com.example.manage.Model.database.getConnection;

public class projectModel {

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
        loadduanData(duanData, ""); // Load all data initially
    }

    public static void loadduanData(ObservableList<Map<String, String>> duanData, String keyword) {
        duanData.clear();
        String query = "SELECT * FROM tblProject WHERE ProjectName LIKE ? OR employeeName LIKE ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("projectId", rs.getString("projectId").trim());
                row.put("projectName", rs.getString("ProjectName").trim());
                row.put("employeeName", rs.getString("employeeName").trim());

                duanData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void showdata(ObservableList<Map<String, String>> duanData) {
        duanData.clear();
        String query = "SELECT * FROM tblProject ";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("projectId", rs.getString("projectId").trim());
                row.put("projectName", rs.getString("ProjectName").trim());
                row.put("employeeName", rs.getString("employeeName").trim());

                duanData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void projectdata(ObservableList<Map<String, String>> duanData) {
        showdata(duanData); // Load all data initially
    }
    public static void addproject(Map<String, String> project) {
        String query = "INSERT INTO tblProject (ProjectId, ProjectName, employeeName) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.get("projectId"));
            preparedStatement.setString(2, project.get("projectName"));
            preparedStatement.setString(3, project.get("employeeName"));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteProject(Map<String, String> project) {
        String query = "DELETE FROM tblProject WHERE ProjectId = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.get("projectId"));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void updateproject(Map<String, String> project) {
        String query = "UPDATE tblProject SET ProjectName = ?, employeeName = ? WHERE ProjectId = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.get("projectName"));
            preparedStatement.setString(2, project.get("employeeName"));
            preparedStatement.setInt(3, Integer.parseInt(project.get("projectId")));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static ObservableList<String> getEmployeeNames() {
        ObservableList<String> employeeNames = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT employeeName FROM tblEmployee";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                employeeNames.add(rs.getString("employeeName").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeNames;
    }

}
