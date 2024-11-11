package com.bosch.stocktoship;

import java.util.Scanner;

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
import com.bosch.stocktoship.service.QualityControl;
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
		//Squad-3
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
		//Squad-4
		Scanner scanner = new Scanner(System.in);
		QualityControl qualityControl = new QualityControl();
		String partCode = null;
		System.out.println("Quality Control");
		while (true) {
			System.out.println("\nSelect the stage:");
			qualityControl.displayStages();
			int stageChoice = scanner.nextInt();
			scanner.nextLine();

			if (stageChoice > 0 && stageChoice <= qualityControl.getStages().size()) {
				String selectedStage = qualityControl.getStages().get(stageChoice - 1);
				qualityControl.setCurrentStage(selectedStage);

				boolean exitStage = false;
				while (!exitStage) {

					System.out.println("\nOptions: 1. Enter Data 2. Edit Data 3. Save 4. Submit 5. Exit Stage");
					System.out.print("Choose an option: ");

					int option = scanner.nextInt();
					switch (option) {
					case 1:
						System.out.print("Enter the sample size: ");
						int sampleSize = scanner.nextInt();
						scanner.nextLine();

						System.out.print("Enter/Scan the part code: ");
						partCode = scanner.nextLine();

						qualityControl.collectData(sampleSize, partCode, scanner);
						break;

					case 2:
						qualityControl.editData(scanner);
						break;

					case 3:
						qualityControl.saveData();
						break;

					case 4:
						qualityControl.submitData(stageChoice, partCode);
						break;

					case 5:
						exitStage = true;
						break;

					default:
						System.out.println("Invalid option. Please try again.");
					}
				}
			} else {
				System.out.println("Invalid stage selection. Try again.");
			}
		}
	}

}
