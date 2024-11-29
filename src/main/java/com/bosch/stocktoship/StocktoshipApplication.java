package com.bosch.stocktoship;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.ItemCodeGeneration;
import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.entity.RequisitionItem;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.AccountManager;
import com.bosch.stocktoship.service.Accountant;
import com.bosch.stocktoship.service.ApplicationData;
import com.bosch.stocktoship.service.BOMMain;
import com.bosch.stocktoship.service.DeliveryDepartmentDAO;
import com.bosch.stocktoship.service.InboundRequisitionForm;
import com.bosch.stocktoship.service.InventoryRequisitionFormService;
import com.bosch.stocktoship.service.LocationAnalayzer;
import com.bosch.stocktoship.service.LocationDAO;
import com.bosch.stocktoship.service.MaterialRequisition;
import com.bosch.stocktoship.service.OutboundRequisitionForm;
import com.bosch.stocktoship.service.PurchaseOrder;
import com.bosch.stocktoship.service.PurchaseRequisition;
import com.bosch.stocktoship.service.QualityControl;
import com.bosch.stocktoship.service.StoreManager;

@SpringBootApplication
public class StocktoshipApplication {
	static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(StocktoshipApplication.class, args);

        ApplicationData app = new ApplicationData(); // Create an instance of the Application class
        app.start();


