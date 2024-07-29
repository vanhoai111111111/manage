package com.example.manage.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.Map;

public class EmployeeDetailsController {

    @FXML
    private Label employeeIDLabel;
    @FXML
    private Label employeeNameLabel;
    @FXML
    private Label projectNameLabel;
    @FXML
    private Label phoneLabel;

    public void setEmployeeDetails(Map<String, String> employeeData) {
        employeeIDLabel.setText(employeeData.get("EmployeeID"));
        employeeNameLabel.setText(employeeData.get("EmployeeName"));
        projectNameLabel.setText(employeeData.get("ProjectName"));
        phoneLabel.setText(employeeData.get("Phone"));
    }
}
