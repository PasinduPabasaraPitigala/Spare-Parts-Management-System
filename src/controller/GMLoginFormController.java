package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import util.Navigation;
import util.Routes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GMLoginFormController implements Initializable {
    public JFXButton btnlog;
    public JFXTextField userNametxt;
    public JFXTextField passwordtxt;
    public AnchorPane Anchorpain2;
    public AnchorPane pane01;
    public PasswordField txtpassword;
    public Button btnBack;

    @Override

    public void initialize(URL location, ResourceBundle resource){
    }

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        System.out.println(userNametxt.getText());
        String tempUsername = userNametxt.getText();
        String tempPassword = txtpassword.getText();
        System.out.println(txtpassword.getText());
        if (tempUsername.equals("pasindu") && tempPassword.equals("2001")) {
            Navigation.navigate(Routes.GMDASHBOARD,pane01);
            System.out.println(txtpassword.getText());
        }
    }

    public void FogotOnAction(ActionEvent actionEvent) throws IOException {

    }

    public void passwordtxtOnAction(ActionEvent actionEvent) {
    }

    public void userNametxtOnAction(ActionEvent actionEvent) {
    }

    public void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MAIN_LOGIN,pane01);
    }

    public void bTnLOG(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.STOCK_MANAGER_DASHBOARD,pane01);
    }

    public void StockManagerbtnBackOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MAIN_LOGIN,pane01);
    }
}