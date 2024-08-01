package com.example.manage.Controller;

import com.example.manage.Model.DepartmentModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


import static com.example.manage.Model.DepartmentModel.*;

public class DepartmentController {

    @FXML
    private TableView <Map<String,String>> DepartmentTable;

    @FXML
    private TableColumn <Map<String,String>,String> DepartmentIdCol;

    @FXML
    private TableColumn <Map<String,String>,String> DepartmentNameCol;

    @FXML
    private TableColumn <Map<String,String>,String> ManagerCol;

    @FXML
    private TextField DepartmentIDTextField;

    @FXML
    private TextField DepartmentNameTextField;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> managerCombobox;
    @FXML private Button addDepartmentButton;
    @FXML private Button deleteDepartmentButton;
    @FXML private Button editDepartmentButton;
    @FXML private Button showDepartmentDetailsButton;

    private ResourceBundle bundle = ResourceBundle.getBundle("messages");

    @FXML
    private ObservableList<Map<String, String>> departmentData = FXCollections.observableArrayList();

    @FXML
    private Main mainApp;

    private int userRole;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
@FXML
    public void initialize() {
        DepartmentIdCol.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().get("DepartmentID")));
        DepartmentNameCol.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().get("DepartmentName")));
        ManagerCol.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().get("Manager")));


        DepartmentTable.setItems(departmentData);
        showDepartment();
        loadManager();
        loadDepartmentSearch("");
    DepartmentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {showDepartmentintextfield(newValue);});
    configureUserPermissions();
}
    public void setUserRole(int role) {

        this.userRole = role;
        configureUserPermissions();
    }
    private void configureUserPermissions() {
        if (userRole ==1) {
            // Admin có thể thực hiện tất cả các chức năng
            addDepartmentButton.setDisable(false);
            deleteDepartmentButton.setDisable(false);
            editDepartmentButton.setDisable(false);
            showDepartmentDetailsButton.setDisable(false);


        } else {
            // Người dùng bình thường không thể thực hiện một số chức năng
            addDepartmentButton.setDisable(true);
            deleteDepartmentButton.setDisable(true);
            editDepartmentButton.setDisable(true);
            showDepartmentDetailsButton.setDisable(false);
        }
    }
public void showDepartment(){
    DepartmentModel.showDepartment(departmentData);
    }
public void loadManager(){
        ObservableList<String> Manager = DepartmentModel.getManager();
        managerCombobox.setItems(Manager);
}
public void addDepartment(ActionEvent event) {
        if (DepartmentIDTextField.getText().isEmpty() || DepartmentNameTextField.getText().isEmpty() || managerCombobox.getSelectionModel().isEmpty()){
         showErrorAlert("dien du thon tin");
         return;
    }
        Map<String, String> newDepartment = new HashMap<>();
        newDepartment.put("DepartmentID", DepartmentIDTextField.getText());
        newDepartment.put("DepartmentName", DepartmentNameTextField.getText());
        newDepartment.put("Manager", managerCombobox.getSelectionModel().getSelectedItem());
    if (isDepartmentIdExists(newDepartment.get("DepartmentID"))) {
        showErrorAlert("DepartmentID đã tồn tại. Vui lòng chọn một ID khác.");
        return;
    }
        departmentData.add(newDepartment);

        AddDepartment(newDepartment);
    showSuccess(bundle.getString("notification.success"));

}

 public void btndeleteDepartment(ActionEvent event){
    Map<String, String> selectedDepartment = DepartmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null){
            departmentData.remove(selectedDepartment);
            showSuccess(bundle.getString("notification.success"));
            DeleteDepartment(selectedDepartment);
        }
        else {
            // Hiển thị thông báo lỗi nếu không có mục nào được chọn
            showErrorAlert("Error");
        }

}
public void btnupdate(ActionEvent event) {
        Map<String, String> selectedDepartment = DepartmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null){
            try{
                int DepartmentId = Integer.parseInt(selectedDepartment.get("DepartmentID"));
                Map<String, String> newDepartment = new HashMap<>();
                newDepartment.put("DepartmentID", String.valueOf(DepartmentId));
                newDepartment.put("DepartmentName", DepartmentNameTextField.getText());
                newDepartment.put("Manager", managerCombobox.getSelectionModel().getSelectedItem());

                DepartmentModel model = new DepartmentModel();
                model.uploadDepartment(newDepartment);
                departmentData.clear();
                showDepartment();
                DepartmentTable.setItems(departmentData);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("notification.title"));
                alert.setHeaderText(null);
                alert.showAndWait();


            }catch(Exception e){
                showErrorAlert("Error");
            }
        }
}
    private void handleShowDetails(Map<String, String> selectedDepartment) {
        if (selectedDepartment != null) {
            mainApp.showDepartmentDetails(selectedDepartment);
        }
    }
public void showDepartmentDetails(ActionEvent event) {
        Map<String, String> selectedDepartment = DepartmentTable.getSelectionModel().getSelectedItem();
       handleShowDetails(selectedDepartment);
}

    private void clearFields() {
        DepartmentIDTextField.clear();
        DepartmentNameTextField.clear();
        managerCombobox.setValue(null);
    }
public void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
}
public void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(bundle.getString("notification.title"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
}
public void showDepartmentintextfield(Map<String,String> departmentData) {
        if(departmentData != null){
            DepartmentIDTextField.setText(departmentData.get("DepartmentID"));
            DepartmentNameTextField.setText(departmentData.get("DepartmentName"));
            managerCombobox.setValue(departmentData.get("Manager"));
        }
        else {
            clearFields();
        }
}
public void loadDepartmentSearch (String keyword){
        DepartmentModel.loaddepartmentSearch(departmentData,keyword);
}
public void handSearch(ActionEvent event){
        loadDepartmentSearch(searchField.getText().trim());
}
}
