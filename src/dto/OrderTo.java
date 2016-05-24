package dto;

/**
 * Created by dashu on 2016/4/8.
 *  * version 1.0
 * OrderTo class that holds the data from the "order1" table and GUI
 */
public class OrderTo {

    private int tableID;
    private String recipeName;
    private String price;

private String ingredient;
    private int dishID;
    private String amount;

public OrderTo(){

    }
    public OrderTo(int tableID, int dishID,String recipeName,String ingredient,String amount,String price) {
        this.tableID=tableID;
        this.recipeName = recipeName;
        this.price = price;
this.ingredient=ingredient;
        this.dishID = dishID;
        this.amount = amount;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