        // Main loop to display the menu after each task is completed
        while (true) {
            System.out.println("\nSTOCK-TO-SHIP");
            System.out.println("Enter your choice:");
            System.out.println("1. Ware-You-Go");
            System.out.println("2. Car-O-Drive");
            System.out.println("3. Stock Vault");
            System.out.println("4. Exit");

            // Read user choice for main menu
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Handle Ware-You-Go option (Inbound or Outbound Management)
                    System.out.println("Inbound and Outbound");
                    System.out.println("Enter your choice:");
                    System.out.println("1. Inbound Management");
                    System.out.println("2. OutBound Management");
                    int managementChoice = Integer.parseInt(scanner.nextLine());

                    if (managementChoice == 1) {
                        // Inbound Management
                    	inboundManagement();
                    } else if (managementChoice == 2) {
                        // Outbound Management
                    	outboundManagement();
                    } else {
                        System.out.println("Invalid Input");
                    }
                    break;

                case 2:
                    // Handle Car-O-Drive option
                    System.out.println("Car-O-Drive");

                    // Squad-4
                    QualityControl qualityControl = new QualityControl();
                    String partCode = null;
                    boolean exitStage = false;
                    while (!exitStage) {
                        System.out.println("\nSelect the stage:");
                        qualityControl.displayStages();
                        int stageChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (stageChoice > 0 && stageChoice <= qualityControl.getStages().size()) {
                            String selectedStage = qualityControl.getStages().get(stageChoice - 1);
                            qualityControl.setCurrentStage(selectedStage);

                            
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

                case 3:
                    // Handle Stock Vault option
                    System.out.println("Stock Vault");

                    // Squad-3
                    // Create a BOMMain instance to manage BOM processes
                    while(true) {
            			System.out.println("\n1. Item code \t2. Inventory Requisition \t3. BOM \t4. Exit");
            			int input = Integer.parseInt(scanner.nextLine());
            			BOMMain bomMain = new BOMMain();
            			if(input == 1) {
            				bomMain.generateItemCode(scanner);
            			}
            			
            			if(input == 2) {				
            				InventoryRequisitionFormService service = new InventoryRequisitionFormService();
            				service.manageIRF(scanner);
            			}
            			
            			if(input == 3) {
            				bomMain.collectBOMDetails(scanner);
            			}
            			
            			if(input == 4) {
            				break;
            			}
            		}

                    // Squad-5
                    // Enter PR details

                    PurchaseRequisition pr = null;

                    System.out.print("Item Code: ");
                    String itemCode = scanner.nextLine();

                    System.out.print("Purpose Description: ");
                    String purposeDescription = scanner.nextLine();

                    System.out.print("Quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    System.out.print("Unit (kg/gm/barrel/meter etc.): ");
                    String unit = scanner.nextLine();

                    System.out.print("Department (optional, leave blank for none): ");
                    String department = scanner.nextLine();

                    System.out.print("Company Make: ");
                    String companyMake = scanner.nextLine();

                    // Create Purchase Requisition with items
                    pr = new PurchaseRequisition(
                            new RequisitionItem(itemCode, purposeDescription, quantity, unit, department, companyMake));
                    // Display PR details
                    pr.displayPRDetails();

                    // Submit PR
                    String submitResponse;
                    do {
                        System.out.print("\nType SUBMIT to Submit the PR");
                        submitResponse = scanner.nextLine();
                        if (submitResponse.equalsIgnoreCase("submit")) {
                            pr.submitPR();
                            // Process approval and move items to departments
                            pr.processApproval();
                        } else {
                            System.out.println("PR not submitted.");
                        }

                    } while (!submitResponse.equalsIgnoreCase("submit"));

                    PurchaseOrder po = new PurchaseOrder();
                    MaterialRequisition mRequisition = new MaterialRequisition();

                    System.out.println("\n*****************Material requisition and Purchase Order Module*****************\n");

                    String prNo;

                    RequisitionItem item1 = new RequisitionItem();
                    System.out.println("Enter the PR number ");

                    while (true) {

                        prNo = scanner.nextLine();

                        if (PurchaseRequisition.prnMap.containsKey(prNo)) {

                            item1 = PurchaseRequisition.prnMap.get(prNo);

                            // checking for PR status
                            if (item1.approvalStatus.equalsIgnoreCase("Approved")) {

                                System.out.println("\nPR is approved. So, moving to Material requisition\n");

                                mRequisition.requestQuotations(item1);

                                List<Supplier> suppliers = new ArrayList<>();
                                suppliers = mRequisition.getSuppliers();

                                System.out.println("\nSelect a supplier from the list:");
                                for (int i = 0; i < suppliers.size(); i++) {
                                    System.out.println((i + 1) + ". " + suppliers.get(i).toString());
                                }
                                System.out.println("Enter the serial no of the supplier you choose: ");
                                int supplierChoice = scanner.nextInt();

                                Supplier supplier2 = mRequisition.selectSupplier(supplierChoice);

                                po.placePurchaseOrder(prNo, item1, supplier2);
                            } else {
                                System.out.println("PR is not approved yet...");
                            }
                            break;
                        } else {
                            System.out.println("Enter the correct PR number");
                            continue;
                        }
                    }

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
                    break;

                case 4:
                    // Exit the program
                    System.out.println("Exiting program.");
                    scanner.close();
                    return;

                default:
                    // Handle invalid choice
                    System.out.println("Invalid Input. Please try again.");
            }
        }
    }
    
 // Method for handling Inbound Management logic
 	public static void inboundManagement() throws Exception {

 		// Initialize DAO for location and insert location details
 		LocationDAO locationDAO = new LocationDAO();
 		locationDAO.insertLocationDetails();

 		// Display and process inbound requisition form
 		System.out.println("\nINBOUND REQUISITION FORM");
 		System.out.println("\nEnter product Code");
 		String productCode = scanner.nextLine();

 		// Initialize InboundRequisitionForm and fetch or create product details
 		Product product;
 		InboundRequisitionForm inboundRequisitionForm = new InboundRequisitionForm();
 		ResultSet resultSet = inboundRequisitionForm.getProductByCode(productCode);

 		// If product does not exist, create new product; otherwise, update existing product
 		if (!resultSet.next()) {
 			product = inboundRequisitionForm.inputNewProductDetails(productCode);
 			inboundRequisitionForm.insertNewOrder(product);
 		} else {
 			product = inboundRequisitionForm.updateProductDetails(resultSet);
 			inboundRequisitionForm.insertNewOrder(product);
 		}

 		// Initialize LocationAnalayzer to fetch available locations for product placement
 		LocationAnalayzer locationAnalayzer = new LocationAnalayzer();
 		List<Location> locationList = locationAnalayzer.fetchLocation();

 		// Calculate the total volume of the product to be stored
 		int quantity = product.getQuantity();
 		int volume = product.getVolume();
 		int totalVolume = quantity * volume;

 		// Loop to find appropriate locations for storing the product
 		do {
 			System.out.println("\nEnter the location you want to check");

 			// Validate location input
 			int locationInput;
 			while ((locationInput = Integer.parseInt(scanner.nextLine())) > 8 && (locationInput < 0)) {
 				System.out.println("Invalid Input, please input again");
 			}

 			// Update location with product details and check if available space
 			boolean available = locationAnalayzer.updateProductCodeInLocation(locationList, product, quantity,
 					locationInput);
 			if (available) {
 				// Reduce quantity and volume after storing the product in a location
 				quantity -= (10000 / volume);
 				totalVolume -= (10000 / volume) * volume;
 			}

 		} while (totalVolume > 0);

 		// Proceed to outbound management once inbound management is completed
 		outboundManagement();
 	}

 	// Method for handling Outbound Management logic
 	static void outboundManagement() throws SQLException {
 		// Initialize DAO for DeliveryDepartment and insert department details
 		DeliveryDepartmentDAO deliveryDepartmentDAO = new DeliveryDepartmentDAO();
 		deliveryDepartmentDAO.insertDeliveryDepartmentDetails();

 		// Initialize OutboundRequisitionForm to handle outbound requisitions
 		OutboundRequisitionForm outboundRequisitionForm = new OutboundRequisitionForm();

 		// Display outbound requisition form and request product details
 		System.out.println("\nOUTBOUND REQUISITION FORM");
 		System.out.println("\nEnter the Product name : ");
 		String productName = scanner.nextLine();
 		System.out.println("\nEnter the Product code : ");
 		String productCode = scanner.nextLine();

 		// Fetch available departments for delivery
 		List<String> departmentList = outboundRequisitionForm.deliveryDeparments();

 		// Display department options and allow user to choose a department
 		System.out.print("\nSelect Delivery Department from the following list \n(");
 		for (int i = 0; i < departmentList.size(); i++) {
 			String department = departmentList.get(i);
 			System.out.print(department);
 			if (i < departmentList.size() - 1) {
 				System.out.print(", ");
 			}
 		}
 		System.out.println(")");
 		String deliveryDepartmentName = scanner.nextLine();
 		System.out.println("Delivery Department : " + deliveryDepartmentName);

 		// Get the delivery in-charge based on selected department
 		outboundRequisitionForm.getDeliveryInchargeBasedOnDepartmentName(deliveryDepartmentName);

 		// Fetch available product locations for the given product code
 		List<Location> locations = outboundRequisitionForm.getAvailableLocations(productCode);

 		// Handle the case when the product is not available
 		if (locations.isEmpty()) {
 			System.out.println("Product not available");
 		} else {
 			// Display available locations and allow the user to select a location
 			System.out.println("\nProduct with product code: " + productCode + " available at these locations:");
 			System.out.format("%-5s %s\n", "LocationID", "Rack and Shelf Details");
 			for (Location location : locations) {
 				System.out.format("%-5s %s\n", location.getId(),
 						"Rack " + location.getRack() + ",Shelf " + location.getShelf());
 			}

 			// Validate and select the product location
 			System.out.println("\nSelect the product locationId");
 			int selectedLocationId;
 			while (!outboundRequisitionForm.validLocation(locations, selectedLocationId = scanner.nextInt())) {
 				System.out.println("Enter valid location");
 			}

 			// Update quantity and location details
 			outboundRequisitionForm.updateQuantityAndLocation(selectedLocationId);

 			// Assign robot for outbound delivery based on product weight
 			int weight = outboundRequisitionForm.getProductWeight(productCode);
 			Robot robot = outboundRequisitionForm.assignRobotBasedOnCapacity(weight);

 			// Display the robot details assigned for the outbound delivery
 			outboundRequisitionForm.displayRobotDetails(robot, productCode);

 			// Confirm product added to the cart
 			System.out.println("\nProduct Added to Cart");
 		}
 	}
}
