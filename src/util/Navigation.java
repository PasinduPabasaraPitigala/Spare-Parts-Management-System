package util;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    private static AnchorPane pane;
    public static void navigate(Routes route, AnchorPane pane) throws IOException {
        Navigation.pane = pane;
        Navigation.pane.getChildren().clear();
        Stage window = (Stage) Navigation.pane.getScene().getWindow();

        switch (route) {
            case CUSTOMER:
                window.setTitle("Customer Manage Form");
                initUI("CustomerForm.fxml");
                break;

            case ORDER:
                window.setTitle(" Order Form");
                initUI("PlaceOrderForm.fxml");
                break;

            case GMDASHBOARD:
                window.setTitle("DashBoard Form");
                initUI("GMDashBoardForm.fxml");
                break;

            case ATTENDANCE:
                window.setTitle("Attendance Form");
                initUI("AttendanceForm.fxml");
                break;

            case CATEGORY:
                window.setTitle("Category Form");
                initUI("CategoryForm.fxml");
                break;

            case CREATE_NEW_ACCOUNT:
                window.setTitle("Create New Account Form");
                initUI("CreateNewAccount.fxml");
                break;

            case EMPLOYER:
                window.setTitle("Employer Form");
                initUI("EmployerForm.fxml");
                break;

            case FOGOT_MY_PASSWORD:
                window.setTitle("Fogot My Password Form");
                initUI("FogotMyPassword.fxml");
                break;

            case ITEM:
                window.setTitle("Item Form");
                initUI("ItemForm.fxml");
                break;

            case GMLOGIN:
                window.setTitle("Login Form");
                initUI("GMLoginForm.fxml");
                break;

            case ORDER_DETAIL:
                window.setTitle("Order Detail Form");
                initUI("OrderDetailForm.fxml");
                break;

            case ORDER_PAYMEMT:
                window.setTitle("Order Payment Form");
                initUI("PaymentForm.fxml");
                break;

            case SALARY:
                window.setTitle("Salary form");
                initUI("SalaryForm.fxml");
                break;

            case SUPPLIER:
                window.setTitle("Supplier Form");
                initUI("SupplierForm.fxml");
                break;

            case SUPPLIER_ORDER:
                window.setTitle("Supplier Order Form");
                initUI("SupplierOrderForm.fxml");
                break;
            case CASHIER_DASHBOARD:
                window.setTitle("Cashier Dashboard Form");
                initUI("CashierDashBoard.fxml");
                break;

            case CASHIER_LOGIN:
                window.setTitle("Cashier Login Form");
                initUI("CashierLoginForm.fxml");
                break;

            case STOCK_MANAGER_DASHBOARD:
                window.setTitle("Stock Manager Dashboard Form");
                initUI("StockManagerDashBoard.fxml");
                break;

            case STOCK_MANAGER_LOGIN:
                window.setTitle("Stock Manager Login Form");
                initUI("StockManagerLoginForm.fxml");
                break;

            case MAIN_LOGIN:
                window.setTitle("Main Login Form");
                initUI("MainLoginForm.fxml");
                break;


            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }
    private static void initUI(String location) throws IOException {
        Navigation.pane.getChildren().add(FXMLLoader.load(Navigation.class
                .getResource("/view/" + location)));
    }
}
