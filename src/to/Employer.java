package to;

public class Employer {
   private String Eid;
   private String EName;
   private String EAddress;
   private String Email;
   private String Tel_Number;
   private String jobRole;

    public Employer() {

    }

    public Employer(String eid, String EName, String EAddress, String email, String tel_Number, String jobRole) {
        Eid = eid;
        this.EName = EName;
        this.EAddress = EAddress;
        Email = email;
        Tel_Number = tel_Number;
        this.jobRole = jobRole;
    }

    public String getEid() {
        return Eid;
    }

    public void setEid(String eid) {
        Eid = eid;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public String getEAddress() {
        return EAddress;
    }

    public void setEAddress(String EAddress) {
        this.EAddress = EAddress;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTel_Number() {
        return Tel_Number;
    }

    public void setTel_Number(String tel_Number) {
        Tel_Number = tel_Number;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    @Override
    public String toString() {
        return "Employer{" +
                "Eid='" + Eid + '\'' +
                ", EName='" + EName + '\'' +
                ", EAddress='" + EAddress + '\'' +
                ", Email='" + Email + '\'' +
                ", Tel_Number='" + Tel_Number + '\'' +
                ", jobRole='" + jobRole + '\'' +
                '}';
    }
}
