package business_logic;

import dao.RecipeDao;
import database.ConnectionManager;
import dto.RecipeTo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dashu on 2016/4/1.
 * version 1.0
 * Recipe class invokes DAO and DTO to get recipe information
 */
public class Recipe {
    ConnectionManager db = new ConnectionManager();
    private String recipeName;
    private String introduction;
    private String price;
    private String category;
    private String image;
    private int dishID;



    public String getRecipeName() {
        return recipeName;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }




    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //read recipe  from recipe table in database
    public List<Recipe> readRecipe(String category) {
        List<Recipe> recipeList = new LinkedList<Recipe>();

        RecipeDao recipeDao = new RecipeDao(db);
        List<RecipeTo> recipeToList = recipeDao.readRecipe(category);
        for (RecipeTo i : recipeToList) {
            Recipe recipe = new Recipe();
            recipe.setDishID(i.getDishID());
            recipe.setIntroduction(i.getIntroduction());
            recipe.setCategory(i.getCategory());
            recipe.setRecipeName(i.getRecipeName());
            recipe.setPrice(i.getPrice());
            recipe.setImage(i.getImage());
            recipeList.add(recipe);
        }

        return recipeList;
    }

}