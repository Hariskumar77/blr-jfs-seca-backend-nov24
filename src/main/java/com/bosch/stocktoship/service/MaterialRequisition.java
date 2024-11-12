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
		
        suppliers.add(new Supplier("S001", "Ramesh","Company A", "Net 30", "Coimbatore","HDFC",123456789,"HDFC4007"));
        suppliers.add(new Supplier("S002", "Suresh","Company B", "Net 45", "Bangalore","Axis",123456888,"AXIS4008"));
        suppliers.add(new Supplier("S003", "Loki","Company C", "Net 20", "Coimbatore","HDFC",123456766,"HDFC5885"));

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
