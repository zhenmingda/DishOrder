package dto;

/**
 * Created by dashu on 2016/5/26.
 *
 *   version 1.0
 * UserTo class that holds the data from the user table and GUI
 */

public class UserTo {
    private int tableID;

    public UserTo(int tableID) {
        this.tableID = tableID;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }
}
