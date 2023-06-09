package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import util.Navigation;
import util.Routes;

import java.io.IOException;

public class MainLoginFormController {
    public Button btnGMLogin;
    public Button btnCLogin;
    public Button btnSMLogin;
    public AnchorPane anchorPane1;

    public void btnGMLoginOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.GMLOGIN,anchorPane1);
    }

    public void btnCLoginOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CASHIER_LOGIN,anchorPane1);
    }

    public void btnSMLoginOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.STOCK_MANAGER_LOGIN,anchorPane1);
    }
}
