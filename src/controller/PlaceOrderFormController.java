package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CustomerModel;
import model.ItemModel;
import model.OrderModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import tm.PlaceOrderTm;
import to.Customer;
import to.Item;
import to.OrderDetail;
import to.Orders;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public TableView tblOrderCart;
    public TableColumn colItemId;
    public TableColumn colPrice;
    public TableColumn colReqQty;
    public TableColumn colDescription;
    public TableColumn colTotal;
    public TableColumn colAction;
    public ComboBox cmbCustomerId;
    public ComboBox cmbItemId;
    public TextField txtCustomerName;
    public TextField txtCustomerAddress;
    public TextField txtContact;
    public TextField txtQtyOnHand;
    public TextField txtPrice;
    public TextField txtDescription;
    public TextField txtReqQty;
    public Label lblOrderId;
    public Label lblTotal;
    public TextField txtCustomerPay;
    public Label lblBalance;
    public Button btnBill;
    private ObservableList<PlaceOrderTm> obList = FXCollections.observableArrayList();

    public void initialize(URL location, ResourceBundle resources) {
        loadCustomerId();
        loadItemId();
        loadNextOrderId();
        setCellValueFactory();

    }

    private void loadNextOrderId() {
        try {
            String OrderID = OrderModel.generateNextOrderId();
            lblOrderId.setText(OrderID);
            System.out.println(OrderID);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerId() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> idList = CustomerModel.loadCustomerIds();

            for (String id : idList) {
                observableList.add(id);
            }
            cmbCustomerId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemId() {
        try {
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ArrayList<String> itemList = ItemModel.loadItemId();

            for (String code : itemList) {
                observableList.add(code);
            }
            cmbItemId.setItems(observableList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void cmbCustomerOnAction(ActionEvent actionEvent) {
        String code = String.valueOf(cmbCustomerId.getValue());
        try {
            Customer customer = CustomerModel.search(code);
            fillItemFields(customer);
            txtReqQty.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(Customer customer) {
        txtCustomerName.setText(customer.getCName());
        txtCustomerAddress.setText(customer.getAddress());
        txtContact.setText(customer.getTel_Number());
    }

    public void cmbItemOnAction(ActionEvent event) {
        String code = String.valueOf(cmbItemId.getValue());
        try {
            Item item = ItemModel.search(code);
            fillItemFields(item);
            txtReqQty.requestFocus();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillItemFields(Item item) {
        txtQtyOnHand.setText(item.getIDescription());
        txtDescription.setText(String.valueOf(item.getQTY()));
        txtPrice.setText(item.getPrice());
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory("code"));
        colPrice.setCellValueFactory(new PropertyValueFactory("unitPrice"));
        colReqQty.setCellValueFactory(new PropertyValueFactory("qty"));
        colDescription.setCellValueFactory(new PropertyValueFactory("description"));
        colTotal.setCellValueFactory(new PropertyValueFactory("total"));
        colAction.setCellValueFactory(new PropertyValueFactory("btnDelete"));
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
        String code = String.valueOf(cmbItemId.getValue());
        double unitPrice = Double.parseDouble(txtPrice.getText());
        int qty = Integer.parseInt(txtReqQty.getText());
        String desc = txtDescription.getText();
        double total = unitPrice * qty;
        Button btnDelete = new Button("Delete");

        txtReqQty.setText("");

        if (!obList.isEmpty()) {
            L1:
            /* check same item has been in table. If so, update that row instead of adding new row to the table */
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemId.getCellData(i).equals(code)) {
                    qty += (int) colReqQty.getCellData(i);
                    total = unitPrice * qty;
                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);
                    tblOrderCart.refresh();
                    calculateTotal();
                    return;
                }
            }
        }

        btnDelete.setOnAction((e) -> {
            ButtonType ok = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ok, no);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == ok) {
                PlaceOrderTm tm = (PlaceOrderTm) tblOrderCart.getSelectionModel().getSelectedItem();
                tblOrderCart.getItems().removeAll(tblOrderCart.getSelectionModel().getSelectedItem());
                calculateTotal();
            }
        });

        obList.add(new PlaceOrderTm(code, unitPrice, qty, desc, total, btnDelete));
        tblOrderCart.setItems(obList);
        calculateTotal();
    }

    private void totalBalance() {
        int CustomerPay=Integer.valueOf(txtCustomerPay.getText());
        int balance=CustomerPay-calculateTotal();
        lblBalance.setText(String.valueOf(balance));

    }

    private int calculateTotal(){
        double total=0;
        for (PlaceOrderTm tm :obList
        ) {
            total+=tm.getTotal();
        }
        lblTotal.setText(String.valueOf(total));
        return (int) total;
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) throws SQLException {
        Orders orders=new Orders(
                lblOrderId.getText(),
                (String.valueOf(LocalDate.now())),
                (String.valueOf(cmbCustomerId.getValue()))
        );

        ArrayList<OrderDetail> details=new ArrayList<>();
        for (PlaceOrderTm tm:obList
        ) {
            details.add(new OrderDetail(
                    lblOrderId.getText(),
                    tm.getCode(),
                    (String.valueOf(tm.getQty())),
                    (String.valueOf(tm.getUnitPrice()))
            ));
        }

        Connection connection=null;

        try{
            connection=  DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            boolean isOrderSaved=new OrderModel().saveOrder(orders);
            if(isOrderSaved){
                boolean isDetailsSaved=new OrderModel().saveOrderDetails(details);
                if(isDetailsSaved){
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION,"Saved").show();
                }else{
                    connection.rollback();
                    new Alert(Alert.AlertType.ERROR,"Error").show();
                }
            }else{
                connection.rollback();
                new Alert(Alert.AlertType.ERROR,"Error").show();
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
        }finally {
            connection.setAutoCommit(true);
        }
        totalBalance();
        loadNextOrderId();

    }

    public void ReportOnAction(ActionEvent actionEvent) {
        try{
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Report/bill.jrxml"));
            Connection connection= DBConnection.getInstance().getConnection();
            JasperReport compileReport = JasperCompileManager.compileReport(load);
            JasperPrint jasperPrint= JasperFillManager.fillReport(compileReport,null,connection);
            JasperViewer.viewReport(jasperPrint,false);
        }catch (JRException e){

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}