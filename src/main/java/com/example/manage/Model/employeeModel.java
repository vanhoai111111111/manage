package com.example.manage.Model;

import com.example.manage.Controller.Main;
import com.sun.source.tree.TryTree;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.manage.Model.database.*;

public class employeeModel {


    public static void addEmployees(Map<String, String> project) {
        String query = "INSERT INTO tblEmployee (EmployeeID, EmployeeName, ProjectName, Phone) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, project.get("EmployeeID"));
            preparedStatement.setString(2, project.get("EmployeeName"));
            preparedStatement.setString(3, project.get("ProjectName"));
            preparedStatement.setString(4, project.get("Phone"));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ObservableList<String> getprojectName() {
        ObservableList<String> projectNames = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT ProjectName FROM tblProject";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                projectNames.add(rs.getString("ProjectName").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectNames;
    }

    public static void showdata(ObservableList<Map<String, String>> employeedata) {
        employeedata.clear();
        String query = "SELECT * FROM tblEmployee";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("EmployeeID", rs.getString("EmployeeID").trim());
                row.put("EmployeeName", rs.getString("EmployeeName").trim());
                row.put("ProjectName", rs.getString("ProjectName").trim());
                row.put("Phone", rs.getString("Phone").trim());
                employeedata.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateemployee(Map<String, String> employee) {
        String query = "UPDATE tblEmployee SET EmployeeName = ?, ProjectName = ?, Phone = ? WHERE EmployeeID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, employee.get("EmployeeName"));
            preparedStatement.setString(2, employee.get("ProjectName"));
            preparedStatement.setString(3, employee.get("Phone"));
            preparedStatement.setInt(4, Integer.parseInt(employee.get("EmployeeID")));

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteemployee(Map<String, String> employee) {
        String query = "DELETE FROM tblEmployee WHERE EmployeeID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employee.get("EmployeeID"));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public static void loademployeedata(ObservableList<Map<String, String>> Employeedata) {
        loademployeedata(Employeedata,"");
    }
    public static void loademployeedata(ObservableList<Map<String, String>> Employeedata, String keyword) {
        Employeedata.clear();
        String query = "SELECT * FROM tblEmployee WHERE EmployeeName LIKE ? OR ProjectName LIKE ?";
        try (Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query))
            {
                preparedStatement.setString(1, "%" + keyword +"%");
                preparedStatement.setString(2,"%" + keyword + "%");
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    Map<String, String> row = new HashMap<>();
                    row.put("EmployeeID", rs.getString("EmployeeID").trim());
                    row.put("EmployeeName", rs.getString("EmployeeName").trim());
                    row.put("ProjectName", rs.getString("ProjectName").trim());
                    row.put("Phone", rs.getString("Phone").trim());
                    Employeedata.add(row);
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}