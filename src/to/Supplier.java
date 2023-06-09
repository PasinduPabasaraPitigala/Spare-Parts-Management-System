package to;

public class Supplier {
  private String SupId;
  private String Name;
  private String Address;
  private String Email;
  private int Tel_Number;
  private String Description;

    public Supplier() {
    }

    public Supplier(String supId, String name, String address, String email, int tel_Number, String description) {
        SupId = supId;
        Name = name;
        Address = address;
        Email = email;
        Tel_Number = tel_Number;
        Description = description;
    }

    public String getSupId() {
        return SupId;
    }

    public void setSupId(String supId) {
        SupId = supId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getTel_Number() {
        return Tel_Number;
    }

    public void setTel_Number(int tel_Number) {
        Tel_Number = tel_Number;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "SupId='" + SupId + '\'' +
                ", Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", Email='" + Email + '\'' +
                ", Tel_Number=" + Tel_Number +
                ", Description='" + Description + '\'' +
                '}';
    }
}
