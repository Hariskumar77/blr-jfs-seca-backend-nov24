package com.bosch.stocktoship;

/**
 * 
 * @author Supriya Khemalapure - QEU3KOR
 * 
 * **/



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.bosch.stocktoship.service.*;
import com.bosch.stocktoship.entity.*;

@SpringBootTest
public class RequisitionAndPurchaseRequisitionTest {
 
    private RequisitionItem item;
    private PurchaseRequisition pr;
 
    // Set up before each test
    @BeforeEach
    public void setUp() {
        item = new RequisitionItem("ABC123", "Office Chair", 30, "Piece", "HR", "ABC Corp");
        pr = new PurchaseRequisition(item);  // Create a PurchaseRequisition with an item
    }
 
    // RequisitionItem Tests
 
    @Test
    public void testRequisitionItemConstructorAndInitialization() {
        assertEquals("ABC123", item.getItemCode());
        assertEquals("Office Chair", item.getPurposeDescription());
        assertEquals(30, item.getQuantity());
        assertEquals("Piece", item.getUnit());
        assertEquals("HR", item.getDepartment());
        assertEquals("ABC Corp", item.getCompanyMake());
        assertEquals("Approved", item.getApprovalStatus()); // Based on the quantity <= 50
    }
 
    @Test
    public void testRequisitionItemSettersAndGetters() {
        item.setItemCode("DEF456");
        item.setPurposeDescription("Monitor");
        item.setQuantity(40);
        item.setUnit("Piece");
        item.setDepartment("IT");
        item.setCompanyMake("DEF Corp");
        assertEquals("DEF456", item.getItemCode());
        assertEquals("Monitor", item.getPurposeDescription());
        assertEquals(40, item.getQuantity());
        assertEquals("Piece", item.getUnit());
        assertEquals("IT", item.getDepartment());
        assertEquals("DEF Corp", item.getCompanyMake());
    }
 
    @Test
    public void testRequisitionItemUpdateApprovalStatus() {
        item.setQuantity(60); // This should trigger "Need Approval" because quantity > 50
        item.updateApprovalStatus();
        assertEquals("Need Approval", item.getApprovalStatus());
 
        item.setQuantity(40); // This should trigger "Approved" because quantity <= 50
        item.updateApprovalStatus();
        assertEquals("Approved", item.getApprovalStatus());
    }
 
    @Test
    public void testRequisitionItemToString() {
        String expectedString = "Item Code: ABC123, Purpose: Office Chair, Quantity: 30Piece, Department: HR, Company Make: ABC Corp, Approval Status: Approved";
        assertEquals(expectedString, item.toString());
    }
 
    @Test
    public void testRequisitionItemDefaultConstructor() {
        RequisitionItem defaultItem = new RequisitionItem();
        assertNull(defaultItem.getItemCode());
        assertNull(defaultItem.getPurposeDescription());
        assertEquals(0, defaultItem.getQuantity());
        assertNull(defaultItem.getUnit());
        assertNull(defaultItem.getDepartment());
        assertNull(defaultItem.getCompanyMake());
        assertEquals("Need Approval", defaultItem.getApprovalStatus());  // Default approval status
    }
 
    // PurchaseRequisition Tests
 
    @Test
    public void testPurchaseRequisitionConstructor() {
        assertNotNull(pr.getPRNumber());  // Check PR number is not null
        assertTrue(PurchaseRequisition.prnMap.containsKey(pr.getPRNumber()));  // Ensure the PR is in the map
        assertEquals(item, PurchaseRequisition.prnMap.get(pr.getPRNumber()));  // Ensure the correct item is added
    }
 
    @Test
    public void testPurchaseRequisitionSubmitPR() {
        pr.submitPR(null);  // Submit the PR
        assertTrue(pr.isSubmitted());  // Ensure PR is marked as submitted
 
        pr.submitPR(null);  // Try submitting again
        assertTrue(pr.isSubmitted()); 
    }
 
    @Test
    public void testPurchaseRequisitionProcessApproval() {
        pr.processApproval(null);  // Process approval for approved item
        // Check correct approval message for approved item
        assertEquals("Approved", PurchaseRequisition.prnMap.get(pr.getPRNumber()).getApprovalStatus());
    }
 
    @Test
    public void testPurchaseRequisitionProcessApprovalWithNeedApproval() {
        item.setQuantity(60);  // Set quantity to a value that needs approval
        item.updateApprovalStatus();  // Update approval status based on quantity
        pr.processApproval(null);  // Process approval for item
        assertEquals("Need Approval", PurchaseRequisition.prnMap.get(pr.getPRNumber()).getApprovalStatus());  // Ensure correct message for items requiring approval
    }
 
    @Test
    public void testPurchaseRequisitionPRNumberGeneration() {
        PurchaseRequisition pr2 = new PurchaseRequisition(item);  // Create another PR
        assertNotEquals(pr.getPRNumber(), pr2.getPRNumber());  // Ensure different PR numbers are generated
    }
 
    @Test
    public void testPurchaseRequisitionPRNumberIncrement() {
        int previousPRNumber = Integer.parseInt(pr.getPRNumber());
        PurchaseRequisition pr2 = new PurchaseRequisition(item);  // Create another PR
        int currentPRNumber = Integer.parseInt(pr2.getPRNumber());
        assertEquals(previousPRNumber + 1, currentPRNumber);  // Check if PR number is incremented by 1
    }
 
    @Test
    public void testPurchaseRequisitionSubmitMultiplePRs() {
        RequisitionItem item2 = new RequisitionItem("XYZ456", "Monitor", 40, "Piece", "IT", "XYZ Corp");
        PurchaseRequisition pr2 = new PurchaseRequisition(item2);
 
        pr.submitPR(null);  // Submit the first PR
        pr2.submitPR(null);  // Submit the second PR
 
        assertTrue(pr.isSubmitted());  // Ensure both PRs are marked as submitted
        assertTrue(pr2.isSubmitted());
 
        // Check that both PR submission messages were printed
        pr.submitPR(null);
        pr2.submitPR(null);

        assertTrue(pr.isSubmitted());
        assertTrue(pr2.isSubmitted());// Verify both submissions were printed
    }
}
