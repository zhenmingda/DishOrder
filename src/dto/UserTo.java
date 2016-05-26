package dto;

/**
 * Created by dashu on 2016/5/26.
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
