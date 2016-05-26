package javafx;

import business_logic.Order;
import business_logic.Recipe;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


/**
 * Created by dashu on 2016/4/6.
 * version 1.0
 * This class is to show details
 */
public class Details {

    int tableNumber;
    String recipeName;



    public Details(String recipeName, int tableNumber) {
        this.recipeName = recipeName;
        this.tableNumber = tableNumber;

        setGui();
    }

    public void setGui() {
        Label nameLabel = new Label(recipeName);
        Stage detailStage = new Stage();
        List<Recipe> recipe = new Recipe().readRecipe(recipeName);
        Order order = new Order();
        TextField amount = new TextField("1");
        Button addButton = new Button("+");
        Button minusButton = new Button("-");
        Button confirmButton = new Button("Confirm");
        Label dishID = new Label("DishID :" + " " + recipe.get(0).getDishID());
        Text introduction = new Text(recipe.get(0).getIntroduction());
        Label priceLabel = new Label("Price : " + recipe.get(0).getPrice() + " Euro");


        handleAmount(amount, addButton, minusButton);
        confirmButton.setOnAction(e -> {
            if (Integer.parseInt(amount.getText()) < 20) {
                order.setRecipeName(recipeName);
                order.setPrice(recipe.get(0).getPrice());
                order.setDishID(recipe.get(0).getDishID());
                order.setAmount(amount.getText());
                order.setIngredient("");
                order.toOrder(tableNumber);
                detailStage.close();
            } else AlertBox.display("Error", "Amount is too large");
        });
        HBox hBoxForAmount = new HBox(20);

        amount.setPrefSize(50, 20);
        hBoxForAmount.getChildren().addAll(new Label("Amount :"), minusButton, amount, addButton);
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        hBoxForAmount.setAlignment(Pos.CENTER);

        ImageView imageView = new ImageView("pictures/" + recipe.get(0).getImage());
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        vBox.getChildren().addAll(dishID, nameLabel, imageView, introduction
                , priceLabel, hBoxForAmount, confirmButton);

        detailStage.setScene(new Scene(vBox, 500, 600));
        detailStage.getIcons().add(new Image("pictures/Kung Pao Chicken.jpg"));
        detailStage.setTitle(recipeName);

        detailStage.show();
    }
//increase or decrease amount
    private static void handleAmount(TextField amount, Button addButton, Button minusButton) {

        addButton.setOnAction(e -> {
            int i = Integer.parseInt(amount.getText());
            i = i + 1;
            amount.setText(String.valueOf(i));
        });
        minusButton.setOnAction(e -> {
            int i = Integer.parseInt(amount.getText());
            if (i == 1) {
                amount.setText("1");
            } else
                i = i - 1;
            amount.setText(String.valueOf(i));
        });
    }
}
