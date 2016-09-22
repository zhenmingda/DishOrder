package Swing;

import business_logic.Order;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.List;


/**
 * Created by dashu on 2016/4/23.
 * version 1.0
 * This class is to show order
 */
public class SwingShowOrder extends JFrame {
    static int tableNumber;
    int totalPrice = 0;

    public int getTableNumber() {
        return tableNumber;
    }


    public SwingShowOrder(int tableNumber) {
        this.tableNumber = tableNumber;
        setGui();
    }

    private void setGui() {

        JLabel jLabel = new JLabel("Table ID: " + tableNumber);
        JTable table = new JTable(new MyTableModel());
        TableColumn column = null;
        List<Order> orderList = new Order().getOrder(tableNumber);
             //calculate total price
        for (Order i : orderList) {

             totalPrice += (Integer.parseInt(i.getPrice()) * Integer.parseInt(i.getAmount()));
        }
        JLabel priceLabel = new JLabel("Total: " + totalPrice + " Euro");

        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setMinWidth(100);
        }

        table.setPreferredScrollableViewportSize(new Dimension(500, 500));

        table.setAutoCreateRowSorter(true);
        table.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JTextField(), tableNumber, MyTableModel.data2, this));


        JScrollPane scrollPane = new JScrollPane(table);   //Create the scroll pane and add the table to it.
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(jLabel);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(scrollPane);
        vBox.add(Box.createVerticalStrut(20));
        vBox.add(priceLabel);
        vBox.add(Box.createVerticalStrut(20));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(vBox);


        //Display the window.
        pack();
        setTitle("View Order");
        ImageIcon img = new ImageIcon("src/pictures/Kung Pao Chicken.jpg");
        setIconImage(img.getImage());
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }


}

class MyTableModel extends AbstractTableModel {
    private String[] columnNames = {"RecipeName", "Price/Euro", "Ingredients", "Amount", "Action"};

    java.util.List<Order> orderList = new Order().getOrder(SwingShowOrder.tableNumber);


    private Object[][] data = new Object[orderList.size()][5];
    static Object[][] data2;

    public MyTableModel() {


        for (int i = 0; i < orderList.size(); i++) {

            data[i][0] = orderList.get(i).getRecipeName();
            data[i][1] = orderList.get(i).getPrice();
            data[i][2] = orderList.get(i).getIngredient();
            data[i][3] = orderList.get(i).getAmount();
            data[i][4] = "Delete";

        }
        data2 = data;
    }

    public int getColumnCount() {
        return columnNames.length;
    }
    public int getRowCount() {
        return data.length;
    }
//set column name
    public String getColumnName(int col) {
        return columnNames[col];
    }
//get cell content
    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    // JTable uses this method to determine the default renderer/editor for each cell.
    public Class getColumnClass(int c) {return getValueAt(0, c).getClass();}
    //set cell uneditable except button column
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col == 4) {
            return true;
        } else {
            return false;
        }
    }

}

/**
 * set the title of button
 */
class ButtonRenderer extends JButton implements TableCellRenderer {


    public ButtonRenderer() {
        //set button properties
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object obj,
                                                   boolean selected, boolean focused, int row, int col) {

        //set passed object as button text
        setText((obj == null) ? "" : obj.toString());
        return this;
    }

}

//button editor class
class ButtonEditor extends DefaultCellEditor {
    protected JButton btn;
    private String lbl;
    private Boolean clicked;
    int tableNumber;
    JFrame frame;
    Object data[][];

    public ButtonEditor(JTextField txt, int tableNumber, Object data[][], JFrame frame) {
        super(txt);
        this.frame = frame;
        this.tableNumber = tableNumber;
        btn = new JButton();
        btn.setOpaque(true);
        this.data = data;
    }



    //delete an order
    public Component getTableCellEditorComponent(JTable table, Object obj,
                                                 boolean selected, int row, int col) {

        //set text to button,set clicked to true,then return the button object

        new Order().delete(tableNumber, String.valueOf(data[row][0]));
       frame.dispose();
        new SwingShowOrder(tableNumber);
        return btn;
    }


}