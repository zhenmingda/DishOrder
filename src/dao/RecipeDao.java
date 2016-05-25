package dao;

import Idao.IRecipeDao;
import com.sun.javafx.image.BytePixelSetter;
import database.ConnectionManager;
import dto.RecipeTo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dashu on 2016/4/2.
 * version 1.0
 * This class implements the IRecipeDao interface for MySQL access
 */
public class RecipeDao implements IRecipeDao {
    private ConnectionManager con;

    public RecipeDao(ConnectionManager con) {
        this.con = con;
        con.connect();
    }

    //Read recipe
    @Override
    public List<RecipeTo> readRecipe(String value) {
        List<RecipeTo> res = new LinkedList<RecipeTo>();
        ResultSet rset;
        if (value == "Beef" || value == "Pork" || value == "Chicken" || value == "Beverage" || value == "Customization"||value=="Vegetables")
            rset = con.query("SELECT * FROM dish where category='" + value + "'");

        else rset = con.query("SELECT * FROM dish where recipename='" + value + "'");
        try {
            while (rset.next()) {
                res.add(new RecipeTo(rset.getInt("dishID"), rset
                        .getString("recipename"), rset.getString("introduction"), rset
                        .getString("price"), rset.getString("category"),
                        rset.getString("image")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        con.disconnect();
        return res;
    }



}
