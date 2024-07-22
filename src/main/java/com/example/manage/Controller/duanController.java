package com.example.manage.Controller;

import com.example.manage.Model.DatabaseUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Map;

import static com.example.manage.Model.DatabaseUtil.addproject;
import static com.example.manage.Model.DatabaseUtil.deleteduan;


public class duanController {

   @FXML
   private TableView<Map<String, String>> duan;

   @FXML
   private TableColumn<Map<String, String>, String> idColumn;

   @FXML
   private TableColumn<Map<String, String>, String> tenduan;
   @FXML
   private TableColumn<Map<String, String>, String> tennv;
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
   public void initialize() {
      idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("id")));
      tenduan.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("tenduan")));
      tennv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("tennv")));

      duanData = FXCollections.observableArrayList();
      duan.setItems(duanData);

      loadduanData();
      //show dự án Tableview ->TextField
      duan.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
         showduan(newValue); // newValue is the newly selected map
      });
   }
   public void setMainApp(Main mainApp) {
      this.mainApp = mainApp;
   }
   private void loadduanData() {
      DatabaseUtil.loadduanData(duanData);
   }


//them du an
   public void addProject(ActionEvent event) {
if (idduanTextField.getText().isEmpty() || tenduanTextField.getText().isEmpty() || tennvTextField.getText().isEmpty()) {
   showErrorAlert("Vui lòng điền đầy đủ thông tin.");
   return;
}
    Map<String, String> newProject = new HashMap<>();
      newProject.put("id", idduanTextField.getText());
      newProject.put("tenduan", tenduanTextField.getText());
      newProject.put("tennv", tennvTextField.getText());
           duanData.add(newProject);

      // Add to database
      addproject(newProject);
   }

   public void btndelete(ActionEvent event) {
      Map<String, String> selectedItem = duan.getSelectionModel().getSelectedItem();

      if (selectedItem != null) {
         // Remove from TableView
         duanData.remove(selectedItem);

         // Remove from database (assuming you have a method to delete from database)
         deleteduan(selectedItem);
      }
   }

   private void showduan(Map<String, String> duanData) {

      if (duanData != null) {
         idduanTextField.setText(duanData.get("id"));
         tenduanTextField.setText(duanData.get("tenduan"));
         tennvTextField.setText(duanData.get("tennv"));
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
