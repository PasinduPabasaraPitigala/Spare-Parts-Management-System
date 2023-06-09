package to;

public class Item {
    private String Iid;
    public  String ICategory;
    private String IName;
    private String QTY;
    private String IDescription;
    private double Price;

    public Item(String text, String txtCategoryText, String txtNameText, String txtDescriptionText, String txtQtyText, String s) {
    }

    public Item(String iid, String ICategory, String IName, String QTY, String IDescription, double price) {
        Iid = iid;
        this.ICategory = ICategory;
        this.IName = IName;
        this.QTY = QTY;
        this.IDescription = IDescription;
        this.Price = price;
    }

    public String getIid() {
        return Iid;
    }

    public void setIid(String iid) {
        Iid = iid;
    }

    public String getICategory() {
        return ICategory;
    }

    public void setICategory(String ICategory) {
        this.ICategory = ICategory;
    }

    public String getIName() {
        return IName;
    }

    public void setIName(String IName) {
        this.IName = IName;
    }

    public String getQTY() {
        return QTY;
    }

    public void setQTY(String QTY) {
        this.QTY = QTY;
    }

    public String getIDescription() {
        return IDescription;
    }

    public void setIDescription(String IDescription) {
        this.IDescription = IDescription;
    }

    public String getPrice() {
        return String.valueOf(Price);
    }

    public void setPrice(double price) {
        Price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "Iid='" + Iid + '\'' +
                ", ICategory='" + ICategory + '\'' +
                ", IName='" + IName + '\'' +
                ", QTY='" + QTY + '\'' +
                ", IDescription='" + IDescription + '\'' +
                ", Price=" + Price +
                '}';
    }
}

