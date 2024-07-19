package com.example.manage.Controller;

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
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private ComboBox<String> languageSelector;

    @FXML
    private Stage stage;

    @FXML
    private com.example.manage.Model.LanguageUtil LanguageUtil;

    @FXML
    public void initialize() {
        // Set default language
        setLanguage(new Locale("en"));

        // Populate language selector
        languageSelector.getItems().addAll("English", "Tiếng Việt");
        languageSelector.setOnAction(e -> {
            String selectedLanguage = languageSelector.getValue();
            if ("English".equals(selectedLanguage)) {
                setLanguage(new Locale("en"));
            } else if ("Tiếng Việt".equals(selectedLanguage)) {
                setLanguage(new Locale("vi"));
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void setLanguage(Locale locale) {
        LanguageUtil.setLocale(locale);


            usernameLabel.setText(LanguageUtil.getString("login.username"));
            passwordLabel.setText(LanguageUtil.getString("login.password"));
            loginButton.setText(LanguageUtil.getString("login.button"));

    }
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (DatabaseUtil.validateLogin(username, password)) {
            // Login successful
            openMainPage();
        } else {
            // Login failed
            showAlert("Error", "Invalid username or password.");
        }
    }

    private void openMainPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/manage/View/Dashboard.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Main Page");
            stage.setScene(new Scene(root, 400, 300));
            stage.show();

            // Close login window
            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to open the main page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
