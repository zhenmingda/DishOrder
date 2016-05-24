package Swing;

import business_logic.Order;
import business_logic.Recipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by dashu on 2016/4/23.
 * version 1.0
 * this class is to show recipe details
 */
public class SwingDetails extends JFrame {

    private int tableNumber;
    private String recipeName;
    JLabel label;


    public SwingDetails(String recipeName, int tableNumber) throws IOException {
        this.recipeName = recipeName;
        this.tableNumber = tableNumber;
        label = new JLabel(recipeName);
        setGui();
    }
// show GUI
    private void setGui() throws IOException {
        Recipe recipe = new Recipe().readRecipeByName(recipeName);
        Order order = new Order();
        JTextField amount = new JTextField("1", 5);
        amount.setEditable(false);

        JButton addButton = new JButton("+");
        JButton minusButton = new JButton("-");
        JButton confirmButton = new JButton("Confirm");
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
        //add recipe to order table in the database 
        confirmButton.addActionListener(e -> {
            if (Integer.parseInt(amount.getText()) < 20) {
                order.setRecipeName(recipeName);
                order.setPrice(recipe.getPrice());
                order.setDishID(recipe.getDishID());
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
        Image image = ImageIO.read(new File("src/pictures/" + recipe.getImage()));
        Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        JLabel imgageLabel = new JLabel(new ImageIcon(resizedImage));
        JLabel introLable = new JLabel(recipe.getIntroduction());
        JLabel priceLabel = new JLabel("Price : " + recipe.getPrice() + " Euro");
        JLabel idLabel = new JLabel("DishID :" + " " + recipe.getDishID());
        idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imgageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        hBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        introLable.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        vBox.add(idLabel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(label);
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
        setTitle("Details");
        ImageIcon img = new ImageIcon("src/pictures/Kung Pao Chicken.jpg");
        setIconImage(img.getImage());
        setVisible(true);
        pack();
    }
}
