package com.bosch.stocktoship;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.*;


@SpringBootApplication
public class StocktoshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocktoshipApplication.class, args);
		
		ApplicationData  app = new ApplicationData(); // Create an instance of the Application class
	        app.start();
	        
	        
	        Supplier supplier = new Supplier();
	        supplier.callInvoice();

	        // Store Manager operations: display invoice and check for approval
	        StoreManager storeManager = new StoreManager();
	        storeManager.displayInvoice();          // Display details of the selected invoice
	        storeManager.checkForApproval();        // Check for approval of the invoice

	        // Accountant operations: enter payment details and process payment if approved
	        Accountant accountant = new Accountant();
	        accountant.enterPaymentDetails(supplier); // Accountant enters payment details for the supplier
	        
	        // If the invoice is approved, proceed with payment
	        if (Invoice.invoiceStatus == true) {
	            accountant.pay(supplier);
	        }

	        // Account Manager operations: view payment details for the supplier
	        AccountManager accountManager = new AccountManager();
	        accountManager.viewPaymentDetails(supplier); // Account Manager views the payment details
		
	}

}
