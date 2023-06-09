package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.SalaryModel;
import to.Salary;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SalaryFormController {
    public AnchorPane SalaryContext;
    public TextField txtSID;
    public TextField txtAmount;
    public TextField txtName;
    public TextField txtDescription;
    public TextField txtEID;
    public TableColumn columnSID;
    public TableColumn columnEID;
    public TableColumn columnName;
    public TableColumn columnAmount;
    public TableColumn columnDescription;
    public TableView tblSalaryView;
    public Button btnAdd;

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Salary salary = new Salary(txtSID.getText(), txtEID.getText(), txtName.getText(), txtAmount.getText(), txtDescription.getText());
        try {
            boolean isAdded = SalaryModel.add(salary);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Salary Added").show();

            } else {
                new Alert(Alert.AlertType.WARNING, "try again").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
        initialize();
    }

    private void initialize() throws SQLException, ClassNotFoundException {
        columnSID.setCellValueFactory(new PropertyValueFactory("Sid"));
        columnEID.setCellValueFactory(new PropertyValueFactory("Eid"));
        columnName.setCellValueFactory(new PropertyValueFactory("SName"));
        columnAmount.setCellValueFactory(new PropertyValueFactory("Amount"));
        columnDescription.setCellValueFactory(new PropertyValueFactory("Description"));

        loadAllDetail();
    }

    private void loadAllDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iron_horse.salary");
        ObservableList<Salary> observableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            observableList.add(
                    new Salary(
                            resultSet.getString("Sid"),
                            resultSet.getString("Eid"),
                            resultSet.getString("SName"),
                            resultSet.getString("Amount"),
                            resultSet.getString("Description")
                    ));
        }
        tblSalaryView.setItems(observableList);
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Sid = txtSID.getText();
        String Eid = txtEID.getText();
        String SName = txtName.getText();
        String Amount = txtAmount.getText();
        String Description = txtDescription.getText();


        Salary salary = new Salary(Sid, Eid,SName, Amount, Description);
        boolean isUpdate = SalaryModel.update(salary);

        if (isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "Salary Updated!").show();
        } else {
            new Alert(Alert.AlertType.WARNING, "Something happened!").show();
        }
        txtEID.clear();txtSID.clear();txtName.clear();txtAmount.clear();txtDescription.clear();

        initialize();

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String Sid = txtSID.getText();
        String Eid = txtEID.getText();
        String SName = txtName.getText();
        String Amount = txtAmount.getText();
        String Description = txtDescription.getText();


        Salary salary = new Salary(Sid, Eid, SName, Amount,Description);
        try {
            boolean isDeleted = SalaryModel.remove(txtSID.getText());

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Delete!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }initialize();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtSID.clear();txtEID.clear();txtName.clear();txtAmount.clear();txtDescription.clear();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String Sid = txtSID.getText();
        try {
            Salary salary = SalaryModel.search(Sid);
            if (salary != null) {
                fillData(salary);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Salary salary) {
        txtSID.setText(salary.getSid());
        txtEID.setText(salary.getEid());
        txtName.setText(salary.getSName());
        txtAmount.setText(salary.getAmount());
        txtDescription.setText(salary.getDescription());
    }

    public void salaryKeyOnReleased(KeyEvent keyEvent) {
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
        Pattern eidPattern = Pattern.compile("^(E00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern amountPattern = Pattern.compile( "^[A-z0-9 ,/]{1,20}$");
        Pattern descriptionPattern = Pattern.compile("^[A-z0-9]{5,30}$");



        if(!(idPattern.matcher(txtSID.getText()).matches())){
            addError(txtSID);
            return txtSID;
        }else{
            removeError(txtSID);
            if((!eidPattern.matcher(txtEID.getText()).matches())){
                addError(txtEID);
                return txtEID;
            }else{
                removeError(txtEID);
                if(!(namePatten.matcher(txtName.getText()).matches())){
                    addError(txtName);
                    return txtName;
                }else{
                    removeError(txtName);
                    if(!(amountPattern.matcher(txtAmount.getText()).matches())){
                        addError(txtAmount);
                        return txtAmount;
                    }else{
                        removeError(txtAmount);
                        if(!(descriptionPattern.matcher(txtDescription.getText()).matches())){
                            addError(txtDescription);
                            return  txtDescription;
                        }else{
                            removeError( txtDescription);

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
