package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.repository.DatabaseConnect;
import com.bosch.stocktoship.service.InboundRequisitionForm;

class InboundRequisitionFormTest {

    private InboundRequisitionForm inboundRequisitionForm;

    @BeforeEach
    void setUp() throws Exception {
        inboundRequisitionForm = new InboundRequisitionForm();

        // Set up the 'products' table for testing
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();

            // Create the 'products' table for testing if it does not exist
            try {
                stmt.execute("CREATE TABLE products (name VARCHAR(255), product_code VARCHAR(255), length INT, breadth INT, height INT, weight INT, volume INT)");
            } catch (Exception e) {
                // Table already exists; continue
            }

            // Create the 'orders' table for testing if it does not exist
            try {
                stmt.execute("CREATE TABLE orders (name VARCHAR(255), product_code VARCHAR(255), batch INT, length INT, breadth INT, height INT, weight INT, quantity INT, supplier_name VARCHAR(255), supplier_location VARCHAR(255), order_number INT, issue_date DATE, delivery_date DATE, volume INT)");
            } catch (Exception e) {
                // Table already exists; continue
            }
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        // Drop the products and orders tables after each test to reset the database
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            try {
                stmt.execute("DROP TABLE products");
                stmt.execute("DROP TABLE orders");
            } catch (Exception e) {
                // Tables did not exist; continue
            }
        }
    }

    @Test
    void testGetProductByCode_ProductExists() throws Exception {
        // Insert a sample product into the products table
        try (Connection conn = DatabaseConnect.getConnection()) {
            String insertQuery = "INSERT INTO products (name, product_code, length, breadth, height, weight, volume) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, "Product1");
            preparedStatement.setString(2, "P001");
            preparedStatement.setInt(3, 10);
            preparedStatement.setInt(4, 20);
            preparedStatement.setInt(5, 30);
            preparedStatement.setInt(6, 40);
            preparedStatement.setInt(7, 6000);
            preparedStatement.executeUpdate();
        }

        // Fetch the product by code using the getProductByCode method
        ResultSet resultSet = inboundRequisitionForm.getProductByCode("P001");

        // Verify the result
        if (resultSet.next()) {
            assertEquals("Product1", resultSet.getString("name"));
            assertEquals("P001", resultSet.getString("product_code"));
        } else {
            fail("Product with code P001 was not found.");
        }
    }

    @Test
    void testInputNewProductDetails() throws ParseException, SQLException {
        // Prepare input data
        String code = "P002";
        Product newProduct = inboundRequisitionForm.inputNewProductDetails(code);

        // Verify that the product was added to the database
        try (Connection conn = DatabaseConnect.getConnection()) {
            String query = "SELECT * FROM products WHERE product_code = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, code);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                assertEquals("P002", resultSet.getString("product_code"));
                assertEquals(newProduct.getName(), resultSet.getString("name"));
                assertEquals(newProduct.getLength(), resultSet.getInt("length"));
                assertEquals(newProduct.getBreadth(), resultSet.getInt("breadth"));
                assertEquals(newProduct.getHeight(), resultSet.getInt("height"));
                assertEquals(newProduct.getWeight(), resultSet.getInt("weight"));
                assertEquals(newProduct.getVolume(), resultSet.getInt("volume"));
            } else {
                fail("New product was not inserted into the products table.");
            }
        }
    }

    @Test
    void testAddProductToProductsTable() throws Exception {
        // Create a new product object
        Product product = new Product("TestProduct", "Supplier1", "Location1", "P003", 1, 10, 20, 30, 40, 100, 5, 5000, new Date(), new Date());

        // Add the product to the database using the method
        inboundRequisitionForm.addProductToProductsTable(product);

        // Verify that the product was added to the products table
        try (Connection conn = DatabaseConnect.getConnection()) {
            String query = "SELECT * FROM products WHERE product_code = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "P003");
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                assertEquals("P003", resultSet.getString("product_code"));
                assertEquals("TestProduct", resultSet.getString("name"));
            } else {
                fail("Product P003 was not inserted into the database.");
            }
        }
    }

    

    @Test
    void testUpdateProductDetails() throws Exception {
        // Insert a sample product into the products table
        try (Connection conn = DatabaseConnect.getConnection()) {
            String insertQuery = "INSERT INTO products (name, product_code, length, breadth, height, weight, volume) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            preparedStatement.setString(1, "ProductToUpdate");
            preparedStatement.setString(2, "P005");
            preparedStatement.setInt(3, 10);
            preparedStatement.setInt(4, 20);
            preparedStatement.setInt(5, 30);
            preparedStatement.setInt(6, 40);
            preparedStatement.setInt(7, 6000);
            preparedStatement.executeUpdate();
        }

        // Fetch the product from the database
        ResultSet resultSet = inboundRequisitionForm.getProductByCode("P005");

        // Update the product details using the updateProductDetails method
        Product updatedProduct = new Product();
        if(resultSet.next()) {
        	 updatedProduct =  inboundRequisitionForm.updateProductDetails(resultSet);
        }

        // Verify that the product details were updated
        try (Connection conn = DatabaseConnect.getConnection()) {
            String query = "SELECT * FROM products WHERE product_code = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "P005");
            ResultSet updatedResultSet = preparedStatement.executeQuery();

            if (updatedResultSet.next()) {
                assertEquals(updatedProduct.getName(), updatedResultSet.getString("name"));
                assertEquals(updatedProduct.getLength(), updatedResultSet.getInt("length"));
                assertEquals(updatedProduct.getBreadth(), updatedResultSet.getInt("breadth"));
                assertEquals(updatedProduct.getHeight(), updatedResultSet.getInt("height"));
                assertEquals(updatedProduct.getWeight(), updatedResultSet.getInt("weight"));
            } else {
                fail("Updated product P005 was not found in the database.");
            }
        }
    }
}
