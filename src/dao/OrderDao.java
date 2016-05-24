package dao;

import Idao.IOrderDao;
import database.ConnectionManager;
import dto.OrderTo;
import dto.RecipeTo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dashu on 2016/4/8.
 * version 1.0
 * This class implements the IOrderDAO interface for MySQL access
 */
public class OrderDao implements IOrderDao {
    private ConnectionManager con;

    public OrderDao(ConnectionManager con) {
        this.con = con;
        this.con.connect();
    }

//insert data into order table. Because "order" is a key word in SQL so that order1 becomes the table name
    @Override
    public void create(OrderTo to) {
        int update = con.update("INSERT INTO order1 VALUES('" + to.getTableID() + "','" + to.getDishID() + "','"
                + to.getRecipeName() + "','" + to.getIngredient() + "','" + to.getAmount() + "','"
                + to.getPrice() + "')");
        con.disconnect();
    }

//read data from Order Table
    @Override
    public List<OrderTo> read(int tableID) {
        List<OrderTo> res = new LinkedList<OrderTo>();
        ResultSet rset = con.query("SELECT * FROM order1 where TableID='" + tableID + "'");
        try {
            while (rset.next()) {
                res.add(new OrderTo(rset.getInt("TableID"), rset
                        .getInt("dishID"), rset.getString("recipename"), rset.getString("ingredient"), rset
                        .getString("amount"), rset.getString("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.disconnect();
        return res;


    }

//update data to Order Table
    @Override
    public void update(OrderTo to) {
        con.update("UPDATE order1 SET amount=amount+'" + to.getAmount()

                + "' WHERE TableID='" + to.getTableID() + "'and recipeName='" + to.getRecipeName() + "'");
        con.disconnect();
    }

//delete order in the order1 table
    @Override
    public void delete(OrderTo to) {
        con.update("DELETE FROM order1 WHERE TableID='" + to.getTableID() + "'and recipename='" + to.getRecipeName() + "'");
        con.disconnect();
    }


}
