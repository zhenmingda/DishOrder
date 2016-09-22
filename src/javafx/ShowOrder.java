package javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import business_logic.Order;

import java.util.List;

/**
 * Created by dashu on 2016/4/8.
 * version 1.0
 * This class is to show Order
 */
public class ShowOrder {
    private int tableNumber;

    int totalPrice = 0;


    public ShowOrder(int tableNumber) {
        this.tableNumber = tableNumber;
        setGui();
    }

    //Show GUI
    public void setGui() {

        Stage showOrder = new Stage();
        showOrder.setTitle("Your Order");
        VBox v1 = new VBox(20);
        v1.setAlignment(Pos.TOP_CENTER);
        v1.setPadding(new Insets(20, 0, 20, 0));
        /**
         * The setCellValueFactory method specifies a cell factory for each column.
         * The cell factories are implemented by using the PropertyValueFactory class,
         *which uses the recipeName, price, ingredient and amount properties of the table columns
         *as references to the corresponding methods of the Order class.
         *
         * In order to associate the data with the table columns. You can do this through the properties defined for each data element
         */

        //Name column
        TableColumn<Order, String> nameColumn = new TableColumn<>("RecipeName");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("recipeName"));

        //Price column
        TableColumn<Order, Integer> priceColumn = new TableColumn<>("Price/Euro");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //ingredients for customization column
        TableColumn<Order, Integer> ingredientColumn = new TableColumn<>("Ingredients");
        ingredientColumn.setMinWidth(100);
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("ingredient"));

        //Amount column
        TableColumn<Order, String> amountColumn = new TableColumn<>("Amount");
        amountColumn.setMinWidth(100);
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));// parameter amount should be a field in order object


        TableColumn deleteColumn = new TableColumn("Action");
        deleteColumn.setMinWidth(100);

        //Add button to column
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>(""));
        Callback<TableColumn<Order, String>, TableCell<Order, String>> cellFactory =
                new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
                    @Override
                    public TableCell call(final TableColumn<Order, String> param) {
                        final TableCell<Order, String> cell = new TableCell<Order, String>() {
                            final Button deleteButton = new Button("Delete");
                            @Override
                            public void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {
                                    //delete an order
                                    deleteButton.setOnAction(e ->
                                    {
                                        Order order = getTableView().getItems().get(getIndex());
                                        order.delete(tableNumber, order.getRecipeName());
                                        showOrder.close();
                                        new ShowOrder(tableNumber);
                                    });
                                    setGraphic(deleteButton);
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                };
        deleteColumn.setCellFactory(cellFactory);

        TableView<Order>  table = new TableView<>();
        table.setItems(show());
        table.getColumns().addAll(nameColumn, ingredientColumn, priceColumn, amountColumn, deleteColumn);
        Label tableNumber = new Label("Table Number:" + this.tableNumber);
        Label price = new Label("Total: " + totalPrice + " Euro");
        ScrollPane scrollPane = new ScrollPane(table);//  //Create the scroll pane and add the table to it.
        v1.getChildren().addAll(tableNumber, scrollPane, price);
        //Display the window.
        Scene scene = new Scene(v1);
        showOrder.setScene(scene);
        showOrder.getIcons().add(new Image("pictures/Kung Pao Chicken.jpg"));
        showOrder.show();//
    }

    public ObservableList<Order> show() {
        //the ObservableList object enables the tracking of any changes to its elements,
        // the TableView content automatically updates whenever the data changes.
        ObservableList<Order> orders = FXCollections.observableArrayList();
        List<Order> orderList = new Order().getOrder(tableNumber);
        //calculate total price and add order
        for (Order i : orderList) {
            orders.add(i);
            totalPrice += (Integer.parseInt(i.getPrice()) * Integer.parseInt(i.getAmount()));
        }
        return orders;
    }


}
