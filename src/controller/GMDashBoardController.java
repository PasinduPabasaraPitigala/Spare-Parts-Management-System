package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import util.Navigation;
import util.Routes;

import java.io.IOException;

public class GMDashBoardController {
    public Button btnItem;
    public Button btnEmployers;
    public Button btnAttendance;
    public Button btnSalary;
    public Button btnCategory;
    public Button btnSupplier;
    public Button btnOrders;
    public Button btnCustomer;
    public Button btnOrderDetails;
    public AnchorPane upAnchorpain;
    public Button btnlogOut;
    public AnchorPane pain;

    public void AttendanceOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ATTENDANCE,upAnchorpain);
    }

    public void SalaryOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SALARY,upAnchorpain);
    }

    public void ItemOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ITEM,upAnchorpain);
    }


    public void EmployeersOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.EMPLOYER,upAnchorpain);
    }

    public void CategoryOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CATEGORY,upAnchorpain);
    }

    public void SupplierOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.SUPPLIER,upAnchorpain);
    }

    public void OrdersOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ORDER,upAnchorpain);
    }

    public void CustomerOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.CUSTOMER,upAnchorpain);
    }

    public void OrderDetailsOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.ORDER_DETAIL,upAnchorpain);
    }

    public void GMlogOutOnAction(ActionEvent actionEvent) throws IOException {
        Navigation.navigate(Routes.MAIN_LOGIN,pain);
    }
}
