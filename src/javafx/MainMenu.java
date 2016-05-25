package javafx;
/**
 * Created by dashu on 2016/4/4.
 * version 1.0
 * This class is to show MainInterface Menu
 */

import database.ConnectionManager;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import business_logic.Recipe;
import javafx.stage.WindowEvent;

import java.util.LinkedList;
import java.util.List;

import static javafx.scene.text.FontWeight.BOLD;

public class MainMenu {
    List<Recipe> recipeList;
    ConnectionManager con = new ConnectionManager();
    TreeView<String> tree;
    GridPane grid;
    ScrollPane scrollPane = new ScrollPane();
    Label hintLabel = new Label("You can customize your recipe");
    BorderPane borderPane = new BorderPane();
    List<ImageView> im = new LinkedList<>();
    Button viewOrder = new Button("View Order");
    Stage mainMenu;
    private int tableNumber;


    public MainMenu(int tableNumber) {


        this.tableNumber = tableNumber;
        next();
    }

    public void next() {
        mainMenu = new Stage();
        mainMenu.setTitle("Shanghai Restaurant");
        Button backToMainInterface = new Button("Back to choosing table number");
        backToMainInterface.setPrefSize(200, 40);
        backToMainInterface.setOnAction(e -> backToMainInterface());//rechoose table number
        TreeItem<String> root, meat, beverage, vegetables, customization;

        //Root
        root = new TreeItem<>();
        root.setExpanded(true);

        //Meat
        meat = makeTree("Meat", root);

        makeTree("Pork", meat);
        makeTree("Beef", meat);
        makeTree("Chicken", meat);

        //vegetables
        vegetables = makeTree("Vegetables", root);

        //Beverage
        beverage = makeTree("Beverage", root);


        //customization
        customization = makeTree("Customization", root);


        //Create the tree and hide the main Root
        tree = new TreeView<>(root);
        tree.setShowRoot(false);
        tree.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tree.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            if (newValue == null)
                //Nothing is selected.
                return;
            if (newValue.isLeaf()) {
                String value =newValue.getValue();

                show(value);
            }
        });
        StackPane stackPane = new StackPane();//let child nodes be set in the center
        stackPane.getChildren().addAll(backToMainInterface);
        borderPane.setLeft(tree);
        borderPane.setBottom(stackPane);
        //Display the window.
        Scene scene = new Scene(borderPane, 1024, 768);
        mainMenu.setMaxHeight(700);
        mainMenu.setMaxWidth(1024);
        mainMenu.setScene(scene);
        mainMenu.getIcons().add(new Image("pictures/Kung Pao Chicken.jpg"));
        mainMenu.setResizable(false);
        mainMenu.show();
        mainMenu.setOnCloseRequest(event -> event.consume());//window will not be close
    }

    private void show(String value) {
        ImageView imageView;
        Label nameLabel;
        grid = new GridPane();
        grid.setVgap(30);
        grid.setHgap(30);
        int j = 0;
        recipeList=new Recipe().readRecipeByCategory(value);
        for (int i = 0; i < recipeList.size(); i++) {
            imageView = new ImageView("pictures/" + recipeList.get(i).getImage());
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            nameLabel = new Label(recipeList.get(i).getRecipeName());
            if (value != "Customization")
                addEventHandler(imageView, nameLabel);
            else addEventHandlerForCustomization(imageView, nameLabel, recipeList.get(i).getDishID());//if choosing customization category,add this even thandler
            VBox v1 = new VBox(10);
            nameLabel.setAlignment(Pos.CENTER);
            v1.setAlignment(Pos.CENTER);
            if (i % 3 == 0) {
                j = 0;
            }
            GridPane.setConstraints(v1, j, i / 3);
            v1.getChildren().addAll(imageView, nameLabel);
            grid.getChildren().add(v1);
            j++;
        }
        scrollPane.setContent(grid);
        scrollPane.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.getStyleClass().add("edge-to-edge");//clear the border line
        viewOrder.setOnAction(e -> new ShowOrder(tableNumber));
        VBox v2 = new VBox(20);
        v2.setAlignment(Pos.TOP_CENTER);
        v2.getChildren().add(viewOrder);
        if (value == "Customization") {
            hintLabel.setFont(Font.font("Arial", BOLD, 15));
            v2.getChildren().add(hintLabel);
        }
        v2.setPadding(new Insets(20, 10, 10, 10));
        v2.getChildren().add(scrollPane);
        borderPane.setCenter(v2);

    }

    private void addEventHandlerForCustomization(ImageView imageView, Label nameLabel, int dishID) {
        imageView.setOnMouseClicked((MouseEvent event) ->
                new Customization(nameLabel, tableNumber, dishID));
    }


    public void addEventHandler(ImageView imageView, Label nameLabel) {

        imageView.setOnMouseClicked((MouseEvent event) ->
                new Details(nameLabel.getText(), tableNumber)
        );
    }

    public TreeItem<String> makeTree(String title, TreeItem<String> parent) {
        TreeItem<String> treeItem = new TreeItem<>(title);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);
        return treeItem;
    }
    private void backToMainInterface() {
        try {
            con.connect();
            con.update("delete from user where TableID='" + tableNumber + "'");
            new MainInterface().start(new Stage());
            con.disconnect();
            mainMenu.close();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
