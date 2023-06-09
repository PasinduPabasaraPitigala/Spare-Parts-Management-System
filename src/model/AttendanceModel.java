package model;

import db.DBConnection;
import to.Attendance;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceModel {

    public static boolean add(Attendance attendance) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iron_horse.attendance VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,attendance.getEid(),attendance.getAid(),attendance.getTimeIn(),attendance.getTimeOut(),attendance.getDate());
    }

    public static boolean update(Attendance attendance) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Attendance set Aid=?,Time_in=?,Time_out=?,ADate=? where Eid=? ");
        preparedStatement.setObject(1,attendance.getAid());
        preparedStatement.setObject(2,attendance.getTimeIn());
        preparedStatement.setObject(3,attendance.getTimeOut());
        preparedStatement.setObject(4,attendance.getDate());
        preparedStatement.setObject(5,attendance.getEid());
        return preparedStatement.executeUpdate()>0;
    }

    public static boolean remove(String Aid) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From iron_horse.attendance where Aid='" + Aid + "'") > 0;

    }

    public static Attendance search(String aid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM iron_horse.attendance WHERE Aid = ?";
        ResultSet result = CrudUtil.execute(sql, aid);

        if (result.next()) {
            return new Attendance(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            );
        }
        return null;
    }
}
