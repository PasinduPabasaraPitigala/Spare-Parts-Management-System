package model;

import db.DBConnection;
import to.Customer;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    public static boolean add(Customer customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iron_horse.customer VALUES (?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql,customer.getCid(),customer.getCName(),customer.getAddress(),customer.getEmail(),customer.getTel_Number());

    }

    public static Customer search(String CId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM iron_horse.Customer WHERE Cid = ?";
        ResultSet result = CrudUtil.execute(sql, CId);

        if (result.next()) {
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5)
            );
        }
        return null;
    }

    public static boolean remove(String Cid) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From iron_horse.customer where Cid='" + Cid + "'") > 0;
    }

    public static boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Customer set CName=?,Address=?,Tel_Number=?,Email=? where Cid=? ");
        preparedStatement.setObject(1,customer.getCName());
        preparedStatement.setObject(2,customer.getAddress());
        preparedStatement.setObject(3,customer.getTel_Number());
        preparedStatement.setObject(4,customer.getEmail());
        preparedStatement.setObject(5,customer.getCid());
        return preparedStatement.executeUpdate()>0;
    }


    public static ArrayList loadCustomerIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Cid FROM iron_horse.customer";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }
}
