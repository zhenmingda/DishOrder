package javafx;

import database.ConnectionManager;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static javafx.scene.text.FontWeight.*;

public class MainInterface extends Application {

    ConnectionManager con = new ConnectionManager();

    public static void main(String[] args) {

        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("Shanghai Restaurant");
        VBox vbox1 = new VBox(70);
        vbox1.setAlignment(Pos.CENTER);

        VBox vbox2 = new VBox(10);
        Label label1 = new Label("Shanghai Restaurant");
        label1.setFont(Font.font("Arial", BOLD, 30));
        label1.setTextFill(Paint.valueOf("#ab1a1a"));
        Label label2 = new Label("© Copyright 2016 Shanghai Restaurant");

        Label label3 = new Label("Table Number");
        // label3.setPrefSize(50,60);
        // label1.setfont;
        TextField text = new TextField();

        text.setPromptText("01");
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(label3, text);
        Button button = new Button("Confirm");
        text.requestFocus();
        button.setDefaultButton(true);
        button.setOnAction(e ->
        {
            try {
                int tableNumber = Integer.parseInt(text.getText());

                if (tableNumber < 100 && tableNumber > 0) {
                    con.connect();
                    if (con.update("insert into user values('" + tableNumber + "')") == 1) {

                        new MainMenu(tableNumber);

                        con.disconnect();
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
        vbox2.getChildren().addAll(hbox, button);
        vbox1.getChildren().addAll(label1, vbox2, label2);
        vbox2.setAlignment(Pos.CENTER);
        primaryStage.setScene(new Scene(vbox1, 500, 600));
        primaryStage.setMaxHeight(600);
        primaryStage.setMaxWidth(500);
        //Scene scene1=new Scene();
        primaryStage.getIcons().add(new Image("pictures/Kung Pao Chicken.jpg"));
        primaryStage.show();
    }
}
