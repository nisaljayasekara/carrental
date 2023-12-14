package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.tm.CustomerTm;
import lk.nimsara.carRental.model.CustomerModel;
import lk.nimsara.carRental.util.Navigation;
import lk.nimsara.carRental.util.Utils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private Button btnSearch;

    @FXML
    void btnBackOnAction(MouseEvent event) {
        Navigation.switchNavigation("loginPageForm.fxml", event);
    }

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> ColU_Id;


    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtUserId;


    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        txtUserId.setText(Utils.userId);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("Tel"));
        ColU_Id.setCellValueFactory(new PropertyValueFactory<>("User_id"));
    }

    private void loadAllCustomers() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = model.getAllCustomers();

            for (CustomerDto dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAddress(),
                                dto.getEmail(),
                                dto.getTel(),
                                dto.getUser_id()

                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        boolean isCustomerValidated = validateCustomer();
        if (isCustomerValidated) {
             new Alert(Alert.AlertType.INFORMATION, "Customer Saved Successfully!").show();

        }
    }


    private boolean validateCustomer() {
        String id = txtId.getText();
        boolean isCustomerIDValidated = Pattern.matches("[C][0-9]{3,}", id);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }

        String name = txtName.getText();
        boolean isCustomerNameValidated = Pattern.matches("^[A-Za-z\\s]+$", name);
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer Name").show();
            return false;
        }
        String address = txtAddress.getText();
        boolean isCustomerAddressValidated = Pattern.matches("^\\d+/\\d+,\\s?[A-Za-z0-9\\s.,'-]+|^[A-Za-z]+$", address);
        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }

        String Email = txtEmail.getText();
        boolean isCustomerEmailValidated = Pattern.matches( "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$", Email);
        if (!isCustomerEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer email").show();
            return false;
        }

        String Tel = txtTel.getText();
        boolean isCustomerTelValidated = Pattern.matches("[0-9]{10}", Tel);
        if (!isCustomerTelValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer Tel").show();
            return false;
        }


        String U_id = txtUserId.getText();
        boolean isCustomerUserIDValidated = Pattern.matches("[U][0-9]{3,}", U_id);
        if (!isCustomerUserIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }


        var dto = new CustomerDto(id, name, address, Email, Tel, U_id);

        var model = new CustomerModel();
        try {
            boolean isSaved = model.saveCustomer(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clearFields();
                tblCustomer.refresh();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return true;
    }


    @FXML
    void txtSearchOnAction(ActionEvent event) {


    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {

            String Customer_id = txtId.getText();

            var customerModel = new CustomerModel();
            try {
                boolean isDeleted = customerModel.deleteCustomer(Customer_id);

                if(isDeleted) {
                    tblCustomer.refresh();
                    new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String Email = txtEmail.getText();
        String Tel=txtTel.getText();
        String U_id=txtUserId.getText();


        boolean isCustomerValidated = validateUpdatedCustomer(id, name, address, Tel);

        if (isCustomerValidated) {

            var dto = new CustomerDto(id, name, address, Email,Tel,U_id);

            var model = new CustomerModel();
            try {
                boolean isUpdated = model.updateCustomer(dto);
                System.out.println(isUpdated);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
                    loadAllCustomers();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    private boolean validateUpdatedCustomer(String id, String name, String address, String Tel) {

        boolean isCustomerIDValidated = Pattern.matches("[C][0-9]{3,}", id);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }


        boolean isCustomerNameValidated = Pattern.matches("^[A-Za-z\\s]+$", name);
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Name!").show();
            return false;
        }


        boolean isCustomerAddressValidated = Pattern.matches("^\\d+/\\d+,\\s?[A-Za-z0-9\\s.,'-]+|^[A-Za-z]+$", address);
        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }


        boolean isCustomerTelValidated = Pattern.matches("[0-9]{10}", Tel);
        if (!isCustomerTelValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer Tel").show();
            return false;
        }
        String Email = txtEmail.getText();
        boolean isCustomerEmailValidated = Pattern.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+$", Email);
        if (!isCustomerEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer email").show();
            return false;
        } String U_id = txtUserId.getText();
        boolean isCustomerUserIDValidated = Pattern.matches("[U][0-9]{3,}", U_id);
        if (!isCustomerUserIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer ID!").show();
            return false;
        }


        return true;
    }





    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void tblOnMouseClickAction(MouseEvent event) {
        txtId.setText(tblCustomer.getSelectionModel().getSelectedItem().getId());
        txtName.setText(tblCustomer.getSelectionModel().getSelectedItem().getName());
        txtAddress.setText(tblCustomer.getSelectionModel().getSelectedItem().getAddress());
        txtEmail.setText(tblCustomer.getSelectionModel().getSelectedItem().getEmail());
        txtTel.setText(tblCustomer.getSelectionModel().getSelectedItem().getTel());
        //txtUserId.setText(tblCustomer.getSelectionModel().getSelectedItem().get());
    }
    private void fillFields(CustomerDto customerDto) {
        txtId.setText(customerDto.getId());
        txtName.setText(customerDto.getName());
        txtEmail.setText(customerDto.getEmail());
        txtAddress.setText(String.valueOf(customerDto.getAddress()));
        txtTel.setText(customerDto.getTel());
        txtUserId.setText(customerDto.getUser_id());

    }

    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtUserId.setText("");
        txtTel.setText("");
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String id = txtId.getText();

        CustomerModel customerModel = new CustomerModel();

        try {
            CustomerDto customerDto = customerModel.searchCustomer(id);


            if (customerDto != null) {
                fillFields(customerDto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Customer not found!").show();
                loadAllCustomers();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



}



