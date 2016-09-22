package Swing;


import business_logic.User;
import database.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;


/**
 * Created by dashu on 2016/4/19.
 * version 1.0
 * This class shows The Front page. Input a table number
 */
public class SwingMainInterface extends JFrame {


    JTextField text;
    JLabel RestaurantName;
    JLabel copyRight;
    JLabel tableID;


    public static void main(String[] args) {

        new SwingMainInterface();
    }

    public SwingMainInterface() {
        setTitle("Shanghai Restaurant");
        RestaurantName = new JLabel("Shanghai Restaurant");
        RestaurantName.setFont(new Font("Arial", Font.BOLD, 30));
        RestaurantName.setForeground(Color.decode("#ab1a1a"));
        copyRight = new JLabel("© Copyright 2016 Shanghai Restaurant");
        tableID = new JLabel("Table Number");
        text = new JTextField();
        JButton confirm = new JButton("Confirm");
        confirm.setBackground(Color.decode("#7cafc2"));//set the background color
        confirm.setForeground(Color.white);//set text color by hex

        Box vBox = Box.createVerticalBox();
        Box hBox = Box.createHorizontalBox();
        RestaurantName.setAlignmentX(Component.CENTER_ALIGNMENT);
        hBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
        copyRight.setAlignmentX(Component.CENTER_ALIGNMENT);
        Box vBoxSmall = Box.createVerticalBox();
        hBox.add(tableID);
        hBox.add(Box.createHorizontalStrut(10));
        hBox.add(text);
        vBox.add(Box.createVerticalStrut(150));
        vBox.add(RestaurantName);
        vBoxSmall.add(hBox);
        vBoxSmall.add(Box.createVerticalStrut(10));
        vBoxSmall.add(confirm);
        vBox.add(Box.createVerticalStrut(70));
        vBox.add(vBoxSmall);
        vBox.add(Box.createVerticalStrut(70));
        vBox.add(copyRight);
        confirm.addActionListener(e -> {
            try {
                int tableNumber = Integer.parseInt(text.getText());
                //table number should be smaller than 100 and larger than0
                if (tableNumber < 100 && tableNumber > 0) {

                    //check if there has existed same table number
                    if (new User(tableNumber).toUser()) {
                        new SwingMainMenu(tableNumber);

                        dispose();
                    } else {
                        SwingAlertBox.display("Error", "Table Number exists");
                        text.setText("");
                        text.requestFocus();//set focus
                    }
                } else SwingAlertBox.display("Error", "Table Number should be larger than 0 and smaller than 101");
                text.setText("");
                text.requestFocus();
            } catch (Exception e1) {
                SwingAlertBox.display("Error", "Please input a number ");
                text.setText("");
                text.requestFocus();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        add(vBox);
        ImageIcon img = new ImageIcon("src/pictures/Kung Pao Chicken.jpg");
        setIconImage(img.getImage());
        setSize(new Dimension(500, 600));
        getRootPane().setDefaultButton(confirm);//if customers click enter.the new window will show like clicking confirm button
        setLocationRelativeTo(null);//set window in the center of screen
        setTitle("Shanghai Restaurant");
        setVisible(true);
        setResizable(false);
    }

}

