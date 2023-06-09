package to;

public class Customer {
   private String Cid;
   private String CName;
   private String Address;
   private String Tel_Number;
   private String Email;


    public Customer() {
    }

    public Customer(String cid, String CName, String address, String tel_Number, String email) {
        Cid = cid;
        this.CName = CName;
        Address = address;
        Tel_Number = tel_Number;
        Email = email;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getTel_Number() {
        return Tel_Number;
    }

    public void setTel_Number(String tel_Number) {
        Tel_Number = tel_Number;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "Cid='" + Cid + '\'' +
                ", CName='" + CName + '\'' +
                ", Address='" + Address + '\'' +
                ", Tel_Number=" + Tel_Number +
                ", Email='" + Email + '\'' +
                '}';
    }
}
