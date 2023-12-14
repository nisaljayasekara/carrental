package lk.nimsara.carRental.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.nimsara.carRental.util.Navigation;

import java.io.IOException;

public class DashBoardFormController {

    @FXML
    private AnchorPane pagingPane;

    @FXML
    void btnHomeMouseOnExited(MouseEvent event) {

    }

    @FXML
    void btnStockOnAction(ActionEvent event) throws IOException {


        Navigation.switchPaging(pagingPane, "stockFrom.fxml");
    }


    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane, "customerForm.fxml");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml", event);
    }

   @FXML
    void btnHomeOnMouseEntered(MouseEvent event) {

   }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"employeeManagementForm.fxml");

    }
    @FXML
    void btnOnActionCar(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"reserveVehicleForm.fxml");
    }

    @FXML
    void btnOnActionSupplier(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"supplierForm.fxml");

    }
    @FXML
    void btnRepierOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"RapierForm.fxml");
    }


    @FXML
    public void btnAttendanceOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"attendance_form.fxml");
    }
    @FXML
    public void btnSalaryOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"salaryForm.fxml");
    }
    @FXML
    void btnAppoimentOnAction(ActionEvent event) throws IOException {
        Navigation.switchPaging(pagingPane,"appoiment.fxml");

    }
}
