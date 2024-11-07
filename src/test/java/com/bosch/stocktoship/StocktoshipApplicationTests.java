package com.bosch.stocktoship;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bosch.stocktoship.entity.InboundRequisition;
import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.OutboundRequisitionForm;
import com.bosch.stocktoship.entity.Product;

@SpringBootTest
class StocktoshipApplicationTests {

    private OutboundRequisitionForm outboundRequisitionForm;
    private Product product;
    private List<Location> locations;

    /**
     * AUTHOR: MUJAHID DAFEDAR(DAFB1KOR) - FOR INBOUND TESTING
     * AUTHOR: GURPARTAP SINGH(INU2COB) - FOR OUTBOUND TESTING
     */
    // Instance of InboundRequisition to be used in tests
    private InboundRequisition inboundRequisition;
    // 2D array representing a location grid, used for availability checks
    private Boolean[][] locationGrid;
 
    /**
     * Sets up the test environment before each test.
     * Initializes an InboundRequisition instance, clears any existing product data,
     * and creates a 3x5 location grid with all locations set to available (true).
     */
    
    
    @BeforeEach
    public void beforeTest() {
        // Create a dummy product with sample values for setup
        product = new Product("Sample Product", "1234", "10", "11", "12", "13", "14", "15", "Sample Supplier",
                "Sample Supplier Location", "963852741", "12-12-2014", "14-12-2014");

        // Initialize and add locations to the product
        locations = new ArrayList<>();
        locations.add(new Location(1, 5)); // Location with ID 1 and quantity 5
        locations.add(new Location(2, 6)); // Location with ID 2 and quantity 6
        product.setLocationList(locations); // Assign locations to the product

        // Initialize the form instance to be used in test cases
        outboundRequisitionForm = new OutboundRequisitionForm();
    }
    
   
    @BeforeEach
    public void beforeTest1() {
        inboundRequisition = new InboundRequisition();
//        InboundRequisition.product = null;  // Reset static product data
        locationGrid = new Boolean[3][5];
        // Set all locations in the grid to available (true)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                locationGrid[i][j] = true;  
            }
        }
    }
 

    // 1. Test `confirmForm()`
    @Test
    public void testConfirmFormWithValidData() {
        outboundRequisitionForm.setProductName("Valid Product");
        outboundRequisitionForm.setProductCode("1234");
        outboundRequisitionForm.setDeliveryDepartment("Electronics");
        outboundRequisitionForm.setDepartmentInCharge("John Doe");

        String confirmFormString = outboundRequisitionForm.confirmForm();
        assertNotNull(confirmFormString);
        assertTrue(confirmFormString.contains("Product Name: Valid Product"));
        assertTrue(confirmFormString.contains("Delivery Department: Electronics"));
    }

    @Test
    public void testConfirmFormWithEmptyProductName() {
        outboundRequisitionForm.setProductName(""); // Empty product name
        outboundRequisitionForm.setProductCode("1234");
        outboundRequisitionForm.setDeliveryDepartment("Electronics");

        assertThrows(IllegalArgumentException.class, () -> {
            outboundRequisitionForm.confirmForm(); // Should throw IllegalArgumentException
        });
    }

    @Test
    public void testConfirmFormWithEmptyProductCode() {
        outboundRequisitionForm.setProductName("Valid Product");
        outboundRequisitionForm.setProductCode(""); // Empty product code
        outboundRequisitionForm.setDeliveryDepartment("Electronics");

        assertThrows(IllegalArgumentException.class, () -> {
            outboundRequisitionForm.confirmForm(); // Should throw IllegalArgumentException
        });
    }

    @Test
    public void testConfirmFormWithEmptyDeliveryDepartment() {
        outboundRequisitionForm.setProductName("Valid Product");
        outboundRequisitionForm.setProductCode("1234");
        outboundRequisitionForm.setDeliveryDepartment(""); // Empty delivery department

        assertThrows(IllegalArgumentException.class, () -> {
            outboundRequisitionForm.confirmForm(); // Should throw IllegalArgumentException
        });
    }

    // 2. Test `displayAvailableLocations()`
    @Test
    public void testDisplayAvailableLocationsWithValidProductCode() {
        InboundRequisition.product = product; // Set product to static variable in InboundRequisition
        outboundRequisitionForm.setProductName("Valid Product");
        outboundRequisitionForm.setProductCode("1234");
        outboundRequisitionForm.setDeliveryDepartment("Electronics");

        // Capture the output printed to System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));

        outboundRequisitionForm.displayAvailableLocations();

        String output = baos.toString();
        assertTrue(output.contains("Available Locations for product Code 1234:"));
        assertTrue(output.contains("Rack 1, Shelf 5"));
        assertTrue(output.contains("Rack 2, Shelf 6"));
    }

    @Test
    public void testDisplayAvailableLocationsWithNonMatchingProductCode() {
        InboundRequisition.product = product; // Set product to static variable in InboundRequisition
        outboundRequisitionForm.setProductName("Valid Product");
        outboundRequisitionForm.setProductCode("9999"); // Non-matching product code
        outboundRequisitionForm.setDeliveryDepartment("Electronics");

        assertThrows(IllegalArgumentException.class, () -> {
            outboundRequisitionForm.displayAvailableLocations(); // Should throw IllegalArgumentException
        });
    }

    @Test
    public void testDisplayAvailableLocationsWithEmptyLocations() {
        // Clear the product location list to simulate an empty list
        InboundRequisition.product.setLocationList(new ArrayList<>());
        outboundRequisitionForm.setProductName("Valid Product");
        outboundRequisitionForm.setProductCode("1234");
        outboundRequisitionForm.setDeliveryDepartment("Electronics");

        assertThrows(InternalError.class, () -> {
            outboundRequisitionForm.displayAvailableLocations(); // Should throw InternalError
        });
    }

    // 3. Test `toString()`
    @Test
    public void testToString() {
        outboundRequisitionForm.setProductName("Valid Product");
        outboundRequisitionForm.setProductCode("1234");
        outboundRequisitionForm.setDeliveryDepartment("Electronics");
        outboundRequisitionForm.setDepartmentInCharge("John Doe");

        String result = outboundRequisitionForm.toString();
        assertTrue(result.contains("Product Name: Valid Product"));
        assertTrue(result.contains("Product Code: 1234"));
        assertTrue(result.contains("Delivery Department: Electronics"));
        assertTrue(result.contains("Department In-charge: John Doe"));
    }

    // 4. Test `displayDetails()`
    @Test
    public void testDisplayDetailsWithValidInput() throws Exception {
        String userInput = "Valid Product\n1234\nElectronics\n"; // Simulate user input
        System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream

        // Capture the output printed to System.out
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos)); // Redirect output stream

        outboundRequisitionForm.displayDetails();

        String output = baos.toString();
        assertTrue(output.contains("Outbound Requisition Form"));
        assertTrue(output.contains("Product Name "));
        assertTrue(output.contains("Product Code "));
        assertTrue(output.contains("Delivery Department"));
        assertTrue(output.contains("Available Locations for product Code 1234:"));
        assertTrue(output.contains("Rack 1, Shelf 5"));
        assertTrue(output.contains("Rack 2, Shelf 6"));
        assertTrue(output.contains("Product Name: Valid Product"));
        assertTrue(output.contains("Product Code: 1234"));
        assertTrue(output.contains("Delivery Department: Electronics"));
    }

    @Test
    public void testDisplayDetailsWithEmptyProductName() throws Exception {
        // Simulate user input where the product name is empty
        String userInput = "\n1234\nElectronics\n"; // Empty product name
        System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream

        // Setup an empty location list or a mock product to avoid triggering
        // InternalError
        InboundRequisition.product = new Product("", "1234", "10", "11", "12", "13", "14", "15", "Sample Supplier",
                "Sample Supplier Location", "963852741", "12-12-2014", "14-12-2014");
        InboundRequisition.product.setLocationList(new ArrayList<>()); // Empty location list

        assertThrows(InternalError.class, () -> {
            outboundRequisitionForm.displayDetails(); // Should throw InternalError
        });
    }

    @Test
    public void testDisplayDetailsWithEmptyProductCode() throws Exception {
        String userInput = "Valid Product\n\nElectronics\n"; // Empty product code
        System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream

        assertThrows(IllegalArgumentException.class, () -> {
            outboundRequisitionForm.displayDetails(); // Should throw IllegalArgumentException
        });
    }

    @Test
    public void testDisplayDetailsWithEmptyDeliveryDepartment() throws Exception {
        String userInput = "Valid Product\n1234\n\n"; // Empty delivery department
        System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream

        assertThrows(NullPointerException.class, () -> {
            outboundRequisitionForm.displayDetails(); // Should throw IllegalArgumentException
        });
    }
	
	
	
    /**
     * Test inboundInputFun method with an invalid input.
     * Expects the method to return "Invalid Input" for input value 3.
     */
    @Test
    public void testInboundInputFunctionInvalidInput() {
        String result = inboundRequisition.inboundInputFunction(3);
        assertEquals("Invalid Input", result);
    }
 
    /**
     * Test the checkAvailability method when the specified location is available.
     * Expects the location at (1, 1) to be available (true).
     */
    @Test
    public void testCheckLocationAvailability() {
        assertTrue("Location should be available", InboundRequisition.checkAvailability(locationGrid, 1, 1));
    }
 
    /**
     * Test getProductLocations method when no product locations are set.
     * Expects an empty list as the result.
     */
    @Test
    public void testGetProductLocationsIsEmpty() {
    	InboundRequisition.product = null;
        List<Location> locations = InboundRequisition.getProductLocations();
        assertTrue("Expected an empty list when no product locations are set", locations.isEmpty());
    }
 
    /**
     * Test the checkAvailability method when the specified location is unavailable.
     * Marks location (0, 0) as unavailable (false) and checks if the method correctly
     * identifies it as unavailable.
     */
    @Test
    public void testCheckUnavailableLocations() {
        locationGrid[0][0] = false;
        assertFalse("Location should be unavailable", InboundRequisition.checkAvailability(locationGrid, 1, 1));
    }
 
    /**
     * Test confirmProduct method by passing a Product object.
     * Expects the method to return the confirmation message "CONFIRMED".
     */
    @Test
    public void testConfirmProduct() {
        Product product = new Product("TestProduct", "Code123", "Batch001", "10", "5", "5", "2", "50", "Supplier", "Location", "PO123", "2023-10-10", "2023-11-10");
        String result = InboundRequisition.confirmProduct(product);
        assertEquals("CONFIRMED", result);
    }
 
    /**
     * Test getProductLocations method when product locations are set.
     * Creates mock locations, assigns them to a product, and sets the product in InboundRequisition.
     * Expects the method to return a list with the same mock locations.
     */
    @Test
    public void testGetProductLocationsNotEmpty() {
        // Creating mock locations to test non-empty product locations
        List<Location> mockLocations = new ArrayList<>();
        mockLocations.add(new Location(1, 1));
        mockLocations.add(new Location(2, 3));
        // Creating a product and setting its locations
        Product product = new Product("TestProduct", "Code123", "Batch001", "10", "5", "5", "2", "50", "Supplier", "Location", "PO123", "2023-10-10", "2023-11-10");
        product.setLocationList(mockLocations);
        // Assign the product to InboundRequisition
        InboundRequisition.product= product;
 
        // Retrieve the locations and verify they match the expected mock locations
        List<Location> locations = InboundRequisition.getProductLocations();
        assertEquals("Expected 2 locations in the product locations list", 2, locations.size());
        assertEquals("Expected locations to match mock locations", mockLocations, locations);
    }
	
}
