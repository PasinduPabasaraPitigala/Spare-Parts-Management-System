package model;

import db.DBConnection;
import to.Employer;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployerModel {


    public static boolean add(Employer employer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iron_horse.employer VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,employer.getEid(),employer.getEName(),employer.getEAddress(),employer.getEmail(),employer.getTel_Number(),employer.getJobRole());

    }

    public static Employer search(String eid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM iron_horse.employer WHERE Eid = ?";
        ResultSet result = CrudUtil.execute(sql, eid);

        if (result.next()) {
            return new Employer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6)
            );
        }
        return null;
    }

    public static boolean update(Employer employer) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Employer set EName=?,EAddress=?,Email=?,Tel_Number=?,jobRole=? where Eid=? ");
        preparedStatement.setObject(1,employer.getEName());
        preparedStatement.setObject(2,employer.getEAddress());
        preparedStatement.setObject(3,employer.getEmail());
        preparedStatement.setObject(4,employer.getTel_Number());
        preparedStatement.setObject(5,employer.getJobRole());
        preparedStatement.setObject(6,employer.getEid());
        return preparedStatement.executeUpdate()>0;
    }

    public static boolean delete(String eid) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM iron_horse.employer where Eid='"+eid+"'")>0;

    }

    /*public static boolean update(Employer employer) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Employer set EName=?,EAddress=?,Email=?,Tel_Number=?,jobRole=? where Eid=?");
        preparedStatement.setObject(1,employer.getEid());
        preparedStatement.setObject(2,employer.getEName());
        preparedStatement.setObject(3,employer.getEAddress());
        preparedStatement.setObject(4,employer.getEmail());
        preparedStatement.setObject(5,employer.getTel_Number());
        preparedStatement.setObject(6,employer.getJobRole());
        return preparedStatement.executeUpdate()>0;
    }*/
}
