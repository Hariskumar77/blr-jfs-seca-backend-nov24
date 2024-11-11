package com.bosch.stocktoship.entity;


//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024


//Class to represent a Bill of Materials (BOM) with associated properties.
public class GenerateBOM {
	// Unique code for the BOM, represented as a long for a 10-digit integer
    private String uniqueCodeInput;  // Change to String for alphanumeric 10-character code
	// Quantity of items in the BOM
	private int quantity;
	// Dimensions associated with the BOM
	private String dimensions;
	// Units of measure for the BOM
	private String unitsOfMeasure;
	// Supplier code associated with the BOM
	private int supplierCode;
	// Sub-assembly code for items in the BOM
	private int subAssembly;

	// Constructor to initialize a GenerateBOM object with provided values
	public GenerateBOM(String uniqueCodeInput, int quantity, int supplierCode, int subAssembly) {
	    this.uniqueCodeInput = uniqueCodeInput;
	    this.quantity = quantity;
	    this.supplierCode = supplierCode;
	    this.subAssembly = subAssembly;
	}


	// Getter method to retrieve the unique code for BOM
	public String getUniqueCode() {
		return uniqueCodeInput;
	}

	// Setter method to update the unique code for BOM
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCodeInput = uniqueCode;
	}

	// Getter method to retrieve the quantity of items in the BOM
	public int getQuantity() {
		return quantity;
	}

	// Setter method to update the quantity of items in the BOM
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	// Getter method to retrieve the dimensions of the BOM
	public String getDimensions() {
		return dimensions;
	}

	// Setter method to update the dimensions of the BOM
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}

	// Getter method to retrieve the units of measure for the BOM
	public String getUnitsOfMeasure() {
		return unitsOfMeasure;
	}

	// Setter method to update the units of measure for the BOM
	public void setUnitsOfMeasure(String unitsOfMeasure) {
		this.unitsOfMeasure = unitsOfMeasure;
	}

	// Getter method to retrieve the supplier code for the BOM
	public int getSupplierCode() {
		return supplierCode;
	}

	// Setter method to update the supplier code for the BOM
	public void setSupplierCode(int supplierCode) {
		this.supplierCode = supplierCode;
	}

	// Getter method to retrieve the sub-assembly code for the BOM
	public int getSubAssembly() {
		return subAssembly;
	}

	// Setter method to update the sub-assembly code for the BOM
	public void setSubAssembly(int subAssembly) {
		this.subAssembly = subAssembly;
	}
}
