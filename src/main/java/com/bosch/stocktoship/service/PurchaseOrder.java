package com.bosch.stocktoship.service;

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
	 
	 public void placePurchaseOrder(String purchaseReqNo,RequisitionItem item, Supplier supplier) {
		 
		 
		   System.out.println("\nPurchase Order Details:\n----------------------------------");
	       
		    System.out.println("PR Number: " + purchaseReqNo);
	        System.out.println("Item Code: " + item.getItemCode());
	        System.out.println("Item Description: " + item.getPurposeDescription());
	        System.out.println("Quantity: " + item.getQuantity());
	        System.out.println("Unit: " + item.getUnit());
	        System.out.println("Company Make: " + item.getCompanyMake());
	        System.out.println("Supplier Code: " + supplier.getSupplierCode());
	        System.out.println("Supplier Name: " + supplier.getSupplierName());
	        System.out.println("Company Name: " + supplier.getCompanyName());
	        System.out.println("Payment Terms: " + supplier.getPaymentTerms());
	        System.out.println("Bank Details: " + supplier.getBankDetails());
	        System.out.println("---------------------------\n");
		    
		    
		    System.out.println("\n------------------------------------\nPurchase Order Submitted successfully\n------------------------------------");
		    
		    System.out.println("\nPurchase order number : "+ purchaseOrderNumber++);
	 }
		    
	    
}

