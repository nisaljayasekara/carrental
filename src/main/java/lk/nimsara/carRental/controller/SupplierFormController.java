package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.SupplierDto;
import lk.nimsara.carRental.dto.tm.SupplierTm;
import lk.nimsara.carRental.model.CustomerModel;
import lk.nimsara.carRental.model.SupplierModel;
import javafx.scene.control.TableView;
import lk.nimsara.carRental.util.Utils;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierFormController {


    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TableColumn<?, ?> S_Email;

    @FXML
    private TableColumn<?, ?> S_Id;

    @FXML
    private TableColumn<?, ?> S_Name;

    @FXML
    private TableColumn<?, ?> S_Tel;

    @FXML
    private TextField textTel;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtUserId;

    @FXML
    private TextField txtName;

    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
        txtUserId.setText(Utils.userId);

    }


    private void setCellValueFactory() {
        S_Id.setCellValueFactory(new PropertyValueFactory<>("id"));
        S_Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        S_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        S_Tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
       // colUser_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));
    }

    private void loadAllSuppliers() {
        var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = model.getAllSuppliers();

            for (SupplierDto dto : dtoList) {
                obList.add(
                        new SupplierTm(
                                dto.getSupplier_id(),
                                dto.getSupplier_name(),
                                dto.getSupplier_email(),
                                dto.getTel(),
                                dto.getUser_id()


                        )
                );
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ClearbtnOnAction(ActionEvent event) {
        clearFields();


    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtEmail.setText("");
        textTel.setText("");
         //cmbUser_id.setValue(null);


    }

    @FXML
    void DeletebtnOnAction(ActionEvent event) {
        String supId = txtId.getText();

        SupplierModel supplierModel = new SupplierModel();

        try {
            boolean isDeleted = supplierModel.deleteSupplier(supId);
            if (isDeleted) {
                tblSupplier.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "supplier deleted!").show();
                loadAllSuppliers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }


    @FXML
    void SaveButtonOnAction(ActionEvent event) {
        String supId = txtId.getText();
        String supName = txtName.getText();
        String supEmail = txtEmail.getText();
        String tel = textTel.getText();
        String userId = txtUserId.getText();


        SupplierDto supplierDto = new SupplierDto(supId, supName, supEmail, tel, userId);

        SupplierModel supplierModel = new SupplierModel();

        try {
            boolean isSaved = supplierModel.saveSupplier(supplierDto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
                loadAllSuppliers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void SearchbtnOnAction(ActionEvent event) {

    }


    @FXML
    void UpadatebtnOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();
        String name = txtName.getText();
        String Email = txtEmail.getText();
        String Tel=textTel.getText();
        String U_Id=txtUserId.getText();



        var dto = new SupplierDto(id, name, Email,Tel,U_Id);

        var model = new SupplierModel();
        try {
            boolean isUpdated = model.updateSupplier(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void tblOnMouseClickAction(MouseEvent event) {
        txtId.setText(tblSupplier.getSelectionModel().getSelectedItem().getId());
        txtName.setText(tblSupplier.getSelectionModel().getSelectedItem().getName());
        txtEmail.setText(tblSupplier.getSelectionModel().getSelectedItem().getEmail());
        textTel.setText(tblSupplier.getSelectionModel().getSelectedItem().getTel());
        txtUserId.setText(tblSupplier.getSelectionModel().getSelectedItem().getUser_id());

    }
}

