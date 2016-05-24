package Swing;

import javafx.stage.Modality;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dashu on 2016/4/21.
 */
public class SwingAlertBox {

    public static void display(String title, String message) {
        JOptionPane.showMessageDialog(null,message,title,JOptionPane.WARNING_MESSAGE);


    }
}
