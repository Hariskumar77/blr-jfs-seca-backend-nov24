package com.bosch.stocktoship.service;
//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024
import java.util.*;

import com.bosch.stocktoship.entity.GenerateBOM;
import com.bosch.stocktoship.entity.ItemCodeGeneration;

public class BOMMain {
    private List<GenerateBOM> bomList = new ArrayList<>();  // List to store BOM entries
    private Scanner sc = new Scanner(System.in);  // Scanner object to read user input

    // Method to collect and validate item details from the user
    public ItemCodeGeneration collectItemDetails() throws InvalidItemCodeException {
        // Validate and retrieve unique item code
        String itemUniqueCode = validateItemUniqueCode();

        // Collect and validate the category
        String category = getMandatoryInputWithRetry("Category");

        // Validate dimensions input (must be in AxBxC format)
        String dimensions = validateDimensionsInput();

        // Validate units of measure (mm, cm, or inches)
        String unitsOfMeasure = validateUnitsOfMeasure();

        // Collect mandatory item description
        String itemDescription = getMandatoryInputWithRetry("Item Description");

        // Collect mandatory manufacturer name
        String manufacturer = getMandatoryInputWithRetry("Manufacturer");

        // Collect mandatory country of origin
        String countryOfOrigin = getMandatoryInputWithRetry("Country of Origin");

        // Create and return an ItemCodeGeneration object with collected details
        return new ItemCodeGeneration(itemUniqueCode, category, dimensions + " " + unitsOfMeasure, itemDescription,
                                      manufacturer, countryOfOrigin);
    }

    // Method to collect BOM details for an item and validate the inputs
    public void collectBOMDetails(ItemCodeGeneration item) throws InvalidIntegerException {
        while (true) {
            // Prompt user for unique BOM code
            System.out.print("Enter Unique Code for BOM (must match Item Unique Code, 10 alphanumeric characters): ");
            String uniqueCodeInput = sc.nextLine();
            try {
                // Validate that unique code matches the required format (10 alphanumeric characters)
                if (!uniqueCodeInput.matches("[A-Za-z0-9]{10}")) {
                    throw new InvalidItemCodeException("Unique Code must be exactly 10 alphanumeric characters.");
                }

                // Check if the unique code matches the item unique code
                if (!uniqueCodeInput.equals(item.getItemUniqueCode())) {
                    System.out.println("Error: Unique Code does not match. Please re-enter.");
                    continue;
                }

                // Collect and validate quantity, supplier code, and sub-assembly code
                int quantity = validateIntegerInput("Quantity");
                int supplierCode = validateIntegerInput("Supplier Code");
                int subAssembly = validateIntegerInput("Sub Assembly Code");

                // Create a new GenerateBOM object and add it to the BOM list
                GenerateBOM bom = new GenerateBOM(uniqueCodeInput, quantity, supplierCode, subAssembly);
                bomList.add(bom);
                break;  // Exit the loop once valid input is received
            } catch (InvalidItemCodeException e) {
                // If an error occurs, print the message and re-prompt the user
                System.out.println(e.getMessage());
            }
        }
    }

    // Method to display the details of all BOMs
    public void displayBOMDetails(ItemCodeGeneration item) {
        // Display header for the BOM details table
        System.out.println("\nBOM Details:");
        System.out.printf("%-5s %-15s %-30s %-15s %-20s %-15s %-20s %-10s%n",
                "S.No", "Item Code", "Description", "Category", "Manufacturer", "Dimensions", "Country of Origin", "Quantity");

        int serialNumber = 1;  // Initialize serial number for listing BOMs

        // Iterate over the bomList and display each BOM's details
        for (GenerateBOM bom : bomList) {
            System.out.printf("%-5d %-15s %-30s %-15s %-20s %-15s %-20s %-10d%n",
                    serialNumber++,  // Serial number for each row
                    item.getItemUniqueCode(),  // Item unique code from the ItemCodeGeneration object
                    item.getItemDescription(),  // Item description from the ItemCodeGeneration object
                    item.getCategory(),  // Category from the ItemCodeGeneration object
                    item.getManufacturer(),  // Manufacturer from the ItemCodeGeneration object
                    item.getDimensions(),  // Dimensions from the ItemCodeGeneration object
                    item.getCountryOfOrigin(),  // Country of origin from the ItemCodeGeneration object
                    bom.getQuantity());  // Quantity from the GenerateBOM object
        }
    }

    // Method to validate the item unique code input
    private String validateItemUniqueCode() throws InvalidItemCodeException {
        while (true) {
            // Prompt user to enter the item unique code
            System.out.print("Enter Item Unique Code (10 alphanumeric characters): ");
            String itemUniqueCode = sc.nextLine();
            try {
                // Validate the format of the item unique code
                if (!itemUniqueCode.matches("[A-Za-z0-9]{10}")) {
                    throw new InvalidItemCodeException("Invalid item code. Must be exactly 10 alphanumeric characters.");
                }
                // Return the valid unique code
                return itemUniqueCode;
            } catch (InvalidItemCodeException e) {
                // If the input is invalid, print the error message and re-prompt
                System.out.println(e.getMessage());
            }
        }
    }

    // Method to get mandatory input from the user with retry if empty
    private String getMandatoryInputWithRetry(String fieldName) {
        while (true) {
            // Prompt user for input
            System.out.print("Enter " + fieldName + ": ");
            String input = sc.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input;  // Return the valid input
            } else {
                // If input is empty, print a warning and re-prompt
                System.out.println(fieldName + " is mandatory and cannot be empty. Please re-enter.");
            }
        }
    }

    // Method to validate integer input (e.g., quantity, supplier code, sub-assembly)
    private int validateIntegerInput(String fieldName) {
        while (true) {
            // Prompt user for integer input
            System.out.print("Enter " + fieldName + ": ");
            try {
                // Attempt to parse the input as an integer
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                // If input is not an integer, print an error message and re-prompt
                System.out.println("Invalid input for " + fieldName + ". Please enter an integer value.");
            }
        }
    }

    // Method to validate the dimensions input (must be in AxBxC format)
    private String validateDimensionsInput() {
        while (true) {
            // Prompt user to enter the dimensions
            System.out.print("Enter Dimensions (format AxBxC): ");
            String dimensions = sc.nextLine();
            if (dimensions.matches("\\d+x\\d+x\\d+")) {
                return dimensions;  // Return valid dimensions
            } else {
                // If input format is invalid, print an error message and re-prompt
                System.out.println("Invalid format. Dimensions must be in the form AxBxC (e.g., 10x20x30). Please re-enter.");
            }
        }
    }

    // Method to validate the units of measure input (must be mm, cm, or inches)
    private String validateUnitsOfMeasure() {
        while (true) {
            // Prompt user to enter units of measure
            System.out.print("Enter Units of Measure (mm/cm/inches): ");
            String units = sc.nextLine().toLowerCase();
            if (units.equals("mm") || units.equals("cm") || units.equals("inches")) {
                return units;  // Return valid units of measure
            } else {
                // If input is invalid, print an error message and re-prompt
                System.out.println("Invalid input. Units of Measure must be 'mm', 'cm', or 'inches'. Please re-enter.");
            }
        }
    }
}
