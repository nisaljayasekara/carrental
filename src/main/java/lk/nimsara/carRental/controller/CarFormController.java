package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.nimsara.carRental.dto.CarDto;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.tm.CarTm;
import lk.nimsara.carRental.dto.tm.CustomerTm;
import lk.nimsara.carRental.model.CarModel;
import lk.nimsara.carRental.model.CustomerModel;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CarFormController {


    @FXML
    private TableColumn<?, ?> ColCarPrice;

    @FXML
    private TableColumn<?, ?> ColAvailability;

    @FXML
    private TableColumn<?, ?> ColCarId;

    @FXML
    private TableColumn<?, ?> ColCarName;

    @FXML
    private TableView<CarTm> tblCar;

    @FXML
    private TextField txtAvailability;

    @FXML
    private TextField txtCarName;

    @FXML
    private TextField txtCarPrice;

    @FXML
    private TextField txtId;


    public void initialize() {
        setCellValueFactory();
        loadAllCars();

    }


    private void setCellValueFactory() {
        ColCarId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColAvailability.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        ColCarName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ColCarPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    private void loadAllCars() {
        var model = new CarModel();

        ObservableList<CarTm> obList = FXCollections.observableArrayList();

        try {
            List<CarDto> dtoList = CarModel.getAllCars();

            for (CarDto dto : dtoList) {
                obList.add(
                        new CarTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getAvailability(),
                                dto.getPrice()

                        )
                );
            }

            tblCar.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void clearFields() {
        txtId.setText("");
        txtCarName.setText("");
        txtAvailability.setText("");
        txtCarPrice.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String id = txtId.getText();

        CarModel carModel = new CarModel();

        try {
            boolean isisDeletedVehicle = carModel.deleteCar(id);

            if (isisDeletedVehicle) {
                tblCar.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Car deleted!").show();
                loadAllCars();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {


        boolean isCarValidated = validateCar();
        if (isCarValidated) {
            new Alert(Alert.AlertType.INFORMATION, "Car Saved Successfully!").show();

        }
    }

    private boolean validateCar() {
        String id = txtId.getText();
        boolean isCustomerIDValidated = Pattern.matches("[C][0-9]{3,}", id);
        if (!isCustomerIDValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Car ID!").show();
            return false;
        }
        String name = txtCarName.getText();
        boolean isCarNameValidated = Pattern.matches("^[A-Za-z\\s]+$", name);
        if (!isCarNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Car Name").show();
            return false;
        }
        String price = txtCarPrice.getText();
        boolean isCarTelValidated = Pattern.matches("^\\d+(\\.\\d{1,2})?$", price);
        if (!isCarTelValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Car price").show();
            return false;
        }
        String Availability = txtAvailability.getText();

        CarDto carDto = new CarDto(id, name, Availability, price);

        CarModel carModel = new CarModel();

        try {
            boolean isSaved = carModel.saveCar(carDto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Car saved!").show();
                clearFields();
                loadAllCars();
                tblCar.refresh();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
        return true;
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtCarName.getText();
        String Availability = txtAvailability.getText();
        String price = txtCarPrice.getText();

        CarDto CarDto =new CarDto(id,name,Availability,price);

        CarModel CarModel =new CarModel();

        try {
            boolean isUpdated =CarModel.updateCar(CarDto);


            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Car updated!").show();
                loadAllCars();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id  = txtId.getText();

        CarModel carModel =new CarModel();

        try {
            CarDto carDto = carModel.searchCar(id);

            if (carDto !=null){
                fillFields(carDto);
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Car not found!").show();
                loadAllCars();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void fillFields(CarDto CarDto) {
        txtId.setText(CarDto.getId());
        txtCarName.setText(CarDto.getName());
        txtAvailability.setText(CarDto.getAvailability());
        txtCarPrice.setText(CarDto.getPrice());

    }
    @FXML
    void tblOnMouseClickAction(MouseEvent event) {
            txtId.setText(tblCar.getSelectionModel().getSelectedItem().getId());
            txtCarName.setText(tblCar.getSelectionModel().getSelectedItem().getName());
            txtAvailability.setText(tblCar.getSelectionModel().getSelectedItem().getAvailability());
            txtCarPrice.setText(tblCar.getSelectionModel().getSelectedItem().getPrice());

        }



}