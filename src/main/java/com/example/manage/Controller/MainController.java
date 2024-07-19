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

import java.io.IOException;
import java.util.Locale;

public class MainController {
    @FXML
    private Button Project;

    @FXML
    private Button Department;

    @FXML
    private Button employee;

    @FXML
    private Stage stage;

    @FXML
    private ComboBox<String> languageSelector;
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



        Project.setText(LanguageUtil.getString("dashboard.project"));
        Department.setText(LanguageUtil.getString("dashboard.department"));
        employee.setText(LanguageUtil.getString("dashboard.employee"));

    }
    public void duan (ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/manage/View/duan.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("DỰ ÁN");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void phongban (ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/manage/View/phongban.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("PHÒNG BAN");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

