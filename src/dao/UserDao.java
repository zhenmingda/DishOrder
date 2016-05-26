package dao;

import Idao.IUserDao;
import database.ConnectionManager;
import dto.UserTo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dashu on 2016/5/26.
 */
public class UserDao implements IUserDao {

    private ConnectionManager con;

    public UserDao(ConnectionManager con) {
        this.con = con;
        this.con.connect();
    }

    @Override
    public int create(UserTo to) {
        int update = con.update("insert into user values('" + to.getTableID() + "')");
        con.disconnect();
        return update;
    }

    @Override
    public List<UserTo> read(int tableID) {
        List<UserTo> res = new LinkedList<UserTo>();
        ResultSet rset = con.query("SELECT TableID FROM user where TableID='" + tableID + "'");
        try {
            while (rset.next()) {
                res.add(new UserTo(rset.getInt("TableID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.disconnect();
        return res;
    }

    @Override
    public void delete(UserTo to) {
        con.update("DELETE FROM user WHERE TableID='" + to.getTableID() +"'");
        con.disconnect();
    }
}
