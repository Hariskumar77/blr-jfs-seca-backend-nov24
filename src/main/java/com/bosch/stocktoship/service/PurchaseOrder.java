package com.bosch.stocktoship.service;

import java.sql.SQLException;

import com.bosch.stocktoship.entity.*;

/**
 *  @author LDO1COB Soundarya
 * 
 * */

public class PurchaseOrder {

	  public static int purchaseOrderNumber= 1;
	
	  public PurchaseOrder() {
		
	  } 
		
	// Printing Purchase Order Details
	 public void displaySupplier(String purchaseReqNo,RequisitionItem item, Supplier supplier) {
		 
		 
		   System.out.println("\nPurchase Order Details:\n----------------------------------");
	       
		    System.out.println("PR Number: " + purchaseReqNo);
	        System.out.println("Supplier Code: " + supplier.getSupplierId());
	        System.out.println("Supplier Name: " + supplier.getSupplierName());
	        System.out.println("Company Name: " + supplier.getComapanyName());
	        System.out.println("Payment Terms: " + supplier.getPaymentTerms());
	        System.out.println("Bank Name: " + supplier.getBankName());
	        System.out.println("IFSC Code: " + supplier.getIfscCode());

	 }
	
	 private PurchaseOrderDAO purchaseOrderDAO = new PurchaseOrderDAO();

	    // Handle purchase order creation
	 public void placePurchaseOrder(String purchaseReqNo, Supplier supplier) throws ClassNotFoundException {
		    try {
		        // Step 1: Get the next Purchase Order number from the database
		        int purchaseOrderNumber = purchaseOrderDAO.getNextPurchaseOrderNumber();
		        if (purchaseOrderNumber == -1) {
		            System.out.println("Error generating purchase order number.");
		            return;
		        }

		        // Step 2: Insert the Purchase Order into the database (only saving supplier code)
		        boolean isInserted = purchaseOrderDAO.insertPurchaseOrder(purchaseOrderNumber, purchaseReqNo, supplier.getSupplierId());

		        if (isInserted) {
		            System.out.println("Purchase Order successfully placed!");
		            System.out.println("Purchase Order Number: " + purchaseOrderNumber); // Print after successful insert
		        } else {
		            System.out.println("Failed to place the Purchase Order.");
		        }
		    } catch (SQLException e) {
		        System.err.println("Error: " + e.getMessage());
		    }
		}
		    
	    
}