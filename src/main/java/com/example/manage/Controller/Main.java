package com.example.manage.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class Main extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Locale locale;
    private ResourceBundle bundle;
    private int userRole; // Thêm biến để lưu trữ vai trò người dùng

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
            controller.setUserRole(userRole); // Truyền vai trò người dùng

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
            loader.setLocation(Main.class.getResource("/com/example/manage/View/Department.fxml"));
            primaryStage.setTitle(bundle.getString("dashboard.title"));
            BorderPane mainPage = loader.load();

            // Set the controller
            DepartmentController controller = loader.getController();
            controller.setMainApp(this);
            controller.setUserRole(userRole); // Truyền vai trò người dùng

            rootLayout.setCenter(mainPage);
            primaryStage.sizeToScene(); // Tự động cập nhật kích thước
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showProjectDetails(Map<String, String> selectedProject) {
        if (selectedProject != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manage/View/projectDetails.fxml"));
                loader.setResources(bundle);
                Parent root = loader.load();

                ProjectDetailsController controller = loader.getController();
                controller.setProjectDetails(selectedProject);

                Stage stage = new Stage();
                stage.setTitle(bundle.getString("project.details.title"));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(primaryStage);
                stage.setScene(new Scene(root));
                stage.sizeToScene(); // Điều chỉnh kích thước cửa sổ cho phù hợp với nội dung
                stage.showAndWait(); // Hiển thị cửa sổ và chờ đến khi nó được đóng
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showDepartmentDetails(Map<String, String> selectedDepartment) {
        if (selectedDepartment != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manage/View/departmentDetails.fxml"));
                loader.setResources(bundle);
                Parent root = loader.load();

                DepartmentDetailsController controller = loader.getController();
                controller.setDepartmentDetails(selectedDepartment);

                Stage stage = new Stage();
                stage.setTitle(bundle.getString("department.details.title"));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(primaryStage);
                stage.setScene(new Scene(root));
                stage.sizeToScene(); // Điều chỉnh kích thước cửa sổ cho phù hợp với nội dung
                stage.showAndWait(); // Hiển thị cửa sổ và chờ đến khi nó được đóng
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(bundle);
            loader.setLocation(Main.class.getResource("/com/example/manage/View/Employee.fxml"));
            primaryStage.setTitle(bundle.getString("dashboard.title"));
            BorderPane mainPage = loader.load();

            EmployeeController controller = loader.getController();
            controller.setMainApp(this);
            controller.setUserRole(userRole); // Truyền vai trò người dùng

            rootLayout.setCenter(mainPage);
            primaryStage.sizeToScene(); // Tự động cập nhật kích thước
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showEmployeeDetails(Map<String, String> employeeData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/manage/View/EmployeeDetails.fxml"));
            loader.setResources(bundle);
            Parent root = loader.load();

            EmployeeDetailsController controller = loader.getController();
            controller.setEmployeeDetails(employeeData);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(bundle.getString("employee.details.title"));
            stage.sizeToScene(); // Điều chỉnh kích thước cửa sổ cho phù hợp với nội dung
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setUserRole(int role) {
        this.userRole = role;
    }

    public int getUserRole() {
        return userRole;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
