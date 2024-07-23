package com.example.manage.Controller;

import com.example.manage.Model.projectModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
   private TextField tennvTextField;

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

      loadduanData();
      //show dự án Tableview ->TextField
      project.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         showduan(newValue); // newValue is the newly selected map
      });
   }
   public void setMainApp(Main mainApp) {
      this.mainApp = mainApp;
      this.bundle = mainApp.getBundle(); // Lấy ResourceBundle từ mainApp
   }
   private void loadduanData() {
      projectModel.loadduanData(duanData);
   }


//them du an
   public void addProject(ActionEvent event) {
if (idduanTextField.getText().isEmpty() || tenduanTextField.getText().isEmpty() || tennvTextField.getText().isEmpty()) {
   showErrorAlert("Vui lòng điền đầy đủ thông tin.");
   return;
}
    Map<String, String> newProject = new HashMap<>();
      newProject.put("projectId", idduanTextField.getText());
      newProject.put("projectName", tenduanTextField.getText());
      newProject.put("employeeName", tennvTextField.getText());
           duanData.add(newProject);

      // Add to database
      addproject(newProject);
      Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
      successAlert.setTitle(bundle.getString("notification.title"));
      successAlert.setHeaderText(null);
      successAlert.setContentText(bundle.getString("notification.success"));
      successAlert.showAndWait();
   }

   public void btndelete(ActionEvent event) {
      Map<String, String> selectedItem = project.getSelectionModel().getSelectedItem();
      FXMLLoader loader = new FXMLLoader();
      loader.setResources(bundle);
      if (selectedItem != null) {
         // Remove from TableView
         duanData.remove(selectedItem);

         // Remove from database (assuming you have a method to delete from database)
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
      FXMLLoader loader = new FXMLLoader();
      loader.setResources(bundle);
      if (selectedItem != null) {
         try {
            int projectId = Integer.parseInt(idduanTextField.getText().trim()); // Loại bỏ khoảng trắng và chuyển đổi
            Map<String, String> newProject = new HashMap<>();
            newProject.put("projectId", String.valueOf(projectId)); // Lưu dưới dạng chuỗi nếu cần
            newProject.put("projectName", tenduanTextField.getText().trim()); // Loại bỏ khoảng trắng
            newProject.put("employeeName", tennvTextField.getText().trim()); // Loại bỏ khoảng trắng

            projectModel model = new projectModel();
            model.updateproject(newProject);

            // Tải lại dữ liệu từ cơ sở dữ liệu
            duanData.clear();
            loadduanData();

            // Cập nhật lại TableView
            project.setItems(duanData);
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle(bundle.getString("notification.title"));
            successAlert.setHeaderText(null);
            successAlert.setContentText(bundle.getString("notification.success"));
            successAlert.showAndWait();
         } catch (NumberFormatException e) {
            // Hiển thị thông báo lỗi cho người dùng
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Định dạng projectId không hợp lệ: " + e.getMessage());
            alert.showAndWait();
         }
      }
   }
   private void showduan(Map<String, String> duanData) {

      if (duanData != null) {
         idduanTextField.setText(duanData.get("projectId"));
         tenduanTextField.setText(duanData.get("projectName"));
         tennvTextField.setText(duanData.get("employeeName"));
      } else {
         clearFields();
      }
   }

   private void clearFields() {
      idduanTextField.clear();
      tenduanTextField.clear();
      tennvTextField.clear();
   }
   private void showErrorAlert(String message) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Lỗi");
      alert.setHeaderText(null);
      alert.setContentText(message);
      alert.showAndWait();
   }
   }
