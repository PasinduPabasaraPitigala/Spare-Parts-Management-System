package model;

import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderModel {

    public static ArrayList<String> loadCusIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Cid FROM iron_horse.Customer";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }

    public static ArrayList<String> loadItemIDs() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Iid FROM iron_horse.item";
        ResultSet result = CrudUtil.execute(sql);

        ArrayList<String> idList = new ArrayList<>();

        while (result.next()) {
            idList.add(result.getString(1));
        }
        return idList;
    }
}
