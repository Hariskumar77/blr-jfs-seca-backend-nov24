package com.bosch.stocktoship.service;

import java.sql.SQLException;
//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bosch.stocktoship.entity.GenerateBOM;
import com.bosch.stocktoship.entity.ItemCodeGeneration;
import com.bosch.stocktoship.exception.InvalidIntegerException;
import com.bosch.stocktoship.exception.InvalidItemCodeException;

public class BOMMain {
	BOMRepositoryService bomRepositoryService = new BOMRepositoryService();

	/**
	 * Methods takes the input from the user and generates ItemCode for valid inputs
	 * 
	 * @param input
	 * @throws InvalidItemCodeException
	 * @throws SQLException
	 */
	public void generateItemCode(Scanner input) throws InvalidItemCodeException, SQLException {

		String itemUniqueCode = validateItemUniqueCode(input);
		String category = getMandatoryInputWithRetry("Category", input);
		String dimensions = validateDimensionsInput(input);
		String unitsOfMeasure = validateUnitsOfMeasure(input);
		String itemDescription = getMandatoryInputWithRetry("Item Description", input);
		String manufacturer = getMandatoryInputWithRetry("Manufacturer", input);
		String countryOfOrigin = getMandatoryInputWithRetry("Country of Origin", input);
		ItemCodeGeneration itemCodeGeneration = new ItemCodeGeneration(itemUniqueCode, category,
				dimensions + " " + unitsOfMeasure, itemDescription, manufacturer, countryOfOrigin);
		bomRepositoryService.addGeneratedItemCode(itemCodeGeneration);
		System.out.println("Item code created successfully");
	}

	/**
	 * Collects the BOMs details and at the end it will display the entered details
	 * 
	 * @param sc
	 * @throws InvalidIntegerException
	 * @throws SQLException
	 */
	public void collectBOMDetails(Scanner sc) throws InvalidIntegerException, SQLException {

		List<GenerateBOM> bomList = new ArrayList<>();
		while (true) {
			System.out.print("Enter Unique Code for BOM (must match Item Unique Code, 10 alphanumeric characters): ");
			String uniqueCodeInput = sc.nextLine();
			try {
				if (!uniqueCodeInput.matches("[A-Za-z0-9]{10}")) {
					throw new InvalidItemCodeException("Unique Code must be exactly 10 alphanumeric characters.");
				}
				if (!bomRepositoryService.isItemWIthIdAvailable(uniqueCodeInput)) {
					System.out.println("Error: Unique Code does not match. Please re-enter.");
					continue;
				}
				int quantity = validateIntegerInput("Quantity", sc);
				int supplierCode = validateIntegerInput("Supplier Code", sc);
				int subAssembly = validateIntegerInput("Sub Assembly Code", sc);
				GenerateBOM bom = new GenerateBOM(uniqueCodeInput, quantity, supplierCode, subAssembly);
				bomList.add(bom);
				System.out.println("1. Add more items to BOM\n2. Save");
				if (sc.nextLine().equals("1")) {
					continue;
				} else {
					break;
				}
			} catch (InvalidItemCodeException e) {
				System.out.println(e.getMessage());
			}
		}
		bomRepositoryService.addItemToBOMDB(bomList);
		displayBOMDetails(bomList);
	}

	/**
	 * Displays the details of BOM entered by the user. Details are taken from the
	 * DB and all details are available in DB
	 * 
	 * @param bomList
	 * @throws SQLException
	 */
	public void displayBOMDetails(List<GenerateBOM> bomList) throws SQLException {
		System.out.println("\nBOM Details:");
		System.out.printf("%-5s %-15s %-30s %-15s %-20s %-15s %-20s %-10s%n", "S.No", "Item Code", "Description",
				"Category", "Manufacturer", "Dimensions", "Country of Origin", "Quantity");

		int serialNumber = 1;

		for (GenerateBOM bom : bomList) {
			ItemCodeGeneration item = bomRepositoryService.getItemWithId(bom.getUniqueCode());
			if (item != null) {
				System.out.printf("%-5d %-15s %-30s %-15s %-20s %-15s %-20s %-10d%n", serialNumber++,
						item.getItemUniqueCode(), item.getItemDescription(), item.getCategory(), item.getManufacturer(),
						item.getDimensions(), item.getCountryOfOrigin(), bom.getQuantity());
			}
		}
	}

	/**
	 * Validates if the itemcode is 10 alphanumeric characters
	 * 
	 * @param input
	 * @return
	 * @throws InvalidItemCodeException
	 */
	private String validateItemUniqueCode(Scanner input) throws InvalidItemCodeException {
		while (true) {
			System.out.print("Enter Item Unique Code (10 alphanumeric characters): ");
			String itemUniqueCode = input.nextLine();
			try {
				if (!itemUniqueCode.matches("[A-Za-z0-9]{10}")) {
					throw new InvalidItemCodeException(
							"Invalid item code. Must be exactly 10 alphanumeric characters.");
				}
				if (bomRepositoryService.isItemWIthIdAvailable(itemUniqueCode)) {
					throw new InvalidItemCodeException("Unique item code already exists");
				}
				return itemUniqueCode;
			} catch (InvalidItemCodeException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Validation for empty values
	 * 
	 * @param fieldName
	 * @param sc
	 * @return
	 */
	private String getMandatoryInputWithRetry(String fieldName, Scanner sc) {
		while (true) {
			// Prompt user for input
			System.out.print("Enter " + fieldName + ": ");
			String input = sc.nextLine();
			if (input != null && !input.trim().isEmpty()) {
				return input; // Return the valid input
			} else {
				// If input is empty, print a warning and re-prompt
				System.out.println(fieldName + " is mandatory and cannot be empty. Please re-enter.");
			}
		}
	}

	/**
	 * validates if the inputs are integer
	 * 
	 * @param fieldName
	 * @param sc
	 * @return
	 */
	private int validateIntegerInput(String fieldName, Scanner sc) {
		while (true) {
			System.out.print("Enter " + fieldName + ": ");
			try {
				return Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid input for " + fieldName + ". Please enter an integer value.");
			}
		}
	}

	/**
	 * Validates the dimensions are in LxBxH format
	 * 
	 * @param sc
	 * @return
	 */
	private String validateDimensionsInput(Scanner sc) {
		while (true) {
			System.out.print("Enter Dimensions (format AxBxC): ");
			String dimensions = sc.nextLine();
			if (dimensions.matches("\\d+x\\d+x\\d+")) {
				return dimensions;
			} else {
				System.out.println(
						"Invalid format. Dimensions must be in the form AxBxC (e.g., 10x20x30). Please re-enter.");
			}
		}
	}

	/**
	 * Validates the measures by confirming if its mm, cm or inches
	 * 
	 * @param sc
	 * @return
	 */
	private String validateUnitsOfMeasure(Scanner sc) {
		while (true) {
			System.out.print("Enter Units of Measure (mm/cm/inches): ");
			String units = sc.nextLine().toLowerCase();
			if (units.equals("mm") || units.equals("cm") || units.equals("inches")) {
				return units;
			} else {
				System.out.println("Invalid input. Units of Measure must be 'mm', 'cm', or 'inches'. Please re-enter.");
			}
		}
	}
}
