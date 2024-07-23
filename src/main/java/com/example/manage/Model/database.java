package com.example.manage.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=manage; trustServerCertificate = TRUE ";
    static final String USER = "sa";
    static final String PASSWORD = "123@";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
