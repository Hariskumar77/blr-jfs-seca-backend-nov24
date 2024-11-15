package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author LRB3KOR Rajesh
 * */



import java.sql.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.SupplierDAO;

public class SupplierDAOTest {

    private SupplierDAO supplierDAO;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        // Initialize the SupplierDAO instance before each test
        supplierDAO = new SupplierDAO();
        // Initialize DB connection (this can be your test DB setup or use a connection pool)
        // You can also insert test data here if needed.
    }


    @Test
    public void testGetAllSuppliers() throws SQLException, ClassNotFoundException {
        // Call the method to fetch all suppliers
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();

        // Check that the list is not empty and contains expected suppliers
        assertNotNull(suppliers);
      //  assertTrue(suppliers.size() > 0);
        /*"SELECT supplier_code, supplier_name, supplier_city, company_name, " +
        "payment_terms, supplier_bank_name, supplier_IFSCcode, supplier_AccNo " +
        "FROM supplier";*/
        // You can also add more assertions based on specific data
        Supplier supplier1 = suppliers.get(0);
        assertEquals("SUP001", supplier1.getSupplierCode());
        assertEquals("ABC Supplies", supplier1.getSupplierName());
        assertEquals("New York",supplier1.getSupplierCity());
        assertEquals("ABC Enterprises",supplier1.getCompanyName());
        assertEquals("Net 30",supplier1.getPaymentTerms());
        assertEquals("XYZ Bank",supplier1.getSupplierBankName());
        assertEquals("XYZB0001234",supplier1.getSupplierIFSCCode());
        assertNotNull(supplier1.getSupplierAccNo());
    }

    @Test
    public void testGetSupplierByCode() throws SQLException, ClassNotFoundException {
        // Test fetching a supplier by code
        String supplierCode = "SUP001";
        Supplier supplier = supplierDAO.getSupplierByCode(supplierCode);

        // Validate that the returned supplier is correct
        assertNotNull(supplier);
        assertEquals("SUP001", supplier.getSupplierCode());
        assertEquals("ABC Supplies", supplier.getSupplierName());
        
        // Test case where supplier does not exist
        Supplier nonExistentSupplier = supplierDAO.getSupplierByCode("S999");
        assertNull(nonExistentSupplier);  // Should return null if supplier doesn't exist
    }

    @Test
    public void testGetSupplierByCodeNotFound() throws SQLException, ClassNotFoundException {
        // Test case for non-existing supplier code
        Supplier supplier = supplierDAO.getSupplierByCode("S999");
        assertNull(supplier); // Should be null
    }
}
