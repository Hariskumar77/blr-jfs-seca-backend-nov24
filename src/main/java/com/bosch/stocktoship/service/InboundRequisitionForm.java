package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.repository.DatabaseConnect;

public class InboundRequisitionForm {

    /**
     * @AUTHOR: CHARUL SAINI (SIC2COB)
     */
    
    // Scanner for user input
    Scanner scanner = new Scanner(System.in);

    // Connection to the database
    Connection con;

    // Method to get product by product code from the database
    public ResultSet getProductByCode(String productCode) throws SQLException {
        con = DatabaseConnect.getConnection();
        String query = "select * from products where product_code = ?";
        PreparedStatement preparedStatement = con.prepareStatement(query);
        preparedStatement.setString(1, productCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    // Method to input details for a new product when it doesn't exist in the database
    public Product inputNewProductDetails(String code) throws ParseException {
        System.out.println("Product doesn't exist, please enter new product details");

        // Date format for PO Insurance and Delivery Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Collect product details from the user
        System.out.println("\nProduct Name");
        String name = scanner.nextLine();

        System.out.println("\nBatch No.");
        int batch = Integer.parseInt(scanner.nextLine());

        System.out.println("\nProduct Dimensions");
        System.out.println("Length:");
        int length = Integer.parseInt(scanner.nextLine());
        System.out.println("Breadth");
        int breadth = Integer.parseInt(scanner.nextLine());
        System.out.println("Height");
        int height = Integer.parseInt(scanner.nextLine());

        System.out.println("\nProduct Weight:");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.println("\nProduct Qty.");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.println("\nSupplier Name");
        String supplierName = scanner.nextLine();

        System.out.println("\nSupplier Location");
        String supplierLocation = scanner.nextLine();

        System.out.println("\nPurchase Order no.");
        int orderNo = Integer.parseInt(scanner.nextLine());

        System.out.println("\nDate of PO Insurance");
        String date = scanner.nextLine();
        java.util.Date issueDate = dateFormat.parse(date);

        System.out.println("\nDate of Delivery");
        date = scanner.nextLine();
        java.util.Date deliveryDate = dateFormat.parse(date);

        // Calculate volume based on the product dimensions
        int volume = calculateVolume(length, breadth, height);

        // Create a new Product object with the collected details
        Product product = new Product(name, supplierName, supplierLocation, code, batch, length, breadth, height,
                weight, quantity, orderNo, volume, deliveryDate, issueDate);

        // Add the new product to the products table
        addProductToProductsTable(product);

        return product;
    }

    // Method to calculate the volume of a product
    private int calculateVolume(int length, int breadth, int height) {
        return length * breadth * height;
    }

    // Method to add a new product to the products table in the database
    public void addProductToProductsTable(Product product) {
        try {
            con = DatabaseConnect.getConnection();
            String query = "Insert into products values(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getCode());
            preparedStatement.setInt(3, product.getLength());
            preparedStatement.setInt(4, product.getBreadth());
            preparedStatement.setInt(5, product.getHeight());
            preparedStatement.setInt(6, product.getWeight());
            preparedStatement.setInt(7, product.getVolume());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error in addToProductsTable!!!");
            e.printStackTrace();
        }
    }

    // Method to insert a new order with the product details into the orders table
    public void insertNewOrder(Product product) {
        try {
            con = DatabaseConnect.getConnection();
            String query = "Insert into orders(product_name,PRODUCT_CODE,BATCH,LENGTH,BREADTH,HEIGHT,WEIGHT,QUANTITY,"
                    + "SUPPLIER_NAME,SUPPLIER_LOCATION,ORDER_NUMBER,ISSUE_DATE,DELIVERY_DATE,VOLUME) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getCode());
            preparedStatement.setInt(3, product.getBatch());
            preparedStatement.setInt(4, product.getLength());
            preparedStatement.setInt(5, product.getBreadth());
            preparedStatement.setInt(6, product.getHeight());
            preparedStatement.setInt(7, product.getWeight());
            preparedStatement.setInt(8, product.getQuantity());
            preparedStatement.setString(9, product.getSupplierName());
            preparedStatement.setString(10, product.getSupplierLocation());
            preparedStatement.setInt(11, product.getOrderNo());
            preparedStatement.setDate(12, new java.sql.Date(product.getIssueDate().getTime()));
            preparedStatement.setDate(13, new java.sql.Date(product.getDeliveryDate().getTime()));
            preparedStatement.setInt(14, product.getVolume());
            preparedStatement.executeUpdate();

            System.out.println("\nOrder Confirmed");

        } catch (SQLException e) {
            System.out.println("Error in insertNewOrder!!!");
            e.printStackTrace();
        }
    }

    // Method to update product details when the product exists in the database
    public Product updateProductDetails(ResultSet resultSet) throws Exception {
        // Fetch the current product details from the result set
        String name = resultSet.getString(1);
        String code = resultSet.getString(2);
        int length = resultSet.getInt(3);
        int breadth = resultSet.getInt(4);
        int height = resultSet.getInt(5);
        int weight = resultSet.getInt(6);
        int volume = resultSet.getInt(7);

        System.out.println("Product Details:");
        System.out.println("Name: " + name);
        System.out.println("Product Code: " + code);
        System.out.println("Length: " + length);
        System.out.println("Breadth: " + breadth);
        System.out.println("Height: " + height);
        System.out.println("Weight: " + weight);
        System.out.println("Volume: " + volume);

        // Collect updated details from the user
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        System.out.println("\nBatch No.");
        int batch = Integer.parseInt(scanner.nextLine());

        System.out.println("\nProduct Qty.");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.println("\nSupplier Name");
        String supplierName = scanner.nextLine();

        System.out.println("\nSupplier Location");
        String supplierLocation = scanner.nextLine();

        System.out.println("\nPurchase Order no.");
        int orderNo = Integer.parseInt(scanner.nextLine());

        System.out.println("\nDate of PO Insurance");
        String date = scanner.nextLine();
        java.util.Date issueDate = dateFormat.parse(date);

        System.out.println("\nDate of Delivery");
        date = scanner.nextLine();
        java.util.Date deliveryDate = dateFormat.parse(date);

        // Create and return the updated Product object
        Product product = new Product(name, supplierName, supplierLocation, code, batch, length, breadth, height,
                weight, quantity, orderNo, volume, deliveryDate, issueDate);

        return product;
    }
}
