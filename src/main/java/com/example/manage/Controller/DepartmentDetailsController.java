package com.example.manage.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;

public class DepartmentDetailsController {
    @FXML
    private Label DepartmentIDLabel;
    @FXML
    private Label DepartmentNameLabel;
    @FXML
    private Label ManagerLabel;


    public void setDepartmentDetails(Map<String, String> departmentData) {
        DepartmentIDLabel.setText(departmentData.get("DepartmentID"));
        DepartmentNameLabel.setText(departmentData.get("DepartmentName"));
        ManagerLabel.setText(departmentData.get("ManagerLabel"));

    }
}
