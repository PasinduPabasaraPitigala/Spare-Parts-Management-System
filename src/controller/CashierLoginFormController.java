package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import util.Navigation;
import util.Routes;

import java.io.IOException;

public class CashierLoginFormController {
    public JFXButton btnlog;
    public JFXTextField userNametxt;
    public AnchorPane Anchorpain2;
    public AnchorPane anchorPane1;
    public Button btnBack;
    public PasswordField paswordtxt;

    public void userNametxtOnAction(ActionEvent actionEvent) {
    }


    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER_DASHBOARD,anchorPane1);
        System.out.println(userNametxt.getText());
        String tempUsername = userNametxt.getText();
        String tempPassword = paswordtxt.getText();
        System.out.println(paswordtxt.getText());
        if (tempUsername.equals("pasindu") && tempPassword.equals("2001")) {
            Navigation.navigate(Routes.CASHIER_DASHBOARD,anchorPane1);
            System.out.println(paswordtxt.getText());
        }
    }

    public void FogotOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.FOGOT_MY_PASSWORD,anchorPane1);
    }

    public void CashierbckOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MAIN_LOGIN,anchorPane1);
    }

    public void paswordtxtOnAction(ActionEvent actionEvent) {
    }
}
