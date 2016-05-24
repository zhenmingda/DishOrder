package dto;

/**
 * Created by dashu on 2016/4/17.
 * version 1.0
 * IngredientTo class that holds the data from the ingredient table and GUI
 */
public class IngredientTo {
    private String ingredientName;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }



    public String getImage() {
        return image;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public IngredientTo(String ingredientName, String price, String image) {
        this.ingredientName = ingredientName;
        this.price = price;
        this.image = image;
    }

    private String price;
    private String image;

}
