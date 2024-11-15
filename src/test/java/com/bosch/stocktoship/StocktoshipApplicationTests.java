package com.bosch.stocktoship;

/**
 * 
 * @author Shubham Singh Tomer - (NGI2COB)
 * 
 * **/

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.bosch.stocktoship.service.*;
import com.bosch.stocktoship.entity.*;

@SpringBootTest
class StocktoshipApplicationTests {

	private PurchaseRequisition pr;
    private MaterialRequisition mRequisition;
    private PurchaseOrder po;

    @BeforeEach
    public void setup() {
        pr = new PurchaseRequisition(new RequisitionItem("Item001", "Test Purpose", 10, "kg", "", "Company A"));
        mRequisition = new MaterialRequisition();
        po = new PurchaseOrder();
    }

    // Test 1: Test that a Purchase Requisition can be submitted and approved
    @Test
    public void testSubmitAndApprovePurchaseRequisition() {
        // Test the PR submission
        assertNotNull(pr);
        pr.submitPR(null);
        assertTrue(pr.isSubmitted());

        // Test the approval process
        pr.processApproval(null);
        assertEquals("Approved", PurchaseRequisition.prnMap.get(pr.getPRNumber()).getApprovalStatus());
    }

    // Test 2: Test Material Requisition can request quotations from suppliers
    @Test
    public void testRequestQuotations() {
        // Arrange: Mock the system to send requests for quotations
        mRequisition.requestQuotations(pr.getItem());
        
        //If the suppliers list is populated, which ensures requests are being sent.
        List<Supplier> suppliers = mRequisition.getSuppliers();
        assertFalse(suppliers.isEmpty());
    }

 // Test 3: Test selecting a supplier from the list
    @Test
    public void testSelectSupplier() {
        // Directly set the supplier index to simulate user input
        int selectedSupplierIndex = 2; // Simulate user selecting the second supplier

        // Get the selected supplier from the list
        Supplier selectedSupplier = mRequisition.getSuppliers().get(selectedSupplierIndex - 1);
        
        // Ensure that the selected supplier is not null
        assertNotNull(selectedSupplier);
        
        // Check that the selected supplier's name matches the expected supplier
        assertEquals("Supplier Two", selectedSupplier.getSupplierName());
    }


    // Test 4: Test placing a Purchase Order
    @Test
    public void testPlacePurchaseOrder() {
    	// Selecting the first supplier
        Supplier supplier = mRequisition.getSuppliers().get(0); 

        // Place the purchase order
        po.placePurchaseOrder("10001", pr.getItem(), supplier);
// 		Ensure that the PurchaseOrder object is initialized.
        assertNotNull(po);  
    }

	
	
	
	
	
}
