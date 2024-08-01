package com.example.manage.Controller;

import com.example.manage.Model.LoginModel;
import com.example.manage.Model.projectModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

        int role = LoginModel.getRole(username, password);
        if (role !=-1) {
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
