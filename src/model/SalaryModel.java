package model;

import db.DBConnection;
import to.Salary;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryModel {
    public static boolean add(Salary salary) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iron_horse.salary VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,salary.getSid(),salary.getEid(),salary.getSName(),salary.getAmount(),salary.getDescription());

    }

    public static boolean update(Salary salary) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Salary set Eid=?,SName=?,Amount=?,Description=? where Sid=? ");
        preparedStatement.setObject(1,salary.getEid());
        preparedStatement.setObject(2,salary.getSName());
        preparedStatement.setObject(3,salary.getAmount());
        preparedStatement.setObject(4,salary.getDescription());
        preparedStatement.setObject(5,salary.getSid());
        return preparedStatement.executeUpdate()>0;
    }

    public static boolean remove(String sid) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From iron_horse.salary where Sid='" + sid + "'") > 0;

    }

    public static Salary search(String sid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM iron_horse.salary WHERE Sid = ?";
        ResultSet result = CrudUtil.execute(sql, sid);

        if (result.next()) {
            return new Salary(
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
