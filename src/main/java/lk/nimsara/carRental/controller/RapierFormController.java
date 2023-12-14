package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.RapierDto;
import lk.nimsara.carRental.dto.tm.CustomerTm;
import lk.nimsara.carRental.dto.tm.RapierTm;
import lk.nimsara.carRental.model.CustomerModel;
import lk.nimsara.carRental.model.RapierModel;
import lk.nimsara.carRental.util.Utils;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class RapierFormController {

    @FXML
    private TableColumn<?, ?> colCarId;

    @FXML
    private TableColumn<?, ?> colRapierDesc;

    @FXML
    private TableColumn<?, ?> colRapierId;

    @FXML
    private TableColumn<?, ?> colRapierPrice;

    @FXML
    private TableColumn<?, ?> colRapierReturnDate;

    @FXML
    private TableColumn<?, ?> colRapierdate;

    @FXML
    private TableView<RapierTm> tblRapier;

    @FXML
    private TextField txtCarId;

    @FXML
    private TextField txtRapierId;

    @FXML
    private TextField txtRapierPrice;

    @FXML
    private DatePicker txtRapierReturnDate;

    @FXML
    private DatePicker txtRapierdate;

    @FXML
    private TextField txtRapierdesc;

    public void initialize() {
        setCellValueFactory();
        loadAllRapiers();

    }

    private void setCellValueFactory() {
        colRapierId.setCellValueFactory(new PropertyValueFactory<>("Rapier_id"));
        colRapierdate.setCellValueFactory(new PropertyValueFactory<>("Rapier_date"));
        colRapierDesc.setCellValueFactory(new PropertyValueFactory<>("Rapier_desc"));
        colRapierReturnDate.setCellValueFactory(new PropertyValueFactory<>("Rapier_returnDate"));
        colRapierPrice.setCellValueFactory(new  PropertyValueFactory<>("Rapier_price"));
        colCarId.setCellValueFactory(new  PropertyValueFactory<>("Car_id"));
    }
    private void loadAllRapiers() {
        var model = new RapierModel();

        ObservableList<RapierTm> obList = FXCollections.observableArrayList();

        try {
            List<RapierDto> dtoList = model.loadAllRapiers();

            for(RapierDto dto : dtoList) {
                obList.add(
                        new RapierTm(
                                dto.getRapier_id(),
                                dto.getRapier_date(),
                                dto.getRapier_desc(),
                                dto.getRapier_returnDate(),
                                dto.getRapier_price(),
                                dto.getCar_id()

                        )
                );
            }

            tblRapier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtRapierId.setText("");
        txtCarId.setText("");
        txtRapierdesc.setText("");
        txtRapierPrice.setText("");
        txtRapierdate.setValue(null);
        txtRapierReturnDate.setValue(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String Rapier_id = txtRapierId.getText();

        var rapierModel = new RapierModel();
        try {
            boolean isDeleted = rapierModel.deleteRapier(Rapier_id);

            if(isDeleted) {
                tblRapier.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "rapier deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String Rapier_id = txtRapierId.getText();
        String Rapier_date = txtRapierdate.getValue().toString();
        String Rapier_desc = txtRapierdesc.getText();
        String Rapier_returnDate= txtRapierReturnDate.getValue().toString();
        String Rapier_price=txtRapierPrice.getText();
        String Car_id=txtCarId.getText();


        var dto = new RapierDto(Rapier_id, Rapier_date, Rapier_desc,Rapier_returnDate,Rapier_price,Car_id);

        var model = new RapierModel();
        try {
            boolean isSaved = model.saveRapier(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Rapier  saved!").show();
               // clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnUpdateOnaAction(ActionEvent event) {
        String Rapier_id = txtRapierId.getText();
        String Rapier_date = txtRapierdate.getValue().toString();
        String Rapier_desc = txtRapierdesc.getText();
        String Rapier_returnDate= txtRapierReturnDate.getValue().toString();
        String Rapier_price=txtRapierPrice.getText();
        String Car_id=txtCarId.getText();


        var dto = new RapierDto(Rapier_id, Rapier_date, Rapier_desc, Rapier_returnDate,Rapier_price,Car_id);

        var model = new RapierModel();
        try {
            boolean isUpdated = model.updateRapier(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "rapier updated!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }
    @FXML
    void tblOnMouseClickAction(MouseEvent event) {
        txtRapierId.setText(tblRapier.getSelectionModel().getSelectedItem().getRapier_id());
        txtRapierdate.setValue(LocalDate.parse(tblRapier.getSelectionModel().getSelectedItem().getRapier_date()));
        txtRapierdesc.setText(tblRapier.getSelectionModel().getSelectedItem().getRapier_desc());
        txtRapierReturnDate.setValue(LocalDate.parse(tblRapier.getSelectionModel().getSelectedItem().getRapier_returnDate()));
        txtRapierPrice.setText(tblRapier.getSelectionModel().getSelectedItem().getRapier_price());
        txtCarId.setText(tblRapier.getSelectionModel().getSelectedItem().getCar_id());
    }


}
