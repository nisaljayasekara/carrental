package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.SalaryDto;
import lk.nimsara.carRental.dto.tm.SalaryTm;
import lk.nimsara.carRental.model.CustomerModel;
import lk.nimsara.carRental.model.SalaryModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryFormController {

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colSalaryId;

    @FXML
    private TextField txtEmpId;


    @FXML
    private TableView<SalaryTm> tblsalary;

    @FXML
    private TextField txtSalaryAmount;

    @FXML
    private DatePicker txtSalaryDate;

    @FXML
    private TextField txtSalaryId;


    public void initialize() {
        setCellValueFactory();
        loadAllSalaryes();

    }

    private void setCellValueFactory() {
        colSalaryId.setCellValueFactory(new PropertyValueFactory<>("Salary_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("Salary_date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("SalaryAmount"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("Employee_id"));

    }

    private void loadAllSalaryes() {
        var model = new SalaryModel();

        ObservableList<SalaryTm> obList = FXCollections.observableArrayList();

        try {
            List<SalaryDto> dtoList = model.getAllSalaryes();

            for (SalaryDto dto : dtoList) {
                obList.add(
                        new SalaryTm(
                                dto.getSalary_id(),
                                dto.getSalary_date(),
                                dto.getSalaryAmount(),
                                dto.getEmployee_id()


                        )
                );
            }

            tblsalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   @FXML
    void btnUpdateOnAction(ActionEvent event) {
       print();

    }
    @FXML
    void btnupdateOnAction(ActionEvent event) {
        String id = txtSalaryId.getText();
        String date = String.valueOf(txtSalaryDate.getValue());
        String  Amount= txtSalaryAmount.getText();
        String EmpId = txtEmpId.getText();



        var dto = new SalaryDto(id, date, Amount, EmpId);

        var model = new SalaryModel();
        try {
            boolean isUpdated = model.updateSalary(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary updated!").show();
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
    void btnDeleteOnAction(ActionEvent event) {

        String Salary_id = txtSalaryId.getText();

        var salaryModel = new SalaryModel();
        try {
            boolean isDeleted = salaryModel.deleteSalary(Salary_id);

            if(isDeleted) {
                tblsalary.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "salary deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtSalaryId.getText();
        String date = String.valueOf(txtSalaryDate.getValue());
        String Amount = txtSalaryAmount.getText();
        String EmpId = txtEmpId.getText();


        var dto = new SalaryDto(id, date, Amount, EmpId);

        var model = new SalaryModel();
        try {
            boolean isSaved = model.saveSalary(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "salary saved!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    void clearFields() {
        txtSalaryId.setText("");
        txtSalaryDate.setValue(null);
        txtSalaryAmount.setText("");
        txtEmpId.setText("");
    }
    @FXML
    void tblMouseClickAction(MouseEvent event) {
        txtSalaryId.setText(tblsalary.getSelectionModel().getSelectedItem().getSalary_id());
        txtSalaryDate.setValue(LocalDate.parse(tblsalary.getSelectionModel().getSelectedItem().getSalary_date()));
        txtSalaryAmount.setText(tblsalary.getSelectionModel().getSelectedItem().getSalaryAmount());
        txtEmpId.setText(tblsalary.getSelectionModel().getSelectedItem().getEmployee_id());


    }


    public void print(){

        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/SalaryReport.jrxml");

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("SELECT * FROM salary");
            jasperDesign.setQuery(jrDesignQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String, Object> parameters = new HashMap<>();
            // 3. Add a parameter and set its value
            parameters.put("Emp_id", txtEmpId.getText());
            parameters.put("Salary_Id", txtSalaryId.getText());
            parameters.put("Salary_date", txtSalaryDate.getValue());
            parameters.put("Amount", txtSalaryAmount.getText());

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                            jasperReport, //compiled report
                            parameters,
                            DbConnection.getInstance().getConnection() //database connection
                    );

            JFrame frame = new JFrame("Jasper Report Viewer");
            JRViewer viewer = new JRViewer(jasperPrint);

            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(viewer);
            frame.setSize(new Dimension(1200, 800));
            frame.setVisible(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}



