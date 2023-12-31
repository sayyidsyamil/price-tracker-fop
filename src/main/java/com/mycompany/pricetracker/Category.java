package com.mycompany.pricetracker;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.awt.GridLayout;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.tree.TreeSelectionModel;

public class Category extends javax.swing.JFrame {

    private static final String JDBC_URL = "jdbc:mysql://sql12.freesqldatabase.com:3306/sql12673794";
    private static final String DB_USER = "sql12673794";
    private static final String DB_PASSWORD = "jUagV5ukYC";

    private JTree jTree;
    public static String publicPath = "C:/Users/USER/Documents/NetBeansProjects/PriceTracker/pricecatcher_2023-08.csv";
    

    public Category(String publicPath) {
    this.publicPath = publicPath;
    initComponents();
    populateTree();
}

    private void populateTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("ITEM GROUPS");
        Set<String> itemGroups = readItemGroupsFromDatabase();

        for (String itemGroup : itemGroups) {
            DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(itemGroup);
            Set<String> itemCategories = readItemCategoriesFromDatabase(itemGroup);

            for (String itemCategory : itemCategories) {
                DefaultMutableTreeNode categoryNode = new DefaultMutableTreeNode(itemCategory);
                Set<String> items = readItemsFromDatabase(itemGroup, itemCategory);

                for (String item : items) {
                    categoryNode.add(new DefaultMutableTreeNode(item));
                }

                groupNode.add(categoryNode);
            }

            rootNode.add(groupNode);
        }

        jTree = new JTree(rootNode);
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

