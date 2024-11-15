package com.bosch.stocktoship;
//Anushua Roy -- 11/14/2024 --SQUAD1

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;



import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.SupplierDAO;
@SpringBootTest
public class SupplierDAOTest {

private SupplierDAO supplierDAO;
private Supplier supplier;

// This setup method runs before each test
@BeforeEach
public void setUp() {
    // Initialize SupplierDAO and create a sample Supplier object
    supplierDAO = new SupplierDAO();
    supplier = new Supplier("SD01", "John Doe", "XYZ Corp", "Net 30", "New York", "HDFC", 1234567890L, "HDFC1234");

    // Clear the database before each test (optional, depends on your testing strategy)
    clearDatabase();
}

// This tearDown method runs after each test
@AfterEach
public void tearDown() {
    // Clean up any data created during tests
    clearDatabase();
}

// Helper method to clear the database
private void clearDatabase() {
    supplierDAO.deleteSupplier("SD01");
    supplierDAO.deleteSupplier("SD02");
}

// Create a supplier and check if it's successfully inserted
@Test
public void testCreateSupplier() {
    boolean result = supplierDAO.createSupplier(supplier);
    assertTrue("Supplier should be created successfully", result);

    // Verify by retrieving the supplier from the database
    Supplier fetchedSupplier = supplierDAO.getSupplierById("SD01");
    assertNotNull("Supplier should be retrieved from the database", fetchedSupplier);
    assertEquals("Supplier name should match", "John Doe", fetchedSupplier.getSupplierName());
    assertEquals("Supplier company name should match", "XYZ Corp", fetchedSupplier.getComapanyName());
}

// Insert the supplier
@Test
public void testGetSupplierById() {
    supplierDAO.createSupplier(supplier);

    // Retrieve the supplier by ID
    Supplier fetchedSupplier = supplierDAO.getSupplierById("SD01");
    assertNotNull("Supplier should be retrieved from the database", fetchedSupplier);
    assertEquals("Supplier ID should match", "SD01", fetchedSupplier.getSupplierId());
    assertEquals("Supplier name should match", "John Doe", fetchedSupplier.getSupplierName());
}

// Add the supplier
@Test
public void testGetAllSuppliers() {
    supplierDAO.createSupplier(supplier);

    // Retrieve all suppliers and check if the supplier is included
    List<Supplier> suppliers = supplierDAO.getAllSuppliers();
    assertTrue("Suppliers list should not be empty", suppliers.size() > 0);
}

// Insert the supplier
@Test
public void testUpdateSupplier() {
    supplierDAO.createSupplier(supplier);

    // Update the supplier details using setter methods
    supplier.setSupplierName("John Updated");
    supplier.setCity("Los Angeles");
    boolean result = supplierDAO.updateSupplier(supplier, "SD01");

    // Verify if the update was successful
    assertTrue("Supplier should be updated successfully", result);

    // Retrieve and verify updated supplier details
    Supplier updatedSupplier = supplierDAO.getSupplierById("SD01");
    assertEquals("Updated supplier name should match", "John Updated", updatedSupplier.getSupplierName());
    assertEquals("Updated city should match", "Los Angeles", updatedSupplier.getCity());
}

// Insert the supplier
@Test
public void testDeleteSupplier() {
    supplierDAO.createSupplier(supplier);

    // Now, delete the supplier
    boolean result = supplierDAO.deleteSupplier("SD01");

    // Verify if the supplier was deleted
    assertTrue("Supplier should be deleted successfully", result);

    // Try to retrieve the deleted supplier
    Supplier deletedSupplier = supplierDAO.getSupplierById("SD01");
    assertNull("Deleted supplier should not be found", deletedSupplier);
}

// Try to delete a non-existent supplier
@Test
public void testDeleteNonExistentSupplier() {
    boolean result = supplierDAO.deleteSupplier("NON_EXISTENT_ID");
    assertFalse("Deleting non-existent supplier should return false", result);
}

// Try to retrieve a non-existent supplier
@Test
public void testGetSupplierByIdNotFound() {
    Supplier fetchedSupplier = supplierDAO.getSupplierById("NON_EXISTENT_ID");
    assertNull("Supplier should not be found", fetchedSupplier);
}

// Create a supplier with null fields and check if it's handled properly
@Test
public void testCreateSupplierWithNullValues() {
    Supplier supplierWithNulls = new Supplier("SD02", null, null, null, null, null, 0L, null);
    boolean result = supplierDAO.createSupplier(supplierWithNulls);
    assertTrue("Supplier with null values should be created", result);

    // Retrieve and verify supplier
    Supplier fetchedSupplier = supplierDAO.getSupplierById("SD02");
    assertNotNull("Supplier should be retrieved from the database", fetchedSupplier);
    assertNull("Supplier name should be null", fetchedSupplier.getSupplierName());
}

// Ensure that the database is clean before starting the test
@Test
public void testGetAllSuppliersEmpty() {
    supplierDAO.deleteSupplier("SD12");
    supplierDAO.deleteSupplier("SD69");

    // Ensure no suppliers exist initially
    List<Supplier> suppliers = supplierDAO.getAllSuppliers();
    assertTrue("Suppliers list should be empty", suppliers.isEmpty());
}

}