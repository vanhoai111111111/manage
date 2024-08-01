package com.example.manage.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import static com.example.manage.Model.database.*;
public class DepartmentModel {

    public static void showDepartment(ObservableList<Map<String,String>>departmentData) {
        departmentData.clear();
        String query = "SELECT * FROM tblDepartment";

        try(Connection connection= DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement stmt = connection.prepareStatement(query);
        ResultSet rs = stmt.executeQuery()) {

            while(rs.next()){
                Map<String,String> row = new HashMap<>();
                row.put("DepartmentID", rs.getString("DepartmentID").trim());
                row.put("DepartmentName",rs.getString("DepartmentName").trim());
                row.put("Manager",rs.getString("Manager").trim());
                departmentData.add(row);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static ObservableList<String> getManager(){
       ObservableList<String> Manager = FXCollections.observableArrayList();
       String query = "SELECT EmployeeName FROM tblEmployee";
       try(Connection connection= DriverManager.getConnection(URL,USER,PASSWORD);
       Statement stmt= connection.createStatement();
       ResultSet rs = stmt.executeQuery(query)){
           while(rs.next()){
               Manager.add(rs.getString("EmployeeName").trim());

           }
       }catch(SQLException e){
           e.printStackTrace();
       }
       return Manager;
    }
    public static void AddDepartment(Map<String,String> departmentData) {
        String query= "INSERT INTO tblDepartment (DepartmentID,DepartmentName,Manager) VALUES (?,?,?)";
        try(Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,departmentData.get("DepartmentID"));
            stmt.setString(2, departmentData.get("DepartmentName"));
            stmt.setString(3,departmentData.get("Manager"));

            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void DeleteDepartment(Map<String,String> departmentData) {
        String query= "DELETE FROM tblDepartment WHERE DepartmentID=?";
        try(Connection connection=DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,departmentData.get("DepartmentID"));
            stmt.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static boolean  isDepartmentIdExists(String departmentId) {
        String query = "SELECT COUNT(*) FROM tblDepartment WHERE DepartmentID = ?";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, departmentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void uploadDepartment(Map<String,String> departmentData) {
        String query = "UPDATE tblDEpartment SET DepartmentName=?, Manager=? WHERE DepartmentID=?";

        try( Connection connection = DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,departmentData.get("DepartmentName"));
            stmt.setString(2,departmentData.get("Manager"));
            stmt.setInt(3,Integer.parseInt(departmentData.get("DepartmentID")));
            stmt.executeUpdate();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void loaddepartmentSearch(ObservableList<Map<String,String>>departmentData) {
        loaddepartmentSearch(departmentData,"");
    }
    public static void loaddepartmentSearch(ObservableList<Map<String,String>>departmentData,String keyword) {
        departmentData.clear();
        String query = "SELECT * FROM tblDepartment WHERE DepartmentName LIKE ? or Manager LIKE ?";
        try(Connection connection=DriverManager.getConnection(URL,USER,PASSWORD);
        PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1,"%" + keyword + "%");
            stmt.setString(2,"%"+ keyword + "%");
            ResultSet rs= stmt.executeQuery();

            while (rs.next()){
                Map<String,String> row = new HashMap<>();
                row.put("DepartmentID", rs.getString("DepartmentID").trim());
                row.put("DepartmentName",rs.getString("DepartmentName").trim());
                row.put("Manager",rs.getString("Manager").trim());
                departmentData.add(row);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
