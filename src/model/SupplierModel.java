package model;

import db.DBConnection;
import to.Supplier;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierModel {
    public static boolean add(Supplier supplier) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iron_horse.supplier VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,supplier.getSupId(),supplier.getName(),supplier.getAddress(),supplier.getEmail(),supplier.getTel_Number(),supplier.getDescription());
    }

    public static boolean update(Supplier supplier) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Supplier set Name=?,Address=?,Email=?,Tel_Number=?,Description=? where Supid=? ");
        preparedStatement.setObject(1,supplier.getName());
        preparedStatement.setObject(2,supplier.getAddress());
        preparedStatement.setObject(3,supplier.getEmail());
        preparedStatement.setObject(4,supplier.getTel_Number());
        preparedStatement.setObject(5,supplier.getDescription());
        preparedStatement.setObject(6,supplier.getSupId());
        return preparedStatement.executeUpdate()>0;
    }

    public static Supplier search(String supid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM iron_horse.supplier WHERE Supid = ?";
        ResultSet result = CrudUtil.execute(sql, supid);

        if (result.next()) {
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getInt(5),
                    result.getString(6)
            );
        }
        return null;
    }

    public static boolean remove(String Supid) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From iron_horse.supplier where Supid='" + Supid + "'") > 0;

    }
}
