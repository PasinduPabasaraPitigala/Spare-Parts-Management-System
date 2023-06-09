package controller;


import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import to.Customer;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CustomerFormController {
    public AnchorPane CustomerContext;
    public TextField txtCID;
    public TextField txtAddress;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtTelNumber;
    public TableView tblCustomer;
    public TableColumn CoulmnCusID;
    public TableColumn CoulmnCusName;
    public TableColumn CoulmnCusAddress;
    public TableColumn CoulmnCusTelNum;
    public TableColumn CoulmnCusEmail;
    public Button btnAdd;


    public void btnAddOn(ActionEvent actionEvent) {
        Customer customer = new Customer(txtCID.getText(), txtName.getText(), txtAddress.getText(), txtEmail.getText(), txtTelNumber.getText());
        try {
            boolean isAdded = CustomerModel.add(customer);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Added").show();

            } else {
                new Alert(Alert.AlertType.WARNING, "try again").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
        initialize();
    }

    private void initialize() {
        CoulmnCusID.setCellValueFactory(new PropertyValueFactory("Cid"));
        CoulmnCusName.setCellValueFactory(new PropertyValueFactory("CName"));
        CoulmnCusAddress.setCellValueFactory(new PropertyValueFactory("Address"));
        CoulmnCusTelNum.setCellValueFactory(new PropertyValueFactory("Tel_Number"));
        CoulmnCusEmail.setCellValueFactory(new PropertyValueFactory("Email"));

        try {
            loadAllDetail();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }

    }

    private void loadAllDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iron_horse.customer");
        ObservableList<Customer> observableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            observableList.add(
                    new Customer(
                    resultSet.getString("Cid"),
                    resultSet.getString("CName"),
                    resultSet.getString("Address"),
                    resultSet.getString("Email"),
                    resultSet.getString("Tel_Number")
            ));
        }
        tblCustomer.setItems(observableList);
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Cid = txtCID.getText();
        String CName = txtName.getText();
        String Address = txtAddress.getText();
        String Email = txtEmail.getText();
        String Tel_Number = txtTelNumber.getText();


        Customer customer = new Customer(Cid, CName, Address, Tel_Number, Email);
        try {
            boolean isUpdate = CustomerModel.update(customer);

            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtCID.clear();txtName.clear();txtAddress.clear();txtTelNumber.clear();txtEmail.clear();
        initialize();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
            String Cid = txtCID.getText();
            String CName = txtName.getText();
            String Address = txtAddress.getText();
            String Email = txtEmail.getText();
            String Tel_Number = txtTelNumber.getText();


            Customer customer = new Customer(Cid, CName, Address, Tel_Number, Email);
            try {
                boolean isDeleted = CustomerModel.remove(txtCID.getText());

                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete!").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Something happened!").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            txtCID.clear();txtName.clear();txtAddress.clear();txtTelNumber.clear();txtEmail.clear();
            initialize();
    }

    public void txtCIDOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String id = txtCID.getText();
        Customer customer = CustomerModel.search(id);
        if (customer != null) {
            fillData(customer);
        }
    }

    private void fillData(Customer customer) {
        txtCID.setText(customer.getCid());
        txtName.setText(customer.getCName());
        txtAddress.setText(customer.getAddress());
        txtEmail.setText(customer.getEmail());
        txtTelNumber.setText(String.valueOf(customer.getTel_Number()));
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String Cid = txtCID.getText();
        try {
            Customer customer = CustomerModel.search(Cid);
            if (customer != null) {
                fillData(customer);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void txtIdOnKeyRelease(KeyEvent keyEvent) {
        validate();
        if (keyEvent.getCode()== KeyCode.ENTER) {
            Object response=validate();

            if (response instanceof TextField){
                TextField textField=(TextField) response;
                textField.requestFocus();
            }else if(response instanceof Boolean){
                System.out.println("Valid");
            }

        }
    }


    private Object validate(){
        Pattern idPattern = Pattern.compile("^(C00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile( "^[A-z0-9 ,/]{4,20}$");
        Pattern contactPattern = Pattern.compile("^[0-9]{5,10}$");
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$");

        if(!(idPattern.matcher(txtCID.getText()).matches())){
            addError(txtCID);
            return txtCID;
        }else{
            removeError(txtCID);
            if((!namePatten.matcher(txtName.getText()).matches())){
                addError(txtName);
                return txtName;
            }else{
                removeError(txtName);
                if(!(addressPattern.matcher(txtAddress.getText()).matches())){
                    addError(txtAddress);
                    return txtAddress;
                }else{
                    removeError(txtAddress);
                    if(!(emailPattern.matcher(txtEmail.getText()).matches())){
                        addError(txtEmail);
                        return txtEmail;
                    }else{
                        removeError(txtEmail);
                    if(!(contactPattern.matcher(txtTelNumber.getText()).matches())){
                        addError(txtTelNumber);
                        return txtTelNumber;
                    }else{
                        removeError(txtTelNumber);

                        }
                    }
                }
            }
        }
        return true;
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: lightblue");btnAdd.setDisable(false);
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red");
        btnAdd.setDisable(true);

    }

    public void printOnAction(ActionEvent actionEvent) {
        try{
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/Report/A4.jrxml"));
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
