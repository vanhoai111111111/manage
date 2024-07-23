package com.example.manage.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Locale locale;
    private ResourceBundle bundle;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;


        // Tạo ComboBox để chọn ngôn ngữ
        ComboBox<String> languageComboBox = new ComboBox<>();
        languageComboBox.getItems().addAll("English", "Tiếng Việt");
        languageComboBox.setValue("English"); // Mặc định chọn English
        languageComboBox.setOnAction(e -> {
            String selectedLanguage = languageComboBox.getValue();
            switch (selectedLanguage) {
                case "English":
                    locale = new Locale("en", "US");
                    break;
                case "Tiếng Việt":
                    locale = new Locale("vi", "VI");
                    break;
            }
            bundle = ResourceBundle.getBundle("messages", locale);
            showLoginPage(); // Load lại trang đăng nhập để cập nhật ngôn ngữ
        });

        locale = new Locale("en", "US");
        bundle = ResourceBundle.getBundle("messages", locale);

        // Khởi tạo root layout
        initRootLayout(languageComboBox);

        // Hiển thị trang đăng nhập
        showLoginPage();
    }

    private void initRootLayout(ComboBox<String> languageComboBox) {
        rootLayout = new BorderPane();
        rootLayout.setTop(languageComboBox);

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("/com/example/manage/View/LoginView.fxml"));
            primaryStage.setTitle(bundle.getString("dashboard.title"));
            BorderPane loginPage = loader.load();

            // Set the controller
            LoginController controller = loader.getController();
            controller.setMainApp(this);

            rootLayout.setCenter(loginPage);
            primaryStage.sizeToScene(); // Tự động cập nhật kích thước
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainPage() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("/com/example/manage/View/Dashboard.fxml"));
            BorderPane mainPage = loader.load();
            primaryStage.setTitle(bundle.getString("dashboard.title"));

            // Set the controller
            DashboardController controller = loader.getController();
            controller.setMainApp(this);

            rootLayout.setCenter(mainPage);
            primaryStage.sizeToScene(); // Tự động cập nhật kích thước
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProject() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("/com/example/manage/View/Project.fxml"));
            primaryStage.setTitle(bundle.getString("dashboard.title"));
            BorderPane mainPage = loader.load();

            // Set the controller
            duanController controller = loader.getController();
            controller.setMainApp(this);

            rootLayout.setCenter(mainPage);
            primaryStage.sizeToScene(); // Tự động cập nhật kích thước
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showDepartment() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("/com/example/manage/View/phongban.fxml"));
            primaryStage.setTitle(bundle.getString("dashboard.title"));
            BorderPane mainPage = loader.load();

            // Set the controller
            DepartmentController controller = loader.getController();
            controller.setMainApp(this);

            rootLayout.setCenter(mainPage);
            primaryStage.sizeToScene(); // Tự động cập nhật kích thước
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ResourceBundle getBundle() {
        return bundle;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
