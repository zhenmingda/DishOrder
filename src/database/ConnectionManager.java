package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ConnectionManager.java
 * 01.04.2016
 *
 * @author Mingda Zhen
 *         ConnectionManager class that manages connection to database and provides methods for access
 */
public class ConnectionManager {

    private static final String URL = "jdbc:mysql://localhost:3306/dishesorder";
    private final static String USER = "root";
    private final static String PASSWORD = "123456";
    private Connection con;

    public ConnectionManager() {
        this.con = null;

    }

    //connect to database
    public boolean connect() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                con = null;
            }
        }
        return false;
    }

    //test if it is connected
    public boolean isConnected(int timeout) {
        if (con != null) {
            try {
                return con.isValid(timeout);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    //disconnet database
    public void disconnect() {
        if (con != null) {
            try {
                con.close();
                con = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //get resultes from database upon query
    public ResultSet query(String query) {
        if (con != null) {
            Statement stmt;
            try {
                stmt = con.createStatement();
                return stmt.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // this method is to implement insert update delete action
    public int update(String query) {
        if (con != null) {
            Statement stmt;
            try {
                stmt = con.createStatement();
                return stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}