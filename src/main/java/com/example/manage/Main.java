package com.example.manage;

import com.example.manage.Model.LanguageUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            LanguageUtil.setLocale(new Locale("en"));
            Parent root = FXMLLoader.load(getClass().getResource("View/LoginView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
