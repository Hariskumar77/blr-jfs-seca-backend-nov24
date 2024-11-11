package com.bosch.stocktoship.entity;


//CODE WRITTEN BY HARSHAVARDHAN VS - 6th November 2024

//Class to represent an item code generation with various properties.
public class ItemCodeGeneration {
 // Unique code for the item, represented as a long to accommodate 10-digit integers
 private String itemUniqueCode;
 // Category of the item
 private String category;
 // Dimensions of the item
 private String dimensions;
 // Description of the item
 private String itemDescription;
 // Manufacturer of the item
 private String manufacturer;
 // Country where the item is produced
 private String countryOfOrigin;

 // Constructor to initialize the ItemCodeGeneration object with provided values
 public ItemCodeGeneration(String itemUniqueCode, String category, String dimensions, 
                           String itemDescription, String manufacturer, String countryOfOrigin) {
     this.itemUniqueCode = itemUniqueCode; // Set the item unique code
     this.category = category; // Set the category
     this.dimensions = dimensions; // Set the dimensions
     this.itemDescription = itemDescription; // Set the item description
     this.manufacturer = manufacturer; // Set the manufacturer
     this.countryOfOrigin = countryOfOrigin; // Set the country of origin
 }

 public ItemCodeGeneration() {
	// TODO Auto-generated constructor stub
}

// Getter method to retrieve the item unique code
 public String getItemUniqueCode() {
     return itemUniqueCode;
 }

 // Setter method to update the item unique code
 public void setItemUniqueCode(String itemUniqueCode) {
     this.itemUniqueCode = itemUniqueCode;
 }

 // Getter method to retrieve the category of the item
 public String getCategory() {
     return category;
 }

 // Setter method to update the category of the item
 public void setCategory(String category) {
     this.category = category;
 }

 // Getter method to retrieve the dimensions of the item
 public String getDimensions() {
     return dimensions;
 }

 // Setter method to update the dimensions of the item
 public void setDimensions(String dimensions) {
     this.dimensions = dimensions;
 }

 // Getter method to retrieve the description of the item
 public String getItemDescription() {
     return itemDescription;
 }

 // Setter method to update the description of the item
 public void setItemDescription(String itemDescription) {
     this.itemDescription = itemDescription;
 }

 // Getter method to retrieve the manufacturer of the item
 public String getManufacturer() {
     return manufacturer;
 }

 // Setter method to update the manufacturer of the item
 public void setManufacturer(String manufacturer) {
     this.manufacturer = manufacturer;
 }

 // Getter method to retrieve the country of origin of the item
 public String getCountryOfOrigin() {
     return countryOfOrigin;
 }

 // Setter method to update the country of origin of the item
 public void setCountryOfOrigin(String countryOfOrigin) {
     this.countryOfOrigin = countryOfOrigin;
 }
}

