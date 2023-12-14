package lk.nimsara.carRental.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.dto.AppoimentDto;
import lk.nimsara.carRental.dto.CarDto;
import lk.nimsara.carRental.dto.CustomerDto;
import lk.nimsara.carRental.dto.tm.AppoimentTm;
import lk.nimsara.carRental.model.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AppoimentFormController {


    @FXML
    private ComboBox<String> cmbCar_id;

    @FXML
    private ComboBox<String> cmbCustId;


    @FXML
    private TableColumn<?, ?> colAppoimentDate;

    @FXML
    private TableColumn<?, ?> colAppoimentreturntime;

    @FXML
    private TableColumn<?, ?> colAppoimentId;

    @FXML
    private TableColumn<?, ?> colAppoimentTime;

    @FXML
    private TableColumn<?, ?> colCarId;

    @FXML
    private TableColumn<?, ?> colCarName;

    @FXML
    private TableColumn<?, ?> colCarPrice;

    @FXML
    private TableColumn<?, ?> colTotalPrice;

    @FXML
    private TableColumn<?, ?> colAppoimentreturndate;

    @FXML
    private Label lblAppoimentId;

    @FXML
    private Label lblCarName;

    @FXML
    private Label lblCarPrice;

    @FXML
    private TableView<AppoimentTm> tblAppoiment;

    @FXML
    private DatePicker txtAppoimentReturnDate;

    @FXML
    private DatePicker txtAppoimentDate;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TextField txtAppoimentReturnTime;

    @FXML
    private TextField txtAppoimentTime;

    @FXML
    private TextField txtTotalPrice;

    private AppoimentTransAction transAction =new AppoimentTransAction();
    //private RepairTransAction transActions =new RepairTransAction();

    public void initialize() {
        setCellValueFactory();
        loadAllAppoiment();
        lblAppoimentId.setText(autoGenerateID());
        loadCarIds();
        loadCustomerIds();

    }

    private void loadCarIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CarDto> carList = CarModel.loadAllCars();
            for (CarDto carDto:carList){
                obList.add(carDto.getId());
            }
            cmbCar_id.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> custList = CustomerModel.loadAllCustomers();
            for (CustomerDto customerDto:custList){
                obList.add(customerDto.getId());
            }
            cmbCustId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private void setCellValueFactory() {
        colAppoimentId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colAppoimentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAppoimentTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colCarId.setCellValueFactory(new PropertyValueFactory<>("Car_id"));
        colCarName.setCellValueFactory(new  PropertyValueFactory<>("Car_name"));
        colAppoimentreturntime.setCellValueFactory(new  PropertyValueFactory<>("Appoiment_returnTime"));
        colCarPrice.setCellValueFactory(new PropertyValueFactory<>("Car_price"));
        colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("Payment"));
        colAppoimentreturndate.setCellValueFactory(new PropertyValueFactory<>("Returndate"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("Customer_id"));
    }
    private void loadAllAppoiment() {
        var model = new AppoimentModel();

        ObservableList<AppoimentTm> obList = FXCollections.observableArrayList();

        try {
            List<AppoimentDto> dtoList = model.getAllAppoiment();

            for(AppoimentDto dto : dtoList) {
                obList.add(
                        new AppoimentTm(
                                dto.getId(),
                                dto.getDate(),
                                dto.getTime(),
                                dto.getReturndate(),
                                dto.getCustomer_id(),
                                dto.getAppoiment_returnTime(),
                                dto.getCar_id(),
                                dto.getCar_name(),
                                dto.getCar_price(),
                                dto.getPayment()

                        )
                );
            }

            tblAppoiment.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
    private void clearFields() {
        lblAppoimentId.setText("");
        txtAppoimentDate.setValue(null);
        txtAppoimentTime.setText("");
        txtAppoimentReturnDate.setValue(null);
        txtAppoimentReturnTime.setText("");
        cmbCustId.setValue("");
        cmbCar_id.setValue("");
        lblCarName.setText("");
        lblCarPrice.setText("");
        txtTotalPrice.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String id =lblAppoimentId.getText();

        AppoimentModel appoimentModel =new AppoimentModel();

        try {
            boolean isDeleted =appoimentModel.deleteAppoiment(id);

            if (isDeleted){
                tblAppoiment.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "Appoiment deleted!").show();
                loadAllAppoiment();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }



    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = lblAppoimentId.getText();
        String date = String.valueOf(txtAppoimentDate.getValue());
        String time= txtAppoimentReturnTime.getText();
        String Returndate= String.valueOf(txtAppoimentReturnDate.getValue());
        String Appoiment_returnTime=txtAppoimentReturnTime.getText();
        String Customer_id= cmbCustId.getValue();
        String Car_id=cmbCar_id.getValue();
        String Car_name =lblCarName.getText();
        String Car_price=lblCarPrice.getText();
        String payment=txtTotalPrice.getText();

        var dto = new AppoimentDto(id,date,time,Returndate,Appoiment_returnTime,Customer_id,Car_id,Car_name,Car_price,payment);

        var model = new AppoimentModel();
        try {
            boolean isSaved=transAction.saveAppoiment(dto,Car_id);

            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "appoiment saved!").show();
               // clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String id =lblAppoimentId.getText();
        String date =txtAppoimentTime.getText();
        String time = String.valueOf(txtAppoimentReturnDate.getValue());
        String Returndate = String.valueOf(txtAppoimentReturnDate.getValue());
        String Customer_id =cmbCustId.getValue();
        String Appoiment_returnTime =txtAppoimentReturnTime.getText();
        String Car_id =cmbCar_id.getValue();
        String Car_name =lblCarName.getText();
        String Car_price =lblCarPrice.getText();
        String Payment =txtTotalPrice.getText();


        AppoimentDto appoimentDto = new AppoimentDto(id,date,time,Returndate,Customer_id,Appoiment_returnTime,Car_id,Car_name,Car_price,Payment);

        AppoimentModel appoimentModelModel =new AppoimentModel();

        try {
            boolean isUpdated = appoimentModelModel.updateAppoiment(appoimentDto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "Appoiment updated!").show();
                loadAllAppoiment();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void tblOnMouseClickAction(MouseEvent event) {
        lblAppoimentId.setText(tblAppoiment.getSelectionModel().getSelectedItem().getId());
        txtAppoimentTime.setText(tblAppoiment.getSelectionModel().getSelectedItem().getTime());
        txtAppoimentDate.setValue(LocalDate.parse(tblAppoiment.getSelectionModel().getSelectedItem().getDate()));
        txtAppoimentReturnTime.setText(tblAppoiment.getSelectionModel().getSelectedItem().getAppoiment_returnTime());
        txtAppoimentReturnDate.setValue(LocalDate.parse(tblAppoiment.getSelectionModel().getSelectedItem().getReturndate()));
        cmbCar_id.setValue(tblAppoiment.getSelectionModel().getSelectedItem().getCar_id());
        lblCarName.setText(tblAppoiment.getSelectionModel().getSelectedItem().getCar_name());
        lblCarPrice.setText(tblAppoiment.getSelectionModel().getSelectedItem().getCar_price());
        txtAppoimentTime.setText(tblAppoiment.getSelectionModel().getSelectedItem().getTime());
        cmbCustId.setValue(tblAppoiment.getSelectionModel().getSelectedItem().getCustomer_id());
        txtTotalPrice.setText(tblAppoiment.getSelectionModel().getSelectedItem().getPayment());

    }
    @FXML
    void btnPrintOnAction(ActionEvent event) {
        print();

    }

    @FXML
    void cmbCarOnAction(ActionEvent event) throws SQLException {
      //  System.out.println("hello");

        String id =  cmbCar_id.getValue();
        CarDto carDto =CarModel.searchCar(id);
        lblCarName.setText(carDto.getName());
        lblCarPrice.setText(carDto.getPrice());

    }

    @FXML
    void cmbCustOnAction(ActionEvent event) throws SQLException {
       String id = (String) cmbCustId.getValue();
        CustomerDto customerDto =CustomerModel.searchCustomer(id);


    }
    @FXML
    void btnCalculateOnAction(ActionEvent event) {
         long noOfDays = ChronoUnit.DAYS.between(txtAppoimentDate.getValue(), txtAppoimentReturnDate.getValue());
         long totValue = Integer.parseInt(lblCarPrice.getText()) * noOfDays;
         txtTotalPrice.setText(Long.toString(totValue));
    }
    public String autoGenerateID() {
        Connection connection = null;
        String userID = null;

        try {
            connection = DbConnection.getInstance().getConnection();

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT Appoiment_id FROM appoiment order by Appoiment_id desc limit 1");

                boolean isExist = resultSet.next();

                if (isExist) {
                    String oldID = resultSet.getString(1);//1st col value

                    oldID = oldID.substring(1, oldID.length());//Character id(U001)

                    int intID = Integer.parseInt(oldID);

                    intID = intID + 1;

                    if (intID < 10) {
                        userID = "A00" + intID;
                    } else if (intID < 100) {
                        userID = "A0" + intID;
                    } else {
                        userID = "A" + intID;
                    }
                } else {
                    userID = "A001";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userID;
    }



    public void print(){

        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src/main/resources/reports/Appoiment_A4.jrxml");

            JRDesignQuery jrDesignQuery = new JRDesignQuery();
            jrDesignQuery.setText("SELECT * FROM appoiment");
            jasperDesign.setQuery(jrDesignQuery);

            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            Map<String, Object> parameters = new HashMap<>();
            // 3. Add a parameter and set its value

            String val = txtAppoimentReturnTime.getText();
            parameters.put("Appoiment_id",lblAppoimentId.getText());
            parameters.put("Appoiment_time", txtAppoimentTime.getText());
            parameters.put("Appoiment_date", txtAppoimentDate.getValue().toString());
            parameters.put("Appoiment_return_time", txtAppoimentReturnTime.getText());
            parameters.put("Total_price", txtTotalPrice.getText());
            parameters.put("Customer_id", cmbCar_id.getValue());
            parameters.put("Car_name", lblCarName.getText());
            parameters.put("Car_price", lblCarPrice.getText());

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
            sendMail();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendMail()
    {
        // Sender's Zoho Mail address and password
        final String senderEmail = "NisalJayasekara@zohomail.com";
        final String senderPassword = "nmj2001@1234";

        // Recipient's email address
        String recipientEmail = "j.nmjayasekara@gmail.com";

        // Zoho Mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.zoho.com");
        properties.put("mail.smtp.port", "465"); // Use port 465 for SSL/TLS
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");

        // Create a session with the Zoho Mail server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender's and recipient's email addresses
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set the email subject and body
            message.setSubject("NMJ Car Rental");
            message.setText("Thanks for Appoiment");

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }


}
