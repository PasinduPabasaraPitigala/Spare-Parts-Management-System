package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.EmployerModel;
import to.Employer;
import util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class EmployerFormController {
    public AnchorPane EmployerContext;
    public Button btnAdd;
    public Button btnSearch;
    public Button btnDelete;
    public TextField txtEID;
    public TextField txtAddress;
    public TextField txtTelNumber;
    public TextField txtName;
    public TextField txtEmail;
    public TextField txtJobRole;
    public Button btnModify;
    public TableColumn tblEID;
    public TableColumn tblName;
    public TableColumn tblAddress;
    public TableColumn tblEmail;
    public TableColumn tblTelNumber;
    public TableColumn tblJobRole;
    public TableView tblEmployer;

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Employer employer = new Employer(txtEID.getText(), txtName.getText(), txtAddress.getText(), txtEmail.getText(), txtTelNumber.getText(),txtJobRole.getText());
        boolean isAdded=EmployerModel.add(employer);
        if (isAdded) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer Added").show();

        } else {
            new Alert(Alert.AlertType.WARNING, "try again").show();
        }initialize();
    }

    public void btnSearchOnAction(ActionEvent actionEvent){
        String Eid = txtEID.getText();
        try {
            Employer employer = EmployerModel.search(Eid);
            if (employer != null) {
                fillData(employer);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Employer employer) {
        txtEID.setText(employer.getEid());
        txtName.setText(employer.getEName());
        txtAddress.setText(employer.getEAddress());
        txtEmail.setText(employer.getEmail());
        txtTelNumber.setText(employer.getTel_Number());
        txtJobRole.setText(employer.getJobRole());

    }


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String Eid = txtEID.getText();
        String EName = txtName.getText();
        String EAddress = txtAddress.getText();
        String Email = txtEmail.getText();
        String Tel_Number = txtTelNumber.getText();
        String jobRole = txtJobRole.getText();


        Employer employer = new Employer(Eid, EName, EAddress, Tel_Number, Email ,jobRole);
        try {
            boolean isDelete = EmployerModel.delete(employer.getEid());

            if (isDelete) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employer Deleted!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtEID.clear();txtName.clear();txtAddress.clear();txtTelNumber.clear();txtEmail.clear();txtJobRole.clear();
        initialize();
    }

    public void txtEIDOnAction(ActionEvent actionEvent) {
    }

    public void btnModifyOnAction(ActionEvent actionEvent){
        String Eid = txtEID.getText();
        String EName = txtName.getText();
        String EAddress = txtAddress.getText();
        String Email = txtEmail.getText();
        String Tel_Number = txtTelNumber.getText();
        String jobRole = txtJobRole.getText();


        Employer employer = new Employer(Eid, EName, EAddress, Tel_Number, Email ,jobRole);
        try {
            boolean isUpdate = EmployerModel.update(employer);

            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Employer Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtEID.clear();txtName.clear();txtAddress.clear();txtTelNumber.clear();txtEmail.clear();txtJobRole.clear();
        initialize();
    }

    private void initialize() {
        tblEID.setCellValueFactory(new PropertyValueFactory("Eid"));
        tblName.setCellValueFactory(new PropertyValueFactory("EName"));
        tblAddress.setCellValueFactory(new PropertyValueFactory("EAddress"));
        tblEmail.setCellValueFactory(new PropertyValueFactory("Email"));
        tblTelNumber.setCellValueFactory(new PropertyValueFactory("Tel_Number"));
        tblJobRole.setCellValueFactory(new PropertyValueFactory("jobRole"));

        try {
            loadAllDetail();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }

    }

    private void loadAllDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iron_horse.employer");
        ObservableList<Employer> observableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            observableList.add(
                    new Employer(
                            resultSet.getString("Eid"),
                            resultSet.getString("EName"),
                            resultSet.getString("EAddress"),
                            resultSet.getString("Email"),
                            resultSet.getString("Tel_Number"),
                            resultSet.getString("jobRole")
                    ));
        }
        tblEmployer.setItems(observableList);
    }

    public void EmployerKeyOnRelease(KeyEvent keyEvent) {
        validate();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            Object response = validate();

            if (response instanceof TextField) {
                TextField textField = (TextField) response;
                textField.requestFocus();
            } else if (response instanceof Boolean) {
                System.out.println("Valid");
            }
        }
    }

    private Object validate() {
        Pattern edPattern = Pattern.compile("^(E00-)[0-9]{3,5}$");
        Pattern namePatten = Pattern.compile("^[A-z ]{3,15}$");
        Pattern addressPattern = Pattern.compile("^[A-z0-9 ,/]{4,20}$");
        Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9,!#$%&'+/=?'{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-z0-9-]+)$");
        Pattern contactPattern = Pattern.compile("^[0-9]{10,10}$");
        Pattern jobrolePattern= Pattern.compile("^[A-z-0-9 ]{1,20}$");


        if(!(edPattern.matcher(txtEID.getText()).matches())){
            addError(txtEID);
            return txtEID;
        }else{
            removeError(txtEID);
            if((!namePatten.matcher(txtName.getText()).matches())){
                addError(txtName);
                return txtName;
            }else{
                removeError(txtName);
                if(!(addressPattern.matcher(txtAddress.getText()).matches())){
                    addError(txtAddress);
                    return txtAddress;
                }else {
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
                            if(!(jobrolePattern.matcher(txtJobRole.getText()).matches())){
                                addError(txtJobRole);
                                return txtJobRole;
                            }else {
                                removeError(txtJobRole);
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
