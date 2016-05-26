package business_logic;


import dao.OrderDao;
import database.ConnectionManager;
import dto.OrderTo;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by Mingda Zhen on 2016/4/8.
 * version 1.0
 * Order class invokes DAO and DTO to transfer data
 */


public class Order {
    ConnectionManager db = new ConnectionManager();
    private String recipeName;
    private String price;
    private String ingredient;
    private String image;
    private int dishID;
    private String amount;


    public Order(String recipeName, String ingredient, String price, String amount) {
        this.recipeName = recipeName;
        this.amount = amount;
        this.price = price;
        this.ingredient = ingredient;
    }

    public Order() {

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
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

    // tranfer order data to order table in the database by DAO. Prevent from adding order repeatedly
    public boolean toOrder(int tableID, boolean check) {//boolean check is to judge category customization or other categories
        List<OrderTo> judgeOrderTo = new LinkedList<>();
        OrderDao orderDao = new OrderDao(db);
        judgeOrderTo = orderDao.read(tableID);
        OrderTo orderTo = new OrderTo(tableID, dishID, recipeName, ingredient, amount, price);
        db.connect();
        for (OrderTo i : judgeOrderTo) {
            if (i.getDishID() == dishID) {
                if (check) {
                    orderDao.update(orderTo);
                    return false;
                } else
                    return false;
            }
        }
        orderDao.create(orderTo);
        return true;
    }

    // get order data from order table in the database by DAO
    public List<Order> getOrder(int tableNumber) {

        List<Order> orderList = new LinkedList<>();
        OrderDao orderDao = new OrderDao(db);
        List<OrderTo> orderTo = orderDao.read(tableNumber);
        for (OrderTo i : orderTo) {
            orderList.add(new Order(i.getRecipeName(), i.getIngredient(), i.getPrice(), i.getAmount()));

        }
        return orderList;

    }

    //delete order in the order table in database by DAO
    public void delete(int tableNumber, String recipeName) {
        OrderDao orderDao = new OrderDao(db);
        OrderTo orderTo = new OrderTo();
        orderTo.setTableID(tableNumber);
        orderTo.setRecipeName(recipeName);
        orderDao.delete(orderTo);
    }
}
