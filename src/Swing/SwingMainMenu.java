package Swing;

import business_logic.Order;
import business_logic.User;
import database.ConnectionManager;


import business_logic.Recipe;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by dashu on 2016/4/21.
 * version 1.0
 * This class is to show MainInterface Menu
 */
public class SwingMainMenu extends JFrame {

    List<Recipe> recipeList;
    JLabel hintLabel = new JLabel();
    JButton backToMainButton = new JButton("Back to choosing table number");
    ConnectionManager con = new ConnectionManager();
    JTree tree;
    JButton viewOrder = new JButton("View Order");
    int tableNumber;
    JPanel jPanel = new JPanel();
    JScrollPane jScrollPane = new JScrollPane(jPanel);

    GridLayout grid = new GridLayout(0, 3);


    public SwingMainMenu(int tableNumber) {
        this.tableNumber = tableNumber;
        next();
    }

    //show GUI
    private void next() {
        DefaultMutableTreeNode root, meat, beverage, vegetables, customization;
        backToMainButton.addActionListener(e -> {
            backToMainInterface();
        });//rechoose table number

        root = new DefaultMutableTreeNode();//root tree node

        // tree node Meat
        meat = makeTree("Meat", root);
        makeTree("Pork", meat);
        makeTree("Beef", meat);
        makeTree("Chicken", meat);

        // tree node vegetables
        vegetables = makeTree("Vegetables", root);

        // tree node Beverage
        beverage = makeTree("Beverage", root);


        //ctree node customization
        customization = makeTree("Customization", root);

        // Create the tree and conceal the root
        tree = new JTree(root);

        addIconToTreeNode(tree);


        tree.setRootVisible(false);
        tree.expandPath(new TreePath(meat.getPath()));
        add(tree, BorderLayout.WEST);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (node == null)
                //Nothing is selected.
                return;
            //if node is a leaf then showing a new window
            if (node.isLeaf()) {
                String newValue = String.valueOf(node);
                try {
                    show(newValue);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        JPanel jSouth = new JPanel();
        jSouth.add(backToMainButton);
        add(jSouth, BorderLayout.SOUTH);
        viewOrder.addActionListener(e -> {
            new SwingShowOrder(tableNumber);

        });
        viewOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Display the window.
        setSize(1024, 700);

        setLocationRelativeTo(null);
        setTitle("SwingMainInterface Menu");
        ImageIcon img = new ImageIcon("src/pictures/Kung Pao Chicken.jpg");
        setIconImage(img.getImage());
        setVisible(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }



    private void backToMainInterface() {
        try {
            new User(tableNumber).delete();
            new SwingMainInterface();
            dispose();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    private void show(String value) throws IOException {
        jPanel.removeAll();
        grid.setVgap(50);
        grid.setHgap(10);
        jPanel.setLayout(grid);
        recipeList = new Recipe().readRecipe(value);
        //let image and nameLabel be a small vbox added in the grid
        for (int i = 0; i < recipeList.size(); i++) {
            Image image = ImageIO.read(new File("src/pictures/" + recipeList.get(i).getImage()));
            Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
            JLabel imageLabel = new JLabel(new ImageIcon(resizedImage));
            JLabel nameLabel = new JLabel(recipeList.get(i).getRecipeName());
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            if (value != "Customization") {
                addEventHandler(imageLabel, nameLabel, recipeList.get(i).getDishID());
            } else {
                addEventHandlerForCustomization(imageLabel, nameLabel, recipeList.get(i).getDishID());

            }

            Box v1 = Box.createVerticalBox();//create a vertical box for each recipe
            v1.add(Box.createVerticalStrut(10));
            v1.add(imageLabel);
            v1.add(Box.createVerticalStrut(10));
            v1.add(nameLabel);
            jPanel.add(v1);

        }
        jScrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));//clear the border line and add paddings
        Box v2 = Box.createVerticalBox();
        v2.add(Box.createVerticalStrut(10));
        v2.add(viewOrder);
        v2.add(Box.createVerticalStrut(10));
        if (value == "Customization") {
            hintLabel.setText("You can customize your recipe");
            hintLabel.setFont(new Font("Arial", Font.BOLD, 15));
            hintLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            v2.add(hintLabel);
            v2.add(Box.createVerticalStrut(10));
        } else
            hintLabel.setText("");//the text of hint nameLabel will appear if clicking another category aftering clicking customization.
        // So it should be set null
        v2.add(jScrollPane);
        add(v2, BorderLayout.CENTER);
    }

    //add listener for image.

    private void addEventHandler(JLabel imageLabel, JLabel nameLabel, int dishID) {
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    new SwingDetails(nameLabel.getText(), tableNumber);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    //add listener for image in the context of Customization category. If the customization is created, it can not be created again
    //if customers click a image whose corresponding recipe has existed in the order table, an alert dialog will show
    private void addEventHandlerForCustomization(JLabel imageLabel, JLabel nameLabel, int dishID) {
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Order judgeOrder = new Order();
                judgeOrder.setDishID(dishID);
                if (judgeOrder.toOrder(tableNumber, false)) {
                    try {
                        new SwingCustomization(nameLabel, tableNumber, dishID);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(jPanel, "Please choose another customized recipe or delete it in Order Table", "Hint", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }


    //make a tree
    public DefaultMutableTreeNode makeTree(String title, DefaultMutableTreeNode parent) {
        DefaultMutableTreeNode item = new DefaultMutableTreeNode(title);
        parent.add(item);
        return item;
    }

    private static void addIconToTreeNode(JTree tree) {
        Image image = null;
        try {
            image = ImageIO.read(new File("src/pictures/Potato.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image resizedImage = image.getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(resizedImage);
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(icon);
        renderer.setOpenIcon(icon);
        renderer.setClosedIcon(icon);
        tree.setCellRenderer(renderer);
    }

}
