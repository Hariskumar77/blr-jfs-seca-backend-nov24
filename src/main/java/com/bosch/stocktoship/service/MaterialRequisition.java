package com.bosch.stocktoship.service;

/**
 *  @author LDO1COB Soundarya 
 * 
 * */
import com.bosch.stocktoship.entity.*;
import java.util.ArrayList;
import java.util.List;



public class MaterialRequisition {

	private List<Supplier> suppliers;
	
	
	public MaterialRequisition() {
		suppliers = new ArrayList<>();
		
		// Creating Suppliers List
		
        suppliers.add(new Supplier("S001", "Supplier One", "City A", "Company A", "Net 30", "Bank A"));
        suppliers.add(new Supplier("S002", "Supplier Two", "City B", "Company B", "Net 45", "Bank B"));
        suppliers.add(new Supplier("S003", "Supplier Three", "City C", "Company C", "Net 30", "Bank C"));
        suppliers.add(new Supplier("S004", "Supplier Four", "City D", "Company D", "Net 60", "Bank D"));
        suppliers.add(new Supplier("S005", "Supplier Five", "City E", "Company E", "Net 15", "Bank E"));
	}
	
	// Getter Method for Suppliers
	public List<Supplier> getSuppliers() {
		return suppliers;
	}

//  Requesting Quotations
	public void requestQuotations(RequisitionItem item) {
		
		System.out.println("------------------------------------------\nSending item details to suppliers for quotation\n------------------------------------------");
		
		for(Supplier supplier : suppliers)
		{
			System.out.println("Sent a mail to "+ supplier.toString());
		}
		
		
		
		
	}
	
	// Selecting Supplier
	
	public Supplier selectSupplier(int choice) {
        
        return suppliers.get(choice - 1); 
    }
	
	
	
	
}
