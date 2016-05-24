package dao;

import Idao.IIngredientDao;
import database.ConnectionManager;
import dto.IngredientTo;
import dto.RecipeTo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dashu on 2016/4/17.
 * version 1.0
 * This class implements the IIngredientDAO interface for MySQL access
 */
public class IngredientDao implements IIngredientDao {
    private ConnectionManager con;

    public IngredientDao(ConnectionManager con) {
        this.con = con;
        con.connect();
    }

    //read Ingredient data from ingredient table
    @Override
    public List<IngredientTo> readIngredient() {
        List<IngredientTo> res = new LinkedList<IngredientTo>();
        ResultSet rset = con.query("SELECT * FROM ingredient");
        try {
            while (rset.next()) {
                res.add(new IngredientTo(rset.getString("ingredient"), rset
                        .getString("price"), rset.getString("image")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.disconnect();
        return res;
    }
}
