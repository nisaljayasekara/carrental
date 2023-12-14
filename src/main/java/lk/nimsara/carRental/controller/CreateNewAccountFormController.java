package lk.nimsara.carRental.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.nimsara.carRental.db.DbConnection;
import lk.nimsara.carRental.util.Navigation;

import java.io.IOException;
import java.sql.*;

public class CreateNewAccountFormController {

    @FXML
    public Label lblPasswordNotMatched1;

    @FXML
    public Label lblPasswordNotMatched2;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmalil;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private TextField txtUserName;



    @FXML
    private Label lblUserId;


    @FXML
    private AnchorPane rootNode;


    public CreateNewAccountFormController() {
    }

    public void initialize() {
        setLblVisibility(false);
        setDisableCommon(true);
        lblUserId.setText(autoGenerateID());
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) {

        register(event);
    }

    @FXML
    void txtConfirmPasswordOnAction(ActionEvent event)  {
        register(event);
    }

    private void register(ActionEvent event)  {
        String newPassword = txtNewPassword.getText();

        String confirmPassword =txtConfirmPassword.getText();

        if (newPassword.equals(confirmPassword)){
            setBorderColor("transparent");
            setLblVisibility(false);

            String User_id =lblUserId.getText();
            String User_name =txtUserName.getText();
            String User_email =txtEmalil.getText();


            Connection connection = null;
            try {
                connection = DbConnection.getInstance().getConnection();

                PreparedStatement  preparedStatement =connection.prepareStatement("INSERT INTO user values(?,?,?,?,?,?)");
                preparedStatement.setObject(1,lblUserId.getText());
                preparedStatement.setObject(2,txtUserName.getText());
                preparedStatement.setObject(3,txtConfirmPassword.getText());
                preparedStatement.setObject(4,txtEmalil.getText());
                preparedStatement.setObject(5,"E001");
                preparedStatement.setObject(6,"user");

                int i=preparedStatement.executeUpdate();
                if (i!=0){
                    Alert alert =new Alert(Alert.AlertType.CONFIRMATION,"Success..");
                    alert.show();
                    Navigation.switchNavigation("loginPageForm.fxml", event);
                }else {
                    Alert alert =new Alert(Alert.AlertType.ERROR,"NoSuccess..");
                    alert.show();

                }
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

        }else {
            setLblVisibility(true);
            setBorderColor("red");
            txtNewPassword.requestFocus();
        }
    }

    private void setBorderColor(String color) {
        txtNewPassword.setStyle("-fx-border-color: color");
        txtConfirmPassword.setStyle("-fx-border-color: color");
    }

    private void setLblVisibility(boolean isVisible) {
        lblPasswordNotMatched1.setVisible(isVisible);
        lblPasswordNotMatched2.setVisible(isVisible);
    }

    @FXML
    void btnAddNewUserOnAction(ActionEvent event)  {
        setDisableCommon(false);
        txtUserName.requestFocus();
    }

    public void setDisableCommon(boolean isDisable){
        txtUserName.setDisable(isDisable);
        txtEmalil.setDisable(isDisable);
        txtNewPassword.setDisable(isDisable);
        txtConfirmPassword.setDisable(isDisable);

    }
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml", event);
    }
    public String autoGenerateID() {
        Connection connection = null;
        String userID = null;

        try {
            connection = DbConnection.getInstance().getConnection();

            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT User_id FROM user order by User_id desc limit 1");

                boolean isExist = resultSet.next();

                if (isExist) {
                    String oldID = resultSet.getString(1);//1st col value

                    oldID = oldID.substring(1, oldID.length());//Character id(U001)

                    int intID = Integer.parseInt(oldID);

                    intID = intID + 1;

                    if (intID < 10) {
                        userID = "U00" + intID;
                    } else if (intID < 100) {
                        userID = "U0" + intID;
                    } else {
                        userID = "U" + intID;
                    }
                } else {
                    userID = "U001";
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userID;
    }
}



