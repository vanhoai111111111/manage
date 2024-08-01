package com.example.manage.Controller;

import com.example.manage.Model.employeeModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.example.manage.Model.employeeModel.*;

public class EmployeeController {
    @FXML
    private TableView<Map<String, String>> employeeTable;

    @FXML
    private TableColumn<Map<String, String>, String> EmployeeIDCol;

    @FXML
    private TableColumn<Map<String, String>, String> EmployeeNameCol;

    @FXML
    private TableColumn<Map<String, String>, String> projectNameCol;

    @FXML
    private TableColumn<Map<String, String>, String> phoneCol;

    @FXML
    private ObservableList<Map<String, String>> Employeedata;

    @FXML
    private TextField searchField;

    @FXML
    private TextField employeeIDTextField;

    @FXML
    private TextField employeeNameTextField;

    @FXML
    private TextField searchtextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private ComboBox<String> projectComboBox;

    @FXML
    private ResourceBundle bundle = ResourceBundle.getBundle("messages");

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button showDetailsButton;

    @FXML
    private Main mainApp;

    private int userRole;

    @FXML
    public void initialize() {
        EmployeeIDCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("EmployeeID")));
        EmployeeNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("EmployeeName")));
        projectNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("ProjectName")));
        phoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("Phone")));

        Employeedata = FXCollections.observableArrayList();
        employeeTable.setItems(Employeedata);

        showdata();
        loadprojectNames();
        employeeTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showemployeeintextfield(newValue);
        });
        loadEmployeeData("");
        configureUserPermissions();
    }
    public void setUserRole(int role) {

        this.userRole = role;
        configureUserPermissions();
    }
    private void configureUserPermissions() {
        if (userRole ==1) {
            // Admin có thể thực hiện tất cả các chức năng
            addButton.setDisable(false);
            deleteButton.setDisable(false);
            editButton.setDisable(false);
            showDetailsButton.setDisable(false);


        } else {
            // Người dùng bình thường không thể thực hiện một số chức năng
            addButton.setDisable(true);
            deleteButton.setDisable(true);
            editButton.setDisable(true);
            showDetailsButton.setDisable(false);
        }
    }

    public void addEmployee(ActionEvent event) {
        if (employeeIDTextField.getText().isEmpty() || employeeNameTextField.getText().isEmpty() || phoneTextField.getText().isEmpty()) {
            showErrorAlert("Vui lòng điền đầy đủ thông tin.");
            return;
        }
        Map<String, String> newProject = new HashMap<>();
        newProject.put("EmployeeID", employeeIDTextField.getText());
        newProject.put("EmployeeName", employeeNameTextField.getText());
        newProject.put("ProjectName", projectComboBox.getValue());
        newProject.put("Phone", phoneTextField.getText());
        Employeedata.add(newProject);

        // Thêm vào cơ sở dữ liệu
        addEmployees(newProject);
        showSuccessAlert(bundle.getString("notification.success"));
        clearFields();
    }
    public void btndeleteEmployee(ActionEvent event) {
        Map<String, String> SelectedItem = employeeTable.getSelectionModel().getSelectedItem();
        if (SelectedItem != null) {
            Employeedata.remove(SelectedItem);
            showSuccessAlert(bundle.getString("notification.success"));
            deleteemployee(SelectedItem);

        }
    }
    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Lỗi");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(bundle.getString("notification.title"));
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadprojectNames() {
        ObservableList<String> projectName = employeeModel.getprojectName();
        projectComboBox.setItems(projectName);
    }

    private void showdata() {
        employeeModel.showdata(Employeedata);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    private void showemployeeintextfield(Map<String, String> Employeedata) {
        if (Employeedata != null) {
            employeeIDTextField.setText(Employeedata.get("EmployeeID"));
            employeeNameTextField.setText(Employeedata.get("EmployeeName"));
            projectComboBox.setValue(Employeedata.get("ProjectName"));
            phoneTextField.setText(Employeedata.get("Phone"));
        } else {
            clearFields();
        }
    }
    private void clearFields() {
        employeeIDTextField.clear();
        employeeNameTextField.clear();
        phoneTextField.clear();
        projectComboBox.setValue(null);
    }
    public void updateemployee(ActionEvent event) {
        Map<String, String> selectedItem = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try{
                int employeeID = Integer.parseInt(employeeIDTextField.getText().trim());
                Map<String, String> newEmployee = new HashMap<>();
                newEmployee.put("EmployeeID", String.valueOf(employeeID));
                newEmployee.put("EmployeeName", employeeNameTextField.getText().trim());
                newEmployee.put("ProjectName", projectComboBox.getValue());
                newEmployee.put("Phone", phoneTextField.getText());

                employeeModel model = new employeeModel();
                model.updateemployee(newEmployee);

                Employeedata.clear();
                showdata();
                employeeTable.setItems(Employeedata);
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle(bundle.getString("notification.success"));
                successAlert.setHeaderText(null);
                successAlert.setContentText(bundle.getString("notification.success"));
                successAlert.showAndWait();

            }catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("notification.error");
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("notification.error"));
                alert.showAndWait();
            }
        }

    }
    @FXML
    private void handleShowDetails(Map<String, String> selectedEmployee) {
        if (selectedEmployee != null) {
            mainApp.showEmployeeDetails(selectedEmployee);
        }
    }
    public void setEmployeeData(ObservableList<Map<String, String>> employeeData) {
        employeeTable.setItems(employeeData);
    }
    public void showEmployeeDetails(ActionEvent event) {
        Map<String, String> selectedItem = employeeTable.getSelectionModel().getSelectedItem();
        handleShowDetails(selectedItem);
    }
    public void loadEmployeeData(String keyword){

        employeeModel.loademployeedata(Employeedata, keyword);
    }
    public void handleSearch(ActionEvent event){

        loadEmployeeData(searchField.getText().trim());
    }

}