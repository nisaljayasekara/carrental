package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.EmployeeDto;
import lk.nimsara.carRental.dto.tm.CustomerTm;
import lk.nimsara.carRental.dto.tm.EmployeeTm;
import lk.nimsara.carRental.model.CustomerModel;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colEmployee_address;

    @FXML
    private TableColumn<?, ?> colEmployee_id;

    @FXML
    private TableColumn<?, ?> colEmployee_job_category;

    @FXML
    private TableColumn<?, ?> colEmployee_name;



    @FXML
    private TableColumn<?, ?> colcontactnum;

    @FXML
    private AnchorPane rootNode;

    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmployee_address;

    @FXML
    private TextField txtEmployee_id;

    @FXML
    private TextField txtEmployee_job_category;

    @FXML
    private TextField txtEmployee_name;

    @FXML
    private TextField txtcontactnum;




    public void initialize() {
        setCellValueFactory();
        loadAllEmployees();
    }


    private void setCellValueFactory() {
        colEmployee_id.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));
        colEmployee_name.setCellValueFactory(new PropertyValueFactory<>("Employee_name"));
        colEmployee_address.setCellValueFactory(new PropertyValueFactory<>("Employee_address"));
        colEmployee_job_category.setCellValueFactory(new PropertyValueFactory<>("Employee_job_category"));
        colcontactnum.setCellValueFactory(new PropertyValueFactory<>("Employee_contactNum"));
  }



    private void loadAllEmployees() {
        var model = new lk.nimsara.carrental.model.EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> dtoList = model.getAllEmployee();

            for(EmployeeDto dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getEmployee_id(),
                                dto.getEmployee_name(),
                                dto.getEmployee_address(),
                                dto.getEmployee_job_category(),
                                dto.getEmployee_contactNum()
                        )
                );
            }

            tblEmployee .setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnAttendanceOnAction(ActionEvent event) {

    }

    @FXML
    void btnEmployeeDeleteOnAction(ActionEvent event) {
        String id =txtEmployee_id.getText();

        lk.nimsara.carrental.model.EmployeeModel employeeModel =new lk.nimsara.carrental.model.EmployeeModel();

        try {
            boolean isDeleted= employeeModel.deleteEmployee(id);
            if (isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }




    @FXML
    void btnEmployeeSaveOnAction(ActionEvent event) {

        boolean isEmployeeValidated = validateEmployee();
        if (isEmployeeValidated) {
             new Alert(Alert.AlertType.INFORMATION, "Employee Saved Successfully!").show();

        }
    }

    private boolean validateEmployee() {
        String id = txtEmployee_id.getText();
        boolean isEmployeeIDValidated = Pattern.matches("[E][0-9]{3,}", id);
        if (!isEmployeeIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }
        String name = txtEmployee_name.getText();
        boolean isCustomerNameValidated = Pattern.matches("^[A-Za-z\\s]+$", name);
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer mail").show();
            return false;
        }
        String address = txtEmployee_address.getText();
        boolean isCustomerAddressValidated = Pattern.matches("^\\d+/\\d+,\\s?[A-Za-z0-9\\s.,'-]+|^[A-Za-z]+$", address);
        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }

        String job = txtEmployee_job_category.getText();
        boolean isCustomerjobValidated = Pattern.matches("^\\d+/\\d+,\\s?[A-Za-z0-9\\s.,'-]+|^[A-Za-z]+$", job);
        if (!isCustomerjobValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer job").show();
            return false;


        } String contact = txtcontactnum.getText();
        boolean isCustomerTelValidated = Pattern.matches("[0-9]{10}", contact);
        if (!isCustomerTelValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer Tel").show();
            return false;
        }

        var dto = new EmployeeDto(id, name, address, job,contact);

        var model = new lk.nimsara.carrental.model.EmployeeModel();
        try {
            boolean isSaved = model.saveEmployee(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved!").show();
                loadAllEmployees();
                clearFields();
                tblEmployee.refresh();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return true;
    }






    @FXML
    void btnEmployeeUpdateOnAction(ActionEvent event) {
       /* String id =txtEmployee_id.getText();
        String name =txtEmployee_name.getText();
        String address =txtEmployee_address.getText();
        String job =txtEmployee_job_category.getText();
        String contact =txtcontactnum.getText();

        EmployeeDto employeeDto = new EmployeeDto(id,name,address,job,contact);

        lk.nimsara.carrental.model.EmployeeModel employeeModel =new lk.nimsara.carrental.model.EmployeeModel();

        try {
            boolean isUpdated =employeeModel.updateEmployee(employeeDto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }*/
        String id =txtEmployee_id.getText();
        String name =txtEmployee_name.getText();
        String address =txtEmployee_address.getText();
        String job =txtEmployee_job_category.getText();
        String contact =txtcontactnum.getText();

        boolean isEmployeeValidated = validateUpdatedEmployee(id, name, address, job,contact);

        if (isEmployeeValidated) {

            var dto = new EmployeeDto(id, name, address, job,contact);

            var model = new lk.nimsara.carrental.model.EmployeeModel();
            try {
                boolean isUpdated = model.updateEmployee(dto);
                System.out.println(isUpdated);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated!").show();
                    loadAllEmployees();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    private boolean validateUpdatedEmployee(String id, String name, String address, String job,String contact) {

        boolean isEmployeeIDValidated = Pattern.matches("[E][0-9]{3,}", id);
        if (!isEmployeeIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee ID!").show();
            return false;
        }


        boolean isEmployeeNameValidated = Pattern.matches("^[A-Za-z\\s]+$", name);
        if (!isEmployeeNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee Name!").show();
            return false;
        }


        boolean isEmployeeAddressValidated = Pattern.matches("^\\d+/\\d+,\\s?[A-Za-z0-9\\s.,'-]+|^[A-Za-z]+$", address);
        if (!isEmployeeAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid customer address").show();
            return false;
        }


        boolean isEmployeecontactValidated = Pattern.matches("[0-9]{10}", contact);
        if (!isEmployeecontactValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Employee contact").show();
            return false;
        }

        boolean isEmployeejobValidated = Pattern.matches("^\\d+/\\d+,\\s?[A-Za-z0-9\\s.,'-]+|^[A-Za-z]+$", job);
        if (!isEmployeejobValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid job address").show();
            return false;
        }


        return true;
    }



    @FXML
    void btnEmployeeclearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        txtEmployee_id.setText("");
        txtEmployee_name.setText("");
        txtEmployee_address.setText("");
        txtEmployee_job_category.setText("");
        txtcontactnum.setText("");
    }

    @FXML
    void tblOnMouseClickAction(MouseEvent event) {
        txtEmployee_id.setText(tblEmployee.getSelectionModel().getSelectedItem().getEmployee_id());
        txtEmployee_name.setText(tblEmployee.getSelectionModel().getSelectedItem().getEmployee_name());
        txtEmployee_address.setText(tblEmployee.getSelectionModel().getSelectedItem().getEmployee_address());
        txtEmployee_job_category.setText(tblEmployee.getSelectionModel().getSelectedItem().getEmployee_job_category());
        txtcontactnum.setText(tblEmployee.getSelectionModel().getSelectedItem().getEmployee_contactNum());


    }

}
