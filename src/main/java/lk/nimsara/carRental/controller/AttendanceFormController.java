package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.nimsara.carRental.dto.AttendanceDto;
import lk.nimsara.carRental.dto.EmployeeDto;
import lk.nimsara.carRental.dto.tm.AttendanceTm;
import lk.nimsara.carRental.model.AttendanceModel;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

public class AttendanceFormController {


    @FXML
    private ComboBox<String> cmbEmployee_id;


    @FXML
    private TableColumn<?, ?> colAttendance_date;

    @FXML
    private TableColumn<?, ?> colAttendance_id;

    @FXML
    private TableColumn<?, ?> colAttendance_time;

    @FXML
    private TableColumn<?, ?> colEmployee_id;

    @FXML
    private DatePicker dateSelect;

    @FXML
    private TableView<AttendanceTm> tblAttendance;

    @FXML
    private TextField txtAttendance_id;

    @FXML
    private TextField txtTime;


    private final lk.nimsara.carrental.model.EmployeeModel employeeModel = new lk.nimsara.carrental.model.EmployeeModel();

    private final AttendanceModel attendanceModel = new AttendanceModel();

    public void initialize() {
        setCellValueFactory();
        loadAllAttendance();
        loadEmployeeIds();

    }

    private void loadEmployeeIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> empList = employeeModel.loadAllEmployees();
            for (EmployeeDto employeeDto:empList){
                obList.add(employeeDto.getEmployee_id());
            }
            cmbEmployee_id.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colAttendance_id.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
        colAttendance_date.setCellValueFactory(new PropertyValueFactory<>("attendanceDate"));
        colAttendance_time.setCellValueFactory(new PropertyValueFactory<>("attendanceTime"));
        colEmployee_id.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
    }

    private void loadAllAttendance() {
        AttendanceModel attendanceModel1 = new AttendanceModel();
        ObservableList<AttendanceTm> oblist =FXCollections.observableArrayList();

        try {
            List<AttendanceDto> attendanceList =attendanceModel1.getAllAttendance();

            for (AttendanceDto attendanceDto:attendanceList){
                AttendanceTm attendanceTm =new AttendanceTm(attendanceDto.getAttendanceId(),
                        attendanceDto.getAttendanceDate(),
                        attendanceDto.getAttendanceTime(),
                        attendanceDto.getEmployeeId());

                oblist.add(attendanceTm);
            }
            tblAttendance.setItems(oblist);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnEmployeeCleanOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtAttendance_id.setText("");
        dateSelect.setValue(null);
        txtTime.setText("");
        cmbEmployee_id.setValue(null);
    }

    @FXML
    void btnEmployeeDeleteOnAction(ActionEvent event) {
        String id =txtAttendance_id.getText();

        AttendanceModel attendanceModel1 =new AttendanceModel();

        try {
            boolean isDeleted =attendanceModel1.deleteAttendance(id);

            if (isDeleted){
                tblAttendance.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "attendance deleted!").show();
                loadAllAttendance();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnEmployeeSaveOnAction(ActionEvent event) {

        String id =txtAttendance_id.getText();
        LocalDate attendanceDate =dateSelect.getValue();
        String time =txtTime.getText();
        String empID =cmbEmployee_id.getValue();

        AttendanceDto attendanceDto =new AttendanceDto(id,attendanceDate,time,empID);

        AttendanceModel attendanceModel1 =new AttendanceModel();

        try {
            boolean isSaved =attendanceModel1.saveAttendance(attendanceDto);
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "attendance saved!").show();
                loadAllAttendance();

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnEmployeeUpdateOnAction(ActionEvent event) {
        String id =txtAttendance_id.getText();
        LocalDate attendanceDate = dateSelect.getValue();
        String time =txtTime.getText();
        String Employee_id =cmbEmployee_id.getValue();


        AttendanceDto attendanceDto = new AttendanceDto(id,attendanceDate,time,Employee_id);

        AttendanceModel attendancModel =new AttendanceModel();

        try {
            boolean isUpdated =attendancModel.updateAttendance(attendanceDto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "attendance  updated!").show();
                loadAllAttendance();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void cmbEmployeeOnAction(ActionEvent event) throws SQLException {


        String id =cmbEmployee_id.getValue();
        EmployeeDto employeeDto =employeeModel.searchEmployee(id);

    }
    @FXML
    void tblOnMouseClickAction(MouseEvent event) {
        txtAttendance_id.setText(tblAttendance.getSelectionModel().getSelectedItem().getAttendanceId());
        txtTime.setText(tblAttendance.getSelectionModel().getSelectedItem().getAttendanceTime());
      // cmbEmployee_id.getValue(tblAttendance.getSelectionModel().getSelectedItem().getEmployeeId());
       // dateSelect.getValue(tblAttendance.getSelectionModel().getSelectedItem().getAttendanceDate());



    }


}

