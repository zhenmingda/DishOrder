package Swing;

import database.ConnectionManager;
import business_logic.Ingredient;
import business_logic.Order;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dashu on 2016/4/23.
 * version 1.0
 * this class is to show recipe details
 */
public class SwingCustomization extends JFrame {


    String ingredient = "";
    private int tableNumber;
    private JLabel customizationName;
    private int dishID;
    private int price = 0;

    public SwingCustomization(JLabel customizationName, int tableNumber, int dishID) throws IOException {
        this.customizationName = customizationName;
        this.tableNumber = tableNumber;
        this.dishID = dishID;
        setGui();
    }

    //show GUI
    private void setGui() throws IOException {
        List<Ingredient> ingredientList = new Ingredient().readIngredient();

        JButton confirmButton = new JButton("Confirm");
        ConnectionManager con = new ConnectionManager();
        con.connect();
        ResultSet resultSet = con.query("select 1 from order1 where dishID='" + dishID + "'AND TableID='" + tableNumber + "'");
        try {

            while (resultSet.next()) {

                if (resultSet.getInt("1") == 1) {
                    con.disconnect();
                    dispose();
                }
            }
            con.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JTextField amount = new JTextField("1", 5);
        amount.setEditable(false);

        JButton addButton = new JButton("+");
        JButton minusButton = new JButton("-");
//add amount
        addButton.addActionListener(e -> {
            int i = Integer.parseInt(amount.getText());
            i = i + 1;
            amount.setText(String.valueOf(i));
        });
        //minus amount
        minusButton.addActionListener(e -> {
            int i = Integer.parseInt(amount.getText());
            if (i == 1) {
                amount.setText("1");
            } else
                i = i - 1;
            amount.setText(String.valueOf(i));
        });
        //tranfer order data to "order1" table in the database
        confirmButton.addActionListener(e -> {
            if (price > 0) {
                Order order = new Order();
                order.setRecipeName(customizationName.getText());
                order.setAmount(amount.getText());
                order.setDishID(dishID);
                order.setPrice(String.valueOf(price * Integer.parseInt(amount.getText())));
                order.setIngredient(ingredient);
                order.toOrder(tableNumber);
                dispose();
            }
        });

        GridLayout grid = new GridLayout(0, 3);
        grid.setVgap(8);
        grid.setHgap(30);


        JPanel jPanel = new JPanel();
        jPanel.setLayout(grid);

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        //choose ingredients from ingredient table in database. Add these in a pane; with grid layout with 3 columns
        for (int i = 0; i < ingredientList.size(); i++) {
            JLabel priceLabel = new JLabel(ingredientList.get(i).getPrice());
            Image image = ImageIO.read(new File("src/pictures/" + ingredientList.get(i).getImage()));
            Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
            JLabel nameLabel = new JLabel(ingredientList.get(i).getIngredientName());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JCheckBox checkBox = new JCheckBox();
            JComboBox comboBox = new JComboBox<String>();
            comboBox.setPreferredSize(new Dimension(20, 20));
            comboBox.addItem("1");
            comboBox.setEnabled(false);
            addEventHandler(checkBox, comboBox, priceLabel, nameLabel); //add listener for checkbox. If checkbox is not selected, combobox can not be activated


            Box hBoxForAmount = Box.createHorizontalBox();//create a horizontal box for amount line
            Box vBoxForEachIngredient = Box.createVerticalBox();//create a vertical box for every ingredient's information
            hBoxForAmount.add(checkBox);
            hBoxForAmount.add(Box.createHorizontalStrut(10));
            hBoxForAmount.add(new Label("Amount :"));
            hBoxForAmount.add(Box.createHorizontalStrut(10));
            hBoxForAmount.add(comboBox);
            Box hBoxForPrice = Box.createHorizontalBox();//create a horizontal box for price line
            hBoxForPrice.add(new JLabel("Price:"));
            hBoxForPrice.add(priceLabel);
            hBoxForPrice.add(new Label(" Euro"));
            hBoxForAmount.setSize(20, 20);
            hBoxForPrice.setAlignmentX(Component.CENTER_ALIGNMENT);
            hBoxForAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
            vBoxForEachIngredient.add(imageLabel);
            vBoxForEachIngredient.add(Box.createVerticalStrut(10));
            vBoxForEachIngredient.add(nameLabel);
            vBoxForEachIngredient.add(Box.createVerticalStrut(10));
            vBoxForEachIngredient.add(hBoxForPrice);
            vBoxForEachIngredient.add(Box.createVerticalStrut(10));
            vBoxForEachIngredient.add(hBoxForAmount);
            vBoxForEachIngredient.add(Box.createVerticalStrut(10));
            jPanel.add(vBoxForEachIngredient);
        }
        Box hBoxForChoosingRecipeAmount = Box.createHorizontalBox();
        hBoxForChoosingRecipeAmount.add(minusButton);
        hBoxForChoosingRecipeAmount.add(Box.createHorizontalStrut(20));
        hBoxForChoosingRecipeAmount.add(amount);
        hBoxForChoosingRecipeAmount.add(Box.createHorizontalStrut(20));
        hBoxForChoosingRecipeAmount.add(addButton);
        Box v1 = Box.createVerticalBox();
        customizationName.setAlignmentX(Component.CENTER_ALIGNMENT);
        jScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        hBoxForChoosingRecipeAmount.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        v1.add(Box.createVerticalStrut(20));
        v1.add(customizationName);
        v1.add(Box.createVerticalStrut(20));
        v1.add(jScrollPane);
        v1.add(Box.createVerticalStrut(20));
        v1.add(hBoxForChoosingRecipeAmount);
        v1.add(Box.createVerticalStrut(20));
        v1.add(confirmButton);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout());
        add(v1);
        //Display the window.
        setMinimumSize(new Dimension(1024, 740));
        setLocationRelativeTo(null);
        setTitle("Customization");
        ImageIcon img = new ImageIcon("src/pictures/Kung Pao Chicken.jpg");
        setIconImage(img.getImage());
        setVisible(true);
        setResizable(false);
    }

    private void addEventHandler(JCheckBox checkBox, JComboBox comboBox, JLabel priceLabel, JLabel nameLabel) {
        checkBox.addItemListener(e -> {
            JCheckBox checkBox1 = (JCheckBox) e.getItemSelectable();
            if (checkBox1.isSelected()) {
                comboBox.setEnabled(true);
                price += Integer.parseInt(priceLabel.getText());
                ingredient = ingredient + nameLabel.getText() + " ";
            } else {
                price -= Integer.parseInt(priceLabel.getText());
                ingredient = ingredient.replace(nameLabel.getText(), "");
                comboBox.setEnabled(false);
            }
        });

    }
}
