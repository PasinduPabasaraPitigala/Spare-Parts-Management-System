package model;

import to.OrderDetail;
import to.Orders;
import util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderModel {


    public boolean saveOrder(Orders orders) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO iron_horse.orders VALUES (?,?,?)",
                orders.getId(),orders.getDate(),orders.getCustomerId());
    }

    public boolean saveOrderDetails(ArrayList<OrderDetail> details) throws SQLException, ClassNotFoundException {
        for (OrderDetail od:details
        ) {
            boolean isDetailSaved=CrudUtil.execute("INSERT INTO iron_horse.orderdetail VALUES (?,?,?,?)",
                    od.getOderId(),od.getItemCode(),od.getQty(),od.getUnitPrice());
            if(isDetailSaved){
                if (!updateQty(od.getItemCode(),od.getQty())){
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    private boolean updateQty(String itemCode, String qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE iron_horse.item SET QTY=QTY-? WHERE Iid=?",qty,itemCode);
    }

    public static String getOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT oId FROM iron_horse.orders ORDER BY oId DESC LIMIT 1";
        ResultSet result = CrudUtil.execute(sql);

        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {
        String orderId = getOrderId();
        if (orderId != null) {
            String[] split = orderId.split("D0");
            int OrderID = Integer.parseInt(split[1]);
            OrderID += 1;
            return "D0" + OrderID;
        }
        return "D01";
    }


}