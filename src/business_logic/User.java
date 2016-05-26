package business_logic;

import dao.UserDao;
import database.ConnectionManager;
import dto.UserTo;

import java.util.List;

/**
 * Created by dashu on 2016/5/26.
 */
public class User {
    ConnectionManager db = new ConnectionManager();


    private int tableID;

    public User(int tableID) {
        this.tableID = tableID;
    }

    public boolean toUser() {

        UserDao tableDao = new UserDao(db);
        UserTo userTo = new UserTo(tableID);
        List<UserTo> judgeUserTo = tableDao.read(tableID);
        db.connect();
        for (UserTo i : judgeUserTo) {
            if (i.getTableID() == tableID)
                db.disconnect();
                return false;
        }
        tableDao.create(userTo);
             return true;
    }

    public void delete() {
        UserDao userDao = new UserDao(db);
        UserTo userTo = new UserTo(tableID);
        userDao.delete(userTo);
    }



    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }
}
