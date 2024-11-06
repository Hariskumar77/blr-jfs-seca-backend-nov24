package com.bosch.stocktoship.entity;




import java.util.*;

public class InboundRequisition {

	/**

	 * AUTHOR: AYUSH ARYA(YYA2COB)

	 */

	static Scanner sc = new Scanner(System.in);

	public static Product product;  // Holds product details for inbound requisition

	// Main method to manage inbound requisitions

	public void inboundMgmt() {

		System.out.println("Inbound Management");

		System.out.println("Enter your choice");

		System.out.println("1. Inbound Requisition Form");

		System.out.println("2. Location Analyzer");

		int choice = Integer.parseInt(sc.nextLine());

		if (choice == 1) {

			InboundRequisitionForm();

		} else if (choice == 2) {

			LocationAnalyzer();

		} else {

			System.out.println(inboundInputFunction(choice));

		}

	}

	// Returns an error message for invalid input

	public String inboundInputFunction(int choice) {

		return "Invalid Input";

	}

	// Method to handle the inbound requisition form inputs

	public static void InboundRequisitionForm() {

		System.out.println("\nProduct Name");

		String name = sc.nextLine();

		System.out.println("\nProduct Code");

		String code = sc.nextLine();

		System.out.println("\nBatch No.");

		String batch = sc.nextLine();

		System.out.println("\nProduct Dimensions");

		System.out.println("Length:");

		String length = sc.nextLine();

		System.out.println("Breadth");

		String breadth = sc.nextLine();

		System.out.println("Height");

		String height = sc.nextLine();

		System.out.println("\nProduct Weight:");

		String weight = sc.nextLine();

		System.out.println("\nProduct Qty.");

		String quantity = sc.nextLine();

		System.out.println("\nSupplier Name");

		String supplierName = sc.nextLine();

		System.out.println("\nSupplier Location");

		String location = sc.nextLine();

		System.out.println("\nPurchase Order no.");

		String orderNo = sc.nextLine();

		System.out.println("\nDate of PO Insurance");

		String purchaseOrderDate = sc.nextLine();

		System.out.println("\nDate of Delivery");

		String deliveryDate = sc.nextLine();

		// Creates a Product object with provided data

		product = new Product(name, code, batch, length, breadth, height, weight, quantity, supplierName, location, orderNo, purchaseOrderDate, deliveryDate);

		System.out.println(confirmProduct(product));

		LocationAnalyzer();

	}

	// Confirms product creation for display

	public static String confirmProduct(Product product) {

		return "CONFIRMED";

	}

	// Method to analyze and allocate location for products in storage

	public static void LocationAnalyzer() {

		Boolean shelves[][] = new Boolean[3][5];  // 3x5 storage structure for products

		for (Boolean row[] : shelves)

			Arrays.fill(row, true);  // Initialize all locations as available

		System.out.format("%-5s %s\n", "S no.", "Location");

		List<Location> list = new ArrayList<>();  // All possible locations

		List<Location> productList = new ArrayList<>();  // Product-specific locations

		int sNo = 1;

		// Generate random, unique locations

		while (list.size() <= 5) {

			Random r = new Random();

			int rack = r.nextInt(3) + 1;

			int shelf = r.nextInt(5) + 1;

			Location loc = new Location(rack, shelf);

			if (!list.isEmpty()) {

				boolean flag = false;

				for (Location loc1 : list) {

					if (loc1.getRack() == rack && loc1.getShelf() == shelf) {

						flag = true;

						break;

					}

				}

				if (!flag) {

					list.add(loc);

					System.out.format("%-5s %s\n", sNo++, "Rack " + rack + ",Shelf " + shelf);

				}

			} else {

				list.add(loc);

			}

		}

		// User checks available locations for storage

		do {

			System.out.println("\nEnter the location you want to check?");

			int input = Integer.parseInt(sc.nextLine());

			if (input > list.size()) {

				System.out.println("Invalid Input");

			} else {

				Location loc = list.get(input);

				int rack = loc.getRack();

				int shelf = loc.getShelf();

				if (checkAvailability(shelves, rack, shelf)) {

					productList.add(loc);  // Block the chosen location

					System.out.println("Rack " + rack + ",Shelf " + shelf + " blocked");

					Robot robot = new Robot(product.getCode());  // Robot handles product allocation

					robot.display();

					shelves[rack - 1][shelf - 1] = false;  // Mark location as occupied

				} else {

					System.out.println("Place Unavailable");

				}

			}

			System.out.println("Do you want to check more locations?(y/n) \n");

		} while (sc.nextLine().equalsIgnoreCase("y"));

		product.setLocationList(productList);  // Update product's location list

		OutboundRequisitionForm obj = new OutboundRequisitionForm();

		obj.displayDetails();

	}

	// Checks if a specific rack and shelf are available

	public static boolean checkAvailability(Boolean[][] shelves, int rack, int shelf) {

		return shelves[rack-1][shelf-1];

	}

	// Retrieves product's assigned locations

	public static List<Location> getProductLocations() {

		return (product != null) ? product.getLocationList() : new ArrayList<>();

	}

}
 