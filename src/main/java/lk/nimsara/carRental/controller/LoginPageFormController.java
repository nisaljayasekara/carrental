package lk.nimsara.carRental.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lk.nimsara.carRental.model.UserModel;
import lk.nimsara.carRental.util.Navigation;
import lk.nimsara.carRental.util.Utils;

import java.io.IOException;

public class LoginPageFormController {

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

   @FXML
    void btnLoginOnAction(ActionEvent event) {
       String userId = UserModel.verifyUser(txtUserName.getText(),txtPassword.getText());
        if(userId != null){
            Utils.userId = userId;
            try {
                Navigation.switchNavigation("dashBoardForm.fxml",event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void hLinkSignUp(ActionEvent event) throws IOException {
        Navigation.switchNavigation("createNewAccount_from.fxml", event);

    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml", event);
    }
}



