package to;

public class payment {
    private String Pid;
    private String Payment_Type;
    private String Description;

    public payment() {
    }
    public payment(String pid, String payment_Type, String description) {
        this.Pid = pid;
        this.Payment_Type = payment_Type;
        this.Description = description;
    }
    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getPayment_Type() {
        return Payment_Type;
    }

    public void setPayment_Type(String payment_Type) {
        Payment_Type = payment_Type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
    @Override
    public String toString() {
        return "payment{" +
                "Pid='" + Pid + '\'' +
                ", Payment_Type='" + Payment_Type + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }

}
