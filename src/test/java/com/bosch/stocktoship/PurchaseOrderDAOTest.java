package com.bosch.stocktoship;
/**
 * @author LRB3KOR Rajesh
 * */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.*;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.RequisitionItem;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.PurchaseOrderDAO;

public class PurchaseOrderDAOTest {

    private PurchaseOrderDAO purchaseOrderDAO;

    @BeforeEach
    public void setUp() throws SQLException, ClassNotFoundException {
        // Initialize PurchaseOrderDAO instance before each test
        purchaseOrderDAO = new PurchaseOrderDAO();

        // Optionally, prepare the database by adding test data or resetting the state.
    }

    // Test: Verify fetching purchase requisition details
    @Test
    public void testGetPurchaseRequisitionDetails() throws SQLException, ClassNotFoundException {
        // Test when PR number exists
    	RequisitionItem requisitionItem = purchaseOrderDAO.getPurchaseRequisitionDetails(10031);
        
        assertNotNull(requisitionItem);
        assertEquals(10031, requisitionItem.getPr_number());
        assertEquals("IT001", requisitionItem.getItemCode());
        assertEquals("Crafting", requisitionItem.getPurposeDescription());
        assertEquals(20,requisitionItem.getQuantity());
        assertEquals("kg",requisitionItem.getUnit());
        assertEquals("Wood",requisitionItem.getDepartment());
        assertEquals("Joshi Woods",requisitionItem.getCompanyMake());
        assertEquals("Approved",requisitionItem.getApprovalStatus());
        
    }
    

    // Test: Verify that null is returned if PR number does not exist
    @Test
    public void testGetPurchaseRequisitionDetailsNotFound() throws SQLException, ClassNotFoundException {
        // Test when PR number does not exist
    	RequisitionItem requisitionItem = purchaseOrderDAO.getPurchaseRequisitionDetails(9999);

        assertNull(requisitionItem);  // Should return null if no requisition found
    }

    // Test: Verify fetching supplier details by supplier code
    @Test
    public void testGetSupplierDetails() throws SQLException, ClassNotFoundException {
        Supplier supplier = purchaseOrderDAO.getSupplierDetails("SUP001");

        assertNotNull(supplier);
        assertEquals("SUP001", supplier.getSupplierCode());
        assertEquals("ABC Supplies", supplier.getSupplierName());
        assertEquals("New York", supplier.getSupplierCity());
    }

    // Test: Verify that null is returned if supplier code does not exist
    @Test
    public void testGetSupplierDetailsNotFound() throws SQLException, ClassNotFoundException {
        Supplier supplier = purchaseOrderDAO.getSupplierDetails("S999");

        assertNull(supplier);  // Should return null if supplier code does not exist
    }

    // Test: Verify fetching all supplier codes
    @Test
    public void testGetSupplierCodes() throws SQLException, ClassNotFoundException {
        List<String> supplierCodes = purchaseOrderDAO.getSupplierCodes();

        assertNotNull(supplierCodes);
     
    }

    // Test: Verify getting the next purchase order number
    @Test
    public void testGetNextPurchaseOrderNumber() throws SQLException, ClassNotFoundException {
        int nextPoNumber = purchaseOrderDAO.getNextPurchaseOrderNumber();
        assertNotNull(nextPoNumber);

    }

    // Test: Verify inserting a purchase order
    @Test
    public void testInsertPurchaseOrder() throws SQLException, ClassNotFoundException {
        int purchaseOrderNumber = 1008;
        String purchaseReqNo = "10031";
        String supplierCode = "SUP001";

        boolean isInserted = purchaseOrderDAO.insertPurchaseOrder(purchaseOrderNumber, purchaseReqNo, supplierCode);

        assertTrue(isInserted);  // Should return true if insertion is successful
    }

}
