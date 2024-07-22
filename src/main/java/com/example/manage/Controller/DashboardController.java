package com.example.manage.Controller;

import com.example.manage.Model.LanguageUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import com.example.manage.Controller.Main;
import java.io.IOException;
import java.util.Locale;

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

