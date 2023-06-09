package model;

import db.DBConnection;
import to.Customer;
import to.Item;
import util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {

    public static boolean add(Item item) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO iron_horse.item VALUES (?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, item.getIid(), item.getICategory(), item.getIName(), item.getIDescription(), item.getQTY(), item.getPrice());
    }

    public static Item item(String CId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM iron_horse.item WHERE Iid = ?";
        ResultSet result = CrudUtil.execute(sql, CId);

        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getDouble(6)
            );
        }
        return null;
    }

    public static boolean remove(String Iid) throws SQLException, ClassNotFoundException {
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("Delete From iron_horse.item where Iid='" + Iid + "'") > 0;
    }

    public static boolean update(Item item) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Item set ICategory=?,IName=?,IDescription=?,QTY=?,Price=? where Iid=? ");
        preparedStatement.setObject(1,item.getICategory());
        preparedStatement.setObject(2,item.getIName());
        preparedStatement.setObject(3,item.getIDescription());
        preparedStatement.setObject(4,item.getQTY());
        preparedStatement.setObject(5,item.getPrice());
        preparedStatement.setObject(6,item.getIid());
        return preparedStatement.executeUpdate()>0;
    }

    public static Item search(String Iid) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM iron_horse.item WHERE Iid = ?";
        ResultSet result = CrudUtil.execute(sql, Iid);

        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getDouble(6)
            );
        }
        return null;
    }

    public static ArrayList loadItemId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Iid FROM iron_horse.item";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

}
