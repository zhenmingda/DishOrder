package dto;

/**
 * Created by dashu on 2016/4/2.
 *  * version 1.0
 * RecipeTo class that holds the data from the Recipe table and GUI
 */
public class RecipeTo {


    private int tableID;
    private int dishID;


    private String recipeName;
    private String introduction;
    private String category;
    private String price;
    private String image;


    private int number;

    public RecipeTo(int dishID, String recipeName, String introduction, String price, String category, String image) {
        this.dishID = dishID;
        this.recipeName = recipeName;
        this.introduction = introduction;
        this.price = price;
        this.category = category;
        this.image = image;
    }

    public RecipeTo(int tableID, int dishID, String recipeName, int number, String price, String image) {
        this.tableID = tableID;
        this.dishID = dishID;
        this.recipeName = recipeName;
        this.image = image;
        this.price = price;
        this.number = number;
    }

    public int getTableID() {
        return tableID;
    }

    public void setTableID(int tableID) {
        this.tableID = tableID;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }


    public String getRecipeName() {
        return recipeName;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}