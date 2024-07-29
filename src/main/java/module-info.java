module com.example.manage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jdk.compiler;
    requires java.desktop;

    opens com.example.manage.Controller to javafx.fxml;

    exports com.example.manage;
    exports com.example.manage.Controller;

}