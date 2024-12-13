package com.bosch.stocktoship;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.repository.DatabaseConnect;
import com.bosch.stocktoship.service.InboundRequisitionForm;

@SpringBootTest
class StocktoshipApplicationTests {

	@Test
	void contextLoads() {
	}
	
	 @Mock
	    private Connection connection;
	    
	    @Mock
	    private PreparedStatement preparedStatement;
	    
	    @Mock
	    private ResultSet resultSet;
	    
	    private InboundRequisitionForm inboundRequisitionForm;

	    @BeforeEach
	    void setUp() throws SQLException {
	        // Initialize the mock objects
	        MockitoAnnotations.openMocks(this);

	        // Mock the database connection
	        when(DatabaseConnect.getConnection()).thenReturn(connection);

	        // Initialize the service to be tested
	        inboundRequisitionForm = new InboundRequisitionForm();
	    }

	    @Test
	    void testInputNewProductDetails() throws Exception {
	        // Create a sample product code
	        String productCode = "P12345";

	        // Simulate user input
	        String name = "Product A";
	        int batch = 1;
	        int length = 10;
	        int breadth = 5;
	        int height = 3;
	        int weight = 50;
	        int quantity = 100;
	        String supplierName = "Supplier XYZ";
	        String supplierLocation = "Location ABC";
	        int orderNo = 1234;
	        String issueDateStr = "01-01-2024";
	        String deliveryDateStr = "01-02-2024";

	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        Date issueDate = dateFormat.parse(issueDateStr);
	        Date deliveryDate = dateFormat.parse(deliveryDateStr);

	        // Call the method that is being tested
	        Product product = inboundRequisitionForm.inputNewProductDetails(productCode);

	        // Verify that the returned product matches the input
	        assertNotNull(product);
	        assertEquals(name, product.getName());
	        assertEquals(productCode, product.getCode());
	        assertEquals(batch, product.getBatch());
	        assertEquals(length, product.getLength());
	        assertEquals(breadth, product.getBreadth());
	        assertEquals(height, product.getHeight());
	        assertEquals(weight, product.getWeight());
	        assertEquals(quantity, product.getQuantity());
	        assertEquals(supplierName, product.getSupplierName());
	        assertEquals(supplierLocation, product.getSupplierLocation());
	        assertEquals(orderNo, product.getOrderNo());
	        assertEquals(issueDate, product.getIssueDate());
	        assertEquals(deliveryDate, product.getDeliveryDate());
	    }

	    @Test
	    void testAddProductToProductsTable() throws SQLException {
	        // Create a sample product
	        Product product = new Product("Product A", "Supplier XYZ", "Location ABC", "P12345", 1, 10, 5, 3, 50, 100, 1234, 1500, new Date(), new Date());

	        // Mock the preparedStatement behavior
	        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
	        doNothing().when(preparedStatement).executeUpdate();

	        // Call the method to add product to the database
	        inboundRequisitionForm.addProductToProductsTable(product);

	        // Verify that the executeUpdate method was called once
	        verify(preparedStatement, times(1)).executeUpdate();
	    }

}
