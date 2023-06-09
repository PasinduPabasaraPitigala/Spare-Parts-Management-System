package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.CustomerModel;
import model.SalaryModel;
import model.SupplierModel;
import to.Customer;
import to.Supplier;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SupplierFormController {
    public AnchorPane SupplierContext;
    public TableView tblSupView;
    public TableColumn colSupID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colTelNumber;
    public TableColumn colDescription;
    public TextField txtDescription;
    public TextField txtEmail;
    public TextField txtName;
    public TextField txtTelNumber;
    public TextField txtAddress;
    public TextField txtSupID;
    public Button btnAdd;

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Supplier supplier = new Supplier(txtSupID.getText(), txtName.getText(), txtAddress.getText(), txtEmail.getText(), Integer.parseInt(txtTelNumber.getText()), txtDescription.getText());
        try {
            boolean isAdded = SupplierModel.add(supplier);
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

    private void initialize() throws SQLException, ClassNotFoundException {
        colSupID.setCellValueFactory(new PropertyValueFactory("SupId"));
        colName.setCellValueFactory(new PropertyValueFactory("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory("Address"));
        colEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        colTelNumber.setCellValueFactory(new PropertyValueFactory("Tel_Number"));
        colDescription.setCellValueFactory(new PropertyValueFactory("Description"));

        loadAllDetail();
    }

    private void loadAllDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iron_horse.supplier");
        ObservableList<Supplier> observableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            observableList.add(
                    new Supplier(
                            resultSet.getString("Supid"),
                            resultSet.getString("Name"),
                            resultSet.getString("Address"),
                            resultSet.getString("Email"),
                            resultSet.getInt("Tel_Number"),
                            resultSet.getString("Description")
                    ));
        }
        tblSupView.setItems(observableList);
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Supid = txtSupID.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Email = txtEmail.getText();
        int Tel_Number = Integer.parseInt(txtTelNumber.getText());
        String Description = txtDescription.getText();


        Supplier supplier = new Supplier(Supid, Name, Address, Email, Tel_Number, Description);
        boolean isUpdate = SupplierModel.update(supplier);

        if (isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
        txtSupID.clear();txtName.clear();txtAddress.clear();txtEmail.clear();txtTelNumber.clear();txtDescription.clear();
        initialize();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Supid = txtSupID.getText();
        String Name = txtName.getText();
        String Address = txtAddress.getText();
        String Email = txtEmail.getText();
        int Tel_Number = (int) Double.parseDouble(txtTelNumber.getText());
        String Description = txtDescription.getText();


        Supplier supplier = new Supplier(Supid, Name, Address, Email, Tel_Number,Description);
        boolean isDeleted = SupplierModel.remove(txtSupID.getText());

        if (isDeleted) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
        txtSupID.clear();txtName.clear();txtAddress.clear();txtEmail.clear();txtTelNumber.clear();txtDescription.clear();
        initialize();
    }

    public void txtSupIDOnAction(ActionEvent actionEvent) {
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String Supid = txtSupID.getText();
        try {
            Supplier supplier = SupplierModel.search(Supid);
            if (supplier != null) {
                fillData(supplier);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Supplier supplier) {
        txtSupID.setText(supplier.getSupId());
        txtName.setText(supplier.getName());
        txtAddress.setText(supplier.getAddress());
        txtEmail.setText(supplier.getEmail());
        txtTelNumber.setText(String.valueOf(supplier.getTel_Number()));
        txtDescription.setText(supplier.getDescription());
    }

    public void txtSupplierIdOnAction(KeyEvent keyEvent) {
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
        Pattern idPattern = Pattern.compile("^(S00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile( "^[A-z0-9 ,/]{4,20}$");
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)$");
        Pattern contactPattern = Pattern.compile("^[0-9]{5,10}$");
        Pattern descriptionPattern = Pattern.compile("^[A-z ]{3,15}$");

        if(!(idPattern.matcher(txtSupID.getText()).matches())){
            addError(txtSupID);
            return txtSupID;
        }else{
            removeError( txtSupID);
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
                            if(!(descriptionPattern.matcher(txtDescription.getText()).matches())){
                                addError(txtDescription);
                                return txtDescription;
                            }else{
                                removeError(txtDescription);

                            }
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


}
