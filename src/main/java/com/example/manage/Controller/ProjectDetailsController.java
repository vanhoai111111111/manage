package com.example.manage.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Map;

public class ProjectDetailsController {

    @FXML
    private Label projectIdLabel;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Label employeeNameLabel;

    public void setProjectDetails(Map<String, String> projectDetails) {
        projectIdLabel.setText(projectDetails.get("projectId"));
        projectNameLabel.setText(projectDetails.get("projectName"));
        employeeNameLabel.setText(projectDetails.get("employeeName"));
    }
}
