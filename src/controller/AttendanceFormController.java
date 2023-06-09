package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.AttendanceModel;
import to.Attendance;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AttendanceFormController {
    public TableColumn colEid;
    public TableColumn colAID;
    public TableColumn colTimeIn;
    public TableColumn colTimeOut;
    public TableColumn colDate;
    public TextField txtEID;
    public TextField txtTimeOut;
    public TextField txtTimeIn;
    public TextField txtDate;
    public AnchorPane AttendanceContext;
    public TextField txtAID;
    public TableView tblAttendanceView;
    public Button btnAdd;

    public void txtEIDOnAction(ActionEvent actionEvent) {
    }

    public void btnAddOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        Attendance attendance = new Attendance(txtEID.getText(), txtAID.getText(), txtTimeIn.getText(), txtTimeOut.getText(), txtDate.getText());
        try {
            boolean isAdded = AttendanceModel.add(attendance);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance Added").show();

            } else {
                new Alert(Alert.AlertType.WARNING, "try again").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }initialize();
    }

    public void btnModifyOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Eid = txtEID.getText();
        String Aid = txtAID.getText();
        String TimeIn = txtTimeIn.getText();
        String TimeOut = txtTimeOut.getText();
        String Date = txtDate.getText();


        Attendance attendance = new Attendance(Eid, Aid, TimeIn, TimeOut, Date);
        try {
            boolean isUpdate = AttendanceModel.update(attendance);

            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance Updated!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtEID.clear();txtAID.clear();txtTimeIn.clear();txtTimeOut.clear();txtDate.clear();
        initialize();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Eid = txtEID.getText();
        String Aid = txtAID.getText();
        String TimeIn = txtTimeIn.getText();
        String TimeOut = txtTimeOut.getText();
        String Date = txtDate.getText();


        Attendance attendance = new Attendance(Eid, Aid, TimeIn, TimeOut, Date);
        try {
            boolean isDeleted = AttendanceModel.remove(txtAID.getText());

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Attendance Delete!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtEID.clear();txtAID.clear();txtTimeIn.clear();txtTimeOut.clear();txtDate.clear();
        initialize();
    }

    private void initialize() throws SQLException, ClassNotFoundException {
        colEid.setCellValueFactory(new PropertyValueFactory("Eid"));
        colAID.setCellValueFactory(new PropertyValueFactory("Aid"));
        colTimeIn.setCellValueFactory(new PropertyValueFactory("TimeIn"));
        colTimeOut.setCellValueFactory(new PropertyValueFactory("TimeOut"));
        colDate.setCellValueFactory(new PropertyValueFactory("Date"));

        loadAllDetail();

    }

    private void loadAllDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iron_horse.attendance");
        ObservableList<Attendance> observableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            observableList.add(
                    new Attendance(
                            resultSet.getString("Eid"),
                            resultSet.getString("Aid"),
                            resultSet.getString("Time_in"),
                            resultSet.getString("Time_out"),
                            resultSet.getString("ADate")
                    ));
        }

        System.out.println(observableList.get(0));

        tblAttendanceView.setItems(observableList);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        String Aid = txtAID.getText();
        try {
            Attendance attendance = AttendanceModel.search(Aid);
            if (attendance != null) {
                fillData(attendance);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Attendance attendance) {
        txtEID.setText(attendance.getEid());
        txtAID.setText(attendance.getAid());
        txtTimeIn.setText(attendance.getTimeIn());
        txtTimeOut.setText(attendance.getTimeOut());
        txtDate.setText(attendance.getDate());
    }

    public void attendanceKeyOnRelease(KeyEvent keyEvent) {
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

    private Object validate() {
        Pattern eIdPattern = Pattern.compile("^(E00-)[0-9]{3,5}$");
        Pattern TimeInPatten = Pattern.compile("^[A-z0-9 ,/]{3,15}$");
        Pattern aIdPattern = Pattern.compile("^(A00-)[0-9]{3,5}$");
        Pattern DatePattern = Pattern.compile("^[A-z0-9 ,/]{3,15}$");
        Pattern TimeOutPattern = Pattern.compile("^[A-z0-9 ,/]{3,15}$");

        if(!(eIdPattern.matcher(txtEID.getText()).matches())){
            addError(txtEID);
            return txtEID;
        }else{
            removeError(txtEID);
            if((!TimeInPatten.matcher(txtTimeIn.getText()).matches())){
                addError(txtTimeIn);
                return txtTimeIn;
            }else{
                removeError(txtTimeIn);
                if(!(aIdPattern.matcher(txtAID.getText()).matches())){
                    addError(txtAID);
                    return txtAID;
                }else {
                    removeError(txtAID);
                    if(!(DatePattern.matcher(txtDate.getText()).matches())){
                        addError(txtDate);
                        return txtDate;
                    }else{
                        removeError(txtDate);
                        if(!(TimeOutPattern.matcher(txtTimeOut.getText()).matches())){
                            addError(txtTimeOut);
                            return txtTimeOut;
                        }else{
                            removeError(txtTimeOut);
                        }
                    }
                }
            }
        }
        return true;
    }

    private void removeError(TextField textField) {
        textField.setStyle("-fx-border-color: lightblue");
        btnAdd.setDisable(false);
    }

    private void addError(TextField textField) {
        textField.setStyle("-fx-border-color: red");
        btnAdd.setDisable(true);
    }
}
