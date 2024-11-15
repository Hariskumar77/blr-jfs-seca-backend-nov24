package com.bosch.stocktoship;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.service.DeliveryDepartmentDAO;
import com.bosch.stocktoship.service.InboundRequisitionForm;
import com.bosch.stocktoship.service.LocationAnalayzer;
import com.bosch.stocktoship.service.LocationDAO;
import com.bosch.stocktoship.service.OutboundRequisitionForm;
import com.bosch.stocktoship.service.RobotDAO;

@SpringBootApplication
public class StocktoshipApplication {
	static Scanner scanner = new Scanner(System.in);

	// Main method for running the Spring Boot application
	public static void main(String[] args) throws Exception {
		// Run the Spring Boot application
		SpringApplication.run(StocktoshipApplication.class, args);

		// Display the main menu options
		System.out.println("STOCK-TO-SHIP");
		System.out.println("\nEnter your choice");
		System.out.println("1. Ware-You-Go");
		System.out.println("2. Car-O-Drive");
		System.out.println("3. Stock Vault");

		// Initialize RobotDAO and insert predefined robot details into the database
		RobotDAO robotDAO = new RobotDAO();
		robotDAO.insertRobotDetails();

		// Read user input for main menu choice
		int choice = Integer.parseInt(scanner.nextLine());

		// Switch statement for handling different main menu options
		switch (choice) {
		case 1:
			// Inbound/Outbound Management section
			System.out.println("\nEnter your choice");
			System.out.println("1. Inbound Management");
			System.out.println("2. Outbound Management");
			int managementChoice = Integer.parseInt(scanner.nextLine());
			if (managementChoice == 1) {
				// Call method to handle inbound management
				inboundManagement();
			} else if (managementChoice == 2) {
				// Call method to handle outbound management
				outboundManagement();
			} else {
				// Invalid input handling
				System.out.println("Invalid Input");
			}
			break;

		case 2:
			// Placeholder for "Car-O-Drive" functionality
			System.out.println("Car-O-Drive");
			break;

		case 3:
			// Placeholder for "Stock Vault" functionality
			System.out.println("Stock Vault");
			break;

		default:
			// Invalid choice handling
			System.out.println("Invalid Input");
			break;
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
