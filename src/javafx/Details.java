package javafx;

import business_logic.Order;
import business_logic.Recipe;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by dashu on 2016/4/6.
 * version 1.0
 * This class is to show details
 */
public class Details {

    int tableNumber;
    String recipeName;

    Label label;

    public Details(String recipeName, int tableNumber) {
        this.recipeName = recipeName;
        this.tableNumber = tableNumber;
        label = new Label(recipeName);
        setGui();
    }

    public void setGui() {
        Stage detailStage = new Stage();
        Recipe recipe = new Recipe().readRecipeByName(recipeName);
        Order order = new Order();
        TextField amount = new TextField("1");
        Button addButton = new Button("+");
        Button minusButton = new Button("-");
        Button confirmButton = new Button("Confirm");
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
        confirmButton.setOnAction(e -> {
            if (Integer.parseInt(amount.getText()) < 20) {
                order.setRecipeName(recipeName);
                order.setPrice(recipe.getPrice());
                order.setDishID(recipe.getDishID());
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

        ImageView imageView = new ImageView("pictures/" + recipe.getImage());
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        vBox.getChildren().addAll(new Label("DishID :" + " " + recipe.getDishID()), label, imageView,
                new Text(recipe.getIntroduction()), new Label("Price : " + recipe.getPrice() + " Euro"), hBoxForAmount, confirmButton);

        detailStage.setScene(new Scene(vBox, 1024, 768));
        detailStage.show();
    }
}
