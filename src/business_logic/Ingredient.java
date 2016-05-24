package business_logic;

import dao.IngredientDao;
import database.ConnectionManager;
import dto.IngredientTo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dashu on 2016/4/15.
 * version 1.0
 * Order class invokes DAO and DTO to transfer data
 */
public class Ingredient {
    private ConnectionManager db = new ConnectionManager();
    private String price;
    private String image;

    public Ingredient() {
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String ingredientName;

    private Ingredient(String ingredientName, String price, String image) {
        this.ingredientName = ingredientName;
        this.price = price;
        this.image = image;
    }

    //read Ingredient from ingredient table in database
    public List<Ingredient> readIngredient() {
        List<Ingredient> ingredientList = new LinkedList<>();
        IngredientDao ingredientDao = new IngredientDao(db);
        List<IngredientTo> ingredientTo = ingredientDao.readIngredient();
        for (IngredientTo i : ingredientTo) {
            ingredientList.add(new Ingredient(i.getIngredientName(), i.getPrice(), i.getImage()));
        }
        return ingredientList;
    }

}