    // Add a TreeSelectionListener to the JTree
    jTree.addTreeSelectionListener(e -> {
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();

        // Check if the selected node is a leaf (item)
        if (selectedNode != null && selectedNode.isLeaf()) {
            String selectedItem = selectedNode.getUserObject().toString();
            displayActions(selectedItem);
        }
    });
        jScrollPane1.setViewportView(jTree);
    }

    private Set<String> readItemGroupsFromDatabase() {
        Set<String> itemGroups = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT DISTINCT item_group FROM lookup_item";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    String itemGroup = resultSet.getString("item_group");
                    itemGroups.add(itemGroup);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemGroups;
    }

    private Set<String> readItemCategoriesFromDatabase(String itemGroup) {
        Set<String> itemCategories = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT DISTINCT item_category FROM lookup_item WHERE item_group = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, itemGroup);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String itemCategory = resultSet.getString("item_category");
                        itemCategories.add(itemCategory);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemCategories;
    }

    private Set<String> readItemsFromDatabase(String itemGroup, String itemCategory) {
        Set<String> items = new HashSet<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT item FROM lookup_item WHERE item_group = ? AND item_category = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, itemGroup);
                preparedStatement.setString(2, itemCategory);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {

                    while (resultSet.next()) {
                        String item = resultSet.getString("item");
                        items.add(item);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setViewportView(jTree1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 232, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    
    
    private void displayActions(String selectedItem) {
        jPanel1.removeAll(); // Updated panel name
        jPanel1.setLayout(new GridLayout(5, 1)); // Updated panel name

        // Create buttons for each action
        String[] actions = {"View top 5 cheapest seller",
                "View price trend", "Add to shopping cart"};

        for (String action : actions) {
            JButton button = new JButton(action);
            button.addActionListener(e -> handleAction(selectedItem, action));
            jPanel1.add(button); // Updated panel name
        }

        // Repaint the panel to reflect the changes
        jPanel1.revalidate(); // Updated panel name
        jPanel1.repaint(); // Updated panel name
    }
    
    private void handleAction(String selectedItem, String action) {
    jTextArea1.append("Selected Item: " + selectedItem + ", Action: " + action);

    if (action.equals("View top 5 cheapest seller")) {
        try {
            // Check if the selected item exists in the database
            String itemCode = getItemCode(selectedItem);
            System.out.println(itemCode);

            if (itemCode != null) {
                // Get details of the selected item from the CSV data
                List<String[]> itemDetails = getItemDetailsFromCSV(itemCode);

                // Display the details in jTextArea1
                displayItemDetails(itemDetails);
            } else {
                jTextArea1.append("\nItem not found in the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    else if (action.equals("View price trend")) {
        try {
            // Check if the selected item exists in the database
            String itemCode = getItemCode(selectedItem);

            if (itemCode != null) {
                // Get price trend data for the selected item from the CSV data
                List<String[]> priceTrendData = getPriceTrendDataFromCSV(itemCode);

                // Display the price trend in jTextArea1
                displayPriceTrend(priceTrendData);
            } else {
                jTextArea1.append("\nItem not found in the database.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    else if (action.equals("Add to shopping cart")) {
            try {
                // Check if the selected item exists in the database
                String itemCode = getItemCode(selectedItem);

                if (itemCode != null) {
                    // Get details of the selected item from the CSV data
                    List<String[]> itemDetails = getItemDetailsFromCSV(itemCode);

                    // Display the details in jTextArea1
                    displayItemDetails(itemDetails);

                    // Add the selected item to the shopping cart
                    addToShoppingCart(itemCode, selectedItem);
                } else {
                    jTextArea1.append("\nItem not found in the database.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any exception
            }
        }
}
    
   private void addToShoppingCart(String itemCode, String itemName) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
        // Check if the item is already in the shopping cart
        String checkQuery = "SELECT * FROM shopping_cart WHERE user_id = ? AND item_code = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            checkStatement.setInt(1, Login.userId);
            checkStatement.setString(2, itemCode);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Item is already in the cart, update the quantity
                    updateQuantityInCart(itemCode);
                } else {
                    // Item is not in the cart, insert a new record
                    insertNewItemToCart(itemCode);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating/adding item to shopping cart",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void updateQuantityInCart(String itemCode) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
        String updateQuery = "UPDATE shopping_cart SET quantity = quantity + 1 WHERE user_id = ? AND item_code = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, Login.userId);
            updateStatement.setString(2, itemCode);
            updateStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Item quantity updated in shopping cart!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error updating item quantity in shopping cart",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void insertNewItemToCart(String itemCode) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
        String insertQuery = "INSERT INTO shopping_cart (user_id, item_code, quantity) VALUES (?, ?, 1)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, Login.userId);
            insertStatement.setString(2, itemCode);
            insertStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "Item added to shopping cart successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error adding item to shopping cart",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}


// Helper method to get item_code for the selected item from the database
private String getItemCode(String selectedItem) {
    try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
        String query = "SELECT item_code FROM lookup_item WHERE item = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, selectedItem);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("item_code");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

// Helper method to get details of the selected item from the CSV data
private List<String[]> getItemDetailsFromCSV(String itemCode) throws IOException, CsvValidationException {
    String csvFilePath = publicPath;
    List<String[]> csvData = readCSV(csvFilePath);

    // Filter data for the selected item code
    return csvData.stream()
            .filter(entry -> entry.length > 1 && entry[2].equals(itemCode))
            .collect(Collectors.toList());
}

// Helper method to display the details of the selected item in jTextArea1
// Helper method to display the details of the selected item in jTextArea1

// Helper method to display the details of the selected item in JTextArea
private void displayItemDetails(List<String[]> itemDetails) {
    StringBuilder detailsText = new StringBuilder("\nTop 5 Cheapest Sellers of the Selected Item:\n");

    // Sort the itemDetails by price in ascending order
    itemDetails.sort(Comparator.comparingDouble(entry -> Double.parseDouble(entry[3])));

    // Display only the top 5 records
    int count = 0;
    for (String[] entry : itemDetails) {
        detailsText.append(Arrays.toString(entry)).append("\n");
        count++;
        if (count == 5) {
            break;
        }
    }

    // Set the text in the JTextArea
    jTextArea1.setText(detailsText.toString());
}

private List<String[]> getPriceTrendDataFromCSV(String itemCode) throws IOException, CsvValidationException {
    String csvFilePath = publicPath;
    List<String[]> csvData = readCSV(csvFilePath);

    // Filter data for the selected item code
    return csvData.stream()
            .filter(entry -> entry.length > 1 && entry[2].equals(itemCode))
            .collect(Collectors.toList());
}


private List<String[]> readCSV(String filePath) throws IOException, CsvValidationException {
    List<String[]> data = new ArrayList<>();
    try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            data.add(nextLine);
        }
    }
    return data;
}

private void viewPriceTrend(String selectedItem) {
    jTextArea1.setText("Selected Item: " + selectedItem + "\n");

    try {
        // Check if the selected item exists in the database
        String itemCode = getItemCode(selectedItem);

        if (itemCode != null) {
            // Get price trend data for the selected item from the CSV data
            List<String[]> priceTrendData = getPriceTrendDataFromCSV(itemCode);

            // Display the price trend in jTextArea1
            displayPriceTrend(priceTrendData);
        } else {
            jTextArea1.append("Item not found in the database.");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void displayPriceTrend(List<String[]> priceTrendData) {
    StringBuilder trendText = new StringBuilder("\nPrice Trend for the Selected Item:\n");
    trendText.append("Days | Price\n");
    trendText.append("------------------\n");

    for (String[] entry : priceTrendData) {
        String day = entry[0]; // Assuming day is in the first column
        String price = entry[3]; // Assuming price is in the fourth column
        trendText.append(String.format("%-4s | RM%s\n", day, price));
    }

    // Set the text in the JTextArea
    jTextArea1.setText(trendText.toString());
}






    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Category.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Category(publicPath).setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

}

