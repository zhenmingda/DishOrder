package javafx;

import business_logic.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.scene.text.FontWeight.*;

public class MainInterface extends Application {



    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Shanghai Restaurant");
        VBox vbox1 = new VBox(70);
        vbox1.setAlignment(Pos.CENTER);

        VBox vbox2 = new VBox(10);
        Label nameLabel = new Label("Shanghai Restaurant");
        nameLabel.setFont(Font.font("Arial", BOLD, 30));
        nameLabel.setTextFill(Paint.valueOf("#ab1a1a"));
        Label copyRight = new Label("© Copyright 2016 Shanghai Restaurant");

        Label tableID = new Label("Table Number");
        TextField text = new TextField();

        text.setPromptText("01");
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(tableID, text);
        Button confirm = new Button("Confirm");

        confirm.setDefaultButton(true);
        confirm.setOnAction(e ->
        {
            try {
                int tableNumber = Integer.parseInt(text.getText());
//check if table number has existed
                if (tableNumber < 100 && tableNumber > 0) {

                    if (new User(tableNumber).toUser()) {

                        new MainMenu(tableNumber);
                        primaryStage.close();
                    } else {
                        AlertBox.display("Error", "Table Number exists");
                        text.clear();
                        text.requestFocus();
                    }
                } else AlertBox.display("Error", "Table Number should be larger than 0 and smaller than 101");
                text.clear();
                text.requestFocus();


            } catch (Exception e1) {
              AlertBox.display("Error", "Please input a number");
                text.clear();
                text.requestFocus();
            }

        });
        vbox2.getChildren().addAll(hbox, confirm);

        vbox1.getChildren().addAll(nameLabel, vbox2, copyRight);
        vbox2.setAlignment(Pos.CENTER);

        primaryStage.setScene(new Scene(vbox1, 500, 600));

        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("pictures/Kung Pao Chicken.jpg"));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> Platform.exit());//exit whole application
    }
}
