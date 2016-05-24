package javafx;

import database.ConnectionManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import business_logic.Ingredient;
import business_logic.Order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dashu on 2016/4/15.
 * version 1.0
 * Generate a cutomized recipe offering some ingredients
 */
public class Customization {
    ConnectionManager con = new ConnectionManager();
    private String ingredient = "";
    private int tableNumber;
    private Label customizationName;
    private int dishID;
    private int price = 0;


    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public Label getCustomizationName() {
        return customizationName;
    }

    public void setCustomizationName(Label customizationName) {
        this.customizationName = customizationName;
    }

    public Customization(Label customizationName, int tableNumber, int dishID) {
        this.customizationName = customizationName;
        this.tableNumber = tableNumber;
        this.dishID = dishID;
        setGui();
    }

    private void setGui() {
        List<Ingredient> ingredientList = new Ingredient().readIngredient();
        Stage customizationStage = new Stage();
        ImageView imageView;
        CheckBox checkBox;
        ComboBox comboBox;
        Button confirmButton = new Button("Confirm");
        Label nameLabel;
        TextField amount = new TextField("1");
        Button addButton = new Button("+");
        Button minusButton = new Button("-");
        ScrollPane scrollPane = new ScrollPane();
        GridPane grid = new GridPane();
        scrollPane.setContent(grid);
        scrollPane.getStyleClass().add("edge-to-edge");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(30);
        int j = 0;
        con.connect();
        //check if there has existed a same name customized recipe
        ResultSet resultSet = con.query("select 1 from order1 where dishID='" + dishID + "'AND TableID='" + tableNumber + "'");
        try {

            while (resultSet.next()) {

                if (resultSet.getInt("1") == 1) {
                    con.disconnect();
                    customizationStage.close();
                }
            }
            con.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        amount.setEditable(false);

        //add amount
        addButton.setOnAction(e -> {
            int i = Integer.parseInt(amount.getText());
            i = i + 1;
            amount.setText(String.valueOf(i));
        });
        //minus amount
        minusButton.setOnAction(e -> {
            int i = Integer.parseInt(amount.getText());
            if (i == 1) {
                amount.setText("1");
            } else
                i = i - 1;
            amount.setText(String.valueOf(i));
        });
        //tranfer order data to "order1" table in the database
        confirmButton.setOnAction(e -> {
            if (price > 0) {
                Order order = new Order();
                order.setRecipeName(this.customizationName.getText());
                order.setAmount(amount.getText());
                order.setDishID(dishID);
                order.setPrice(String.valueOf(price * Integer.parseInt(amount.getText())));
                order.setIngredient(ingredient);
                order.toOrder(tableNumber);
                customizationStage.close();

            }
        });



        //choose ingredients from ingredient table in database. Add these in a grid pane with 3 columns
        for (int i = 0; i < ingredientList.size(); i++) {
            imageView = new ImageView("pictures/" + ingredientList.get(i).getImage());
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            nameLabel = new Label(ingredientList.get(i).getIngredientName());
            nameLabel.setPrefSize(200, 50);
            nameLabel.setAlignment(Pos.CENTER);
            checkBox = new CheckBox();
            comboBox = new ComboBox<String>();
            comboBox.getItems().addAll("1");
            comboBox.setValue("1");
            Label priceLabel = new Label(ingredientList.get(i).getPrice());
            HBox hBoxForAmount = new HBox(20);
            VBox vBox = new VBox(20);
            vBox.setAlignment(Pos.CENTER);
            hBoxForAmount.setAlignment(Pos.CENTER);
            hBoxForAmount.getChildren().addAll(checkBox, new Label("Amount :"), comboBox);
            HBox hBoxForPrice = new HBox(10);
            hBoxForPrice.getChildren().addAll(new Label("Price:"), priceLabel, new Label("Euro"));
            hBoxForPrice.setAlignment(Pos.CENTER);
            vBox.getChildren().addAll(imageView, nameLabel, hBoxForPrice, hBoxForAmount);
            addEventHandler(checkBox, comboBox, priceLabel, nameLabel);
            if (i % 3 == 0) {
                j = 0;
            }
            grid.getChildren().add(vBox);
            GridPane.setConstraints(vBox, j, i / 3);
            j++;
        }
        HBox hBoxForChoosingRecipeAmount = new HBox(10);
        hBoxForChoosingRecipeAmount.getChildren().addAll(minusButton, amount, addButton);
        hBoxForChoosingRecipeAmount.setAlignment(Pos.CENTER);
        VBox v1 = new VBox(20);
        v1.setPadding(new Insets(20, 10, 10, 10));
        v1.setAlignment(Pos.TOP_CENTER);
        v1.getChildren().addAll(new Label(this.customizationName.getText()), scrollPane, hBoxForChoosingRecipeAmount, confirmButton);
        //Display the window.
        customizationStage.setScene(new Scene(v1, 768, 1024));
        customizationStage.setMaxHeight(768);
        customizationStage.setMaxWidth(1024);
        customizationStage.getIcons().add(new Image("pictures/Kung Pao Chicken.jpg"));
        customizationStage.show();
    }

    //add listener for checkbox. If checkbox is not selected, combobox can not be activated
    private void addEventHandler(CheckBox checkBox, ComboBox comboBox, Label priceLabel, Label nameLabel) {
        comboBox.setDisable(true);
        checkBox.setOnMouseClicked(e -> {
            if (checkBox.isSelected()) {

                comboBox.setDisable(false);
                price += Integer.parseInt(priceLabel.getText());
                ingredient = ingredient + " , " + nameLabel.getText();
            } else {
                price -= Integer.parseInt(priceLabel.getText());
                ingredient = ingredient.replace(nameLabel.getText(), "");
                comboBox.setValue(null);
                comboBox.setDisable(true);

            }
        });

    }
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }


}