package Swing;

import business_logic.Order;
import business_logic.Recipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import java.util.List;

/**
 * Created by dashu on 2016/4/23.
 * version 1.0
 * this class is to show recipe details
 */
public class SwingDetails extends JFrame {

    private int tableNumber;
    private String recipeName;


    public SwingDetails(String recipeName, int tableNumber) throws IOException {
        this.recipeName = recipeName;
        this.tableNumber = tableNumber;
        setGui();
    }
// show GUI
    private void setGui() throws IOException {
        JLabel nameLabel = new JLabel(recipeName);
        List<Recipe> recipe = new Recipe().readRecipe(recipeName);
        Order order = new Order();
        JTextField amount = new JTextField("1", 5);
        amount.setEditable(false);

        JButton addButton = new JButton("+");
        JButton minusButton = new JButton("-");
        JButton confirmButton = new JButton("Confirm");
        handleAmount(amount, addButton, minusButton);
        //add recipe to order table in the database 
        confirmButton.addActionListener(e -> {
            if (Integer.parseInt(amount.getText()) < 20) {
                order.setRecipeName(recipeName);
                order.setPrice(recipe.get(0).getPrice());
                order.setDishID(recipe.get(0).getDishID());
                order.setAmount(amount.getText());
                order.setIngredient("");
                order.toOrder(tableNumber);
                dispose();
            } else JOptionPane.showMessageDialog(this, "Amount is too large", "Warning", JOptionPane.WARNING_MESSAGE);

        });
        Box hBox = Box.createHorizontalBox();
        JLabel amountLabel = new JLabel("Amount :");
        hBox.add(amountLabel);
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(minusButton);
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(amount);
        hBox.add(Box.createHorizontalStrut(20));
        hBox.add(addButton);

        Box vBox = Box.createVerticalBox();
        Image image = ImageIO.read(new File("src/pictures/" + recipe.get(0).getImage()));
        Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel imgageLabel = new JLabel(new ImageIcon(resizedImage));
        JLabel introLable = new JLabel(recipe.get(0).getIntroduction());
        JLabel priceLabel = new JLabel("Price : " + recipe.get(0).getPrice() + " Euro");
        JLabel idLabel = new JLabel("DishID :" + " " + recipe.get(0).getDishID());
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        introLable.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        vBox.add(idLabel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(nameLabel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(imgageLabel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(introLable);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(priceLabel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(hBox);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(confirmButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        add(vBox);
        //Display the window.
        setMinimumSize(new Dimension(500, 600));
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setTitle(recipeName);
        ImageIcon img = new ImageIcon("src/pictures/Kung Pao Chicken.jpg");
        setIconImage(img.getImage());
        setVisible(true);
pack();
    }
    //increase or decrease amount
    private static void handleAmount(JTextField amount, JButton addButton, JButton minusButton) {
        addButton.addActionListener(e -> {
            int i = Integer.parseInt(amount.getText());
            i = i + 1;
            amount.setText(String.valueOf(i));
        });
        minusButton.addActionListener(e -> {
            int i = Integer.parseInt(amount.getText());
            if (i == 1) {
                amount.setText("1");
            } else
                i = i - 1;
            amount.setText(String.valueOf(i));
        });
    }
}
