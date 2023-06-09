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
import model.ItemModel;
import to.Customer;
import to.Item;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ItemFormController {
    public AnchorPane ItemContext;
    public TextField txtDescription;
    public TextField txtName;
    public Button btnDelete;
    public Button btnAdd;
    public Button btnModify;
    public TextField txtQty;
    public Button btnSearch;
    public Button btnReload;
    public TableView tblItemForm;
    public TableColumn tblItemID;
    public TableColumn tbleCategoryName;
    public TableColumn tblItemName;
    public TableColumn tblDescription;
    public TableColumn tblQty;
    public TableColumn tblPrice;
    public TextField txtItemID;
    public TextField txtPrice;
    public TextField txtCategory;


    public void btnDeleteOnAction(ActionEvent actionEvent) {
        String Iid = txtItemID.getText();
        String ICategory = txtCategory.getText();
        String IName = txtName.getText();
        String IDescription = txtDescription.getText();
        String QTY = txtQty.getText();
        String Price = txtPrice.getText();


        Item item = new Item(Iid, ICategory, IName, IDescription, QTY, Price);
        try {
            boolean isDeleted = ItemModel.remove(txtItemID.getText());

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Delete!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        txtItemID.clear();
        txtCategory.clear();
        txtName.clear();
        txtDescription.clear();
        txtQty.clear();
        txtPrice.clear();

        initialize();
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        Item item = new Item(txtItemID.getText(), txtCategory.getText(), txtName.getText(), txtDescription.getText(), txtQty.getText(), Double.parseDouble(txtPrice.getText()));
        try {
            boolean isAdded = ItemModel.add(item);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Added").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "try again").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
        initialize();
    }

    public void btnModifyOnAction(ActionEvent actionEvent) {
        Item item = new Item(txtItemID.getText(), txtCategory.getText(), txtName.getText(), txtDescription.getText(), txtQty.getText(), Double.parseDouble(txtPrice.getText()));
        try {
            boolean isUpdate = ItemModel.update(item);
            if (isUpdate) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Update").show();

            } else {
                new Alert(Alert.AlertType.WARNING, "try again").show();
            }
        } catch (ClassNotFoundException | SQLException exception) {
            throw new RuntimeException(exception);
        }
        initialize();
    }

    public void btnSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        String Iid = txtItemID.getText();
        try {
            Item item = ItemModel.search(Iid);
            if (item != null) {
                fillData(item);
            } else {
                new Alert(Alert.AlertType.WARNING, "Something happened!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillData(Item item) {
        txtItemID.setText(item.getIid());
        txtCategory.setText(item.getICategory());
        txtName.setText(item.getIName());
        txtDescription.setText(item.getIDescription());
        txtQty.setText(item.getQTY());
        txtPrice.setText(String.valueOf(item.getPrice()));
    }

    private void initialize() {
        tblItemID.setCellValueFactory(new PropertyValueFactory("Iid"));
        tbleCategoryName.setCellValueFactory(new PropertyValueFactory("ICategory"));
        tblItemName.setCellValueFactory(new PropertyValueFactory("IName"));
        tblDescription.setCellValueFactory(new PropertyValueFactory("IDescription"));
        tblQty.setCellValueFactory(new PropertyValueFactory("QTY"));
        tblPrice.setCellValueFactory(new PropertyValueFactory("Price"));

        try {
            loadAllDetail();
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void loadAllDetail() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iron_horse.item");
        ObservableList<Item> observableList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            observableList.add(
                    new Item(
                            resultSet.getString("Iid"),
                            resultSet.getString("ICategory"),
                            resultSet.getString("IName"),
                            resultSet.getString("IDescription"),
                            resultSet.getString("QTY"),
                            resultSet.getDouble("Price")
                    ));
        }
        tblItemForm.setItems(observableList);
    }

    public void txtSearchOnAction(ActionEvent actionEvent) {
    }

    public void txtItemIDKeyReleasedOnAction(KeyEvent keyEvent) {
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

        Pattern idPattern = Pattern.compile("^(I00-)[0-9]{3,5}$");
        Pattern categoryNamePatten = Pattern.compile("^[A-z0-9 ,/ ]{1,15}$");
        Pattern namePattern = Pattern.compile("^[A-z0-9 ,/]{2,20}$");
        Pattern descriptionPattern = Pattern.compile("^[A-z0-9 ,/]{2,20}$");
        Pattern qtyPattern = Pattern.compile("^[0-9]{1,10}$");
        Pattern pricePattern = Pattern.compile("^[A-z0-9 ,/]{2,20}$");

        if (!(idPattern.matcher(txtItemID.getText()).matches())) {
            addError(txtItemID);
            return txtItemID;
        } else {
            removeError(txtItemID);
            if ((!categoryNamePatten.matcher(txtCategory.getText()).matches())) {
                addError(txtCategory);
                return txtCategory;
            } else {
                removeError(txtCategory);
                if (!(namePattern.matcher(txtName.getText()).matches())) {
                    addError(txtName);
                    return txtName;
                } else {
                    removeError(txtName);
                    if (!(descriptionPattern.matcher(txtDescription.getText()).matches())) {
                        addError(txtDescription);
                        return txtDescription;
                    } else {
                        removeError(txtDescription);
                        if (!(qtyPattern.matcher(txtQty.getText()).matches())) {
                            addError(txtQty);
                            return txtQty;
                        } else {
                            removeError(txtQty);
                            if (!(pricePattern.matcher(txtPrice.getText()).matches())) {
                                addError(txtPrice);
                                return txtPrice;
                            } else {
                                removeError(txtPrice);

                            }
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


