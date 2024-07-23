package com.example.manage.Controller;

import javafx.event.ActionEvent;

public class DashboardController {



    private Main mainApp;



    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public void Project (ActionEvent event) {
        mainApp.showproject();
    }
    public void Department (ActionEvent event) {
        mainApp.showdepartment();

    }

}

