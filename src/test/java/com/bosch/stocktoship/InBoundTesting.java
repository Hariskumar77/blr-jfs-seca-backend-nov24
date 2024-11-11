package com.bosch.stocktoship;
 
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bosch.stocktoship.service.InboundRequisition;
import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;

import static org.junit.Assert.*;
 
import java.util.ArrayList;
import java.util.List;

@SpringBootTest 
public class InBoundTesting {
    /**
     * AUTHOR: MUJAHID DAFEDAR(DAFB1KOR)
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
    @Before
    public void beforeTest() {
        inboundRequisition = new InboundRequisition();
        InboundRequisition.product = null;  // Reset static product data
        locationGrid = new Boolean[3][5];
        // Set all locations in the grid to available (true)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                locationGrid[i][j] = true;  
            }
        }
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