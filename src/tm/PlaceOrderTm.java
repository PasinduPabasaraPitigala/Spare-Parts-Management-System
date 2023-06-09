package tm;

import javafx.scene.control.Button;

public class PlaceOrderTm {
    private String code;
    private double unitPrice;
    private int qty;
    private String description;
    private double total;
    private Button btnDelete;

    public PlaceOrderTm() {
    }

    public PlaceOrderTm(String code, double unitPrice, int qty, String description, double total, Button btnDelete) {
        this.code = code;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.description = description;
        this.total = total;
        this.btnDelete = btnDelete;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(Button btnDelete) {
        this.btnDelete = btnDelete;
    }

    @Override
    public String toString() {
        return "PlaceOrderTm{" +
                "code='" + code + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", description='" + description + '\'' +
                ", total=" + total +
                ", btnDelete=" + btnDelete +
                '}';
    }
}
