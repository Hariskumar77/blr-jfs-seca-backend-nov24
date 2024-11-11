package com.bosch.stocktoship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.ItemCodeGeneration;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.AccountManager;
import com.bosch.stocktoship.service.Accountant;
import com.bosch.stocktoship.service.ApplicationData;
import com.bosch.stocktoship.service.BOMMain;
import com.bosch.stocktoship.service.InventoryRequisitionFormService;
import com.bosch.stocktoship.service.StoreManager;

@SpringBootApplication
public class StocktoshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocktoshipApplication.class, args);

		ApplicationData app = new ApplicationData(); // Create an instance of the Application class
		app.start();

		Supplier supplier = new Supplier();
		supplier.callInvoice();

		// Store Manager operations: display invoice and check for approval
		StoreManager storeManager = new StoreManager();
		storeManager.displayInvoice(); // Display details of the selected invoice
		storeManager.checkForApproval(); // Check for approval of the invoice

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

		// Create a BOMMain instance to manage BOM processes
		BOMMain bomMain = new BOMMain();

		try {
			// Collect general item details and store them in an ItemCodeGeneration object
			ItemCodeGeneration item = bomMain.collectItemDetails();

			// Collect specific BOM details (dimensions, quantity, etc.) for the item
			bomMain.collectBOMDetails(item);

			// Display the collected BOM details in a formatted table
			bomMain.displayBOMDetails(item);

		} catch (Exception e) {
			// Print any error that occurs during BOM collection or display
			System.out.println("An unexpected error occurred: " + e.getMessage());
		}
		InventoryRequisitionFormService service = new InventoryRequisitionFormService();
		service.manageIRF();
	}

}
