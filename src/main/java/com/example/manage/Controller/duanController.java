package com.example.manage.Controller;

import com.example.manage.Model.projectModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;

import static com.example.manage.Model.projectModel.*;

public class duanController {

   @FXML
   private TableView<Map<String, String>> project;

   @FXML
   private TableColumn<Map<String, String>, String> idproject;

   @FXML
   private TableColumn<Map<String, String>, String> projectName;
   @FXML
   private TableColumn<Map<String, String>, String> employeeName;
   @FXML
   private ObservableList<Map<String, String>> duanData;

   @FXML
   private TextField idduanTextField;

   @FXML
   private TextField tenduanTextField;

   @FXML
   private TextField searchField;

   @FXML
   private ComboBox<String> employeeComboBox;

   @FXML
   private Main mainApp;

   @FXML
   private ResourceBundle bundle = ResourceBundle.getBundle("messages");

   @FXML
   public void initialize() {
      idproject.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("projectId")));
      projectName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("projectName")));
      employeeName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("employeeName")));

      duanData = FXCollections.observableArrayList();
      project.setItems(duanData);

      showdata();
      loadEmployeeNames();

      //show dự án Tableview ->TextField
      project.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         showduan(newValue); // newValue is the newly selected map
      });
   }

   public void setMainApp(Main mainApp) {
      this.mainApp = mainApp;
      this.bundle = mainApp.getBundle(); // Lấy ResourceBundle từ mainApp
   }



   private void loadEmployeeNames() {
      ObservableList<String> employeeNames = projectModel.getEmployeeNames();
      employeeComboBox.setItems(employeeNames);
   }

   // Thêm dự án
   public void addProject(ActionEvent event) {
      if (idduanTextField.getText().isEmpty() || tenduanTextField.getText().isEmpty() || employeeComboBox.getValue() == null) {
         showErrorAlert("Vui lòng điền đầy đủ thông tin.");
         return;
      }
      Map<String, String> newProject = new HashMap<>();
      newProject.put("projectId", idduanTextField.getText());
      newProject.put("projectName", tenduanTextField.getText());
      newProject.put("employeeName", employeeComboBox.getValue());
      duanData.add(newProject);

      // Thêm vào cơ sở dữ liệu
      addproject(newProject);
      Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
      successAlert.setTitle(bundle.getString("notification.title"));
      successAlert.setHeaderText(null);
      successAlert.setContentText(bundle.getString("notification.success"));
      successAlert.showAndWait();
   }

   public void btndelete(ActionEvent event) {
      Map<String, String> selectedItem = project.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
         duanData.remove(selectedItem);
         deleteProject(selectedItem);
         Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
         successAlert.setTitle(bundle.getString("notification.title"));
         successAlert.setHeaderText(null);
         successAlert.setContentText(bundle.getString("notification.success"));
         successAlert.showAndWait();
      }
   }

   public void btnupdate(ActionEvent event) {
      Map<String, String> selectedItem = project.getSelectionModel().getSelectedItem();
      if (selectedItem != null) {
         try {
            int projectId = Integer.parseInt(idduanTextField.getText().trim());
            Map<String, String> newProject = new HashMap<>();
            newProject.put("projectId", String.valueOf(projectId));
            newProject.put("projectName", tenduanTextField.getText().trim());
            newProject.put("employeeName", employeeComboBox.getValue().trim());

            projectModel model = new projectModel();
            model.updateproject(newProject);

            duanData.clear();
            showdata();

            project.setItems(duanData);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(bundle.getString("notification.title"));
            successAlert.setHeaderText(null);
            successAlert.setContentText(bundle.getString("notification.success"));
            successAlert.showAndWait();
         } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("notification.error");
            alert.setHeaderText(null);
            alert.setContentText(bundle.getString("notification.error"));
            alert.showAndWait();
         }
      }
   }

   private void showduan(Map<String, String> duanData) {
      if (duanData != null) {
         idduanTextField.setText(duanData.get("projectId"));
         tenduanTextField.setText(duanData.get("projectName"));
         employeeComboBox.setValue(duanData.get("employeeName"));
      } else {
         clearFields();
      }
   }

   private void clearFields() {
      idduanTextField.clear();
      tenduanTextField.clear();
      employeeComboBox.setValue(null);
   }

   private void showErrorAlert(String message) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
   }

   private void loadduanData(String keyword) {
      projectModel.loadduanData(duanData, keyword);
   }

   @FXML
   private void handleSearch(ActionEvent event) {
      loadduanData(searchField.getText().trim());
   }
   private void showdata(){
      projectModel.showdata(duanData);
   }
   public void showProjectDetails(ActionEvent event) {
      Map<String, String> selectedItem = project.getSelectionModel().getSelectedItem();
      mainApp.showProjectDetails(selectedItem);
   }

}
