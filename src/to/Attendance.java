package to;

public class Attendance {
   private String Eid;
   private String Aid;
   private String TimeIn;
   private String TimeOut;
   private String Date;

    public Attendance() {
    }

    public Attendance(String eid, String aid, String timeIn, String timeOut, String date) {
        Eid = eid;
        Aid = aid;
        TimeIn = timeIn;
        TimeOut = timeOut;
        Date = date;
    }

    public String getEid() {
        return Eid;
    }

    public void setEid(String eid) {
        Eid = eid;
    }

    public String getAid() {
        return Aid;
    }

    public void setAid(String aid) {
        Aid = aid;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String timeOut) {
        TimeOut = timeOut;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "Eid='" + Eid + '\'' +
                ", Aid='" + Aid + '\'' +
                ", TimeIn='" + TimeIn + '\'' +
                ", TimeOut='" + TimeOut + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
