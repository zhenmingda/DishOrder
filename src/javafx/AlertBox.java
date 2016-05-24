package javafx;

import javafx.scene.control.*;

/**
 * Created by dashu on 2016/4/17.
 * version 1.0
 * This class is to provide a stactic method to give an alert dialog
 */
public class AlertBox {

    public static void display(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}