package com.example.manage.Controller;

import javafx.event.ActionEvent;

public class DashboardController {



    private Main mainApp;



    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void Project (ActionEvent event) {
        mainApp.showProject();
    }
    public void Department (ActionEvent event) {
        mainApp.showDepartment();

    }
    public void Employee (ActionEvent event) {
        mainApp.showEmployee();
    }

}

