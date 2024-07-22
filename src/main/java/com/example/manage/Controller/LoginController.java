package com.example.manage.Controller;

import com.example.manage.Controller.Main;
import com.example.manage.Model.DatabaseUtil;
import com.example.manage.Model.LanguageUtil;
import com.example.manage.Model.LanguageUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class LoginController {




    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private ComboBox<String> languageSelector;



    @FXML
    private Main mainApp;

    @FXML
    public void initialize() {
        // Set default language

    }


    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseUtil.validateLogin(username, password)) {
            // Login successful
            mainApp.showMainPage();
        } else {
            // Login failed
            showAlert("Error", "Invalid username or password.");
        }
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
