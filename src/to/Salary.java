package to;

public class Salary {
    private String Sid;
    private String Eid;
    private String SName;
    private String Amount;
    private String Description;

    public Salary() {
    }

    public Salary(String sid, String eid, String SName, String amount, String description) {
        Sid = sid;
        Eid = eid;
        this.SName = SName;
        Amount = amount;
        Description = description;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getEid() {
        return Eid;
    }

    public void setEid(String eid) {
        Eid = eid;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public String toString() {
        return "Salary{" +
                "Sid='" + Sid + '\'' +
                ", Eid='" + Eid + '\'' +
                ", SName='" + SName + '\'' +
                ", Amount='" + Amount + '\'' +
                ", Description='" + Description + '\'' +
                '}';
    }
}
