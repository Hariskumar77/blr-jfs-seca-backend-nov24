package com.bosch.stocktoship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Outbound testing - AUTHOR: Gurpartap Singh - INU2COB

import org.springframework.boot.test.context.SpringBootTest;
import com.bosch.stocktoship.service.InboundRequisition;
import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.service.OutboundRequisitionForm;
import com.bosch.stocktoship.entity.Product;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.*;

@SpringBootTest
public class OutboundTesting {

	private OutboundRequisitionForm outboundRequisitionForm;
	private Product product;
	private List<Location> locations;

	@BeforeEach
	public void beforeTest() {
		// Create a dummy product with sample values for setup
		product = new Product("Sample Product", "1234", "10", "11", "12", "13", "14", "15", "Sample Supplier",
				"Sample Supplier Location", "963852741", "12-12-2014", "14-12-2014");

		// Initialize and add locations to the product
		locations = new ArrayList<>();
		locations.add(new Location(1, 5)); // Location with ID 1 and quantity 5
		locations.add(new Location(2, 6)); // Location with ID 2 and quantity 6
		product.setLocationList(locations); // Assign locations to the product

		// Initialize the form instance to be used in test cases
		outboundRequisitionForm = new OutboundRequisitionForm();
	}

	// 1. Test `confirmForm()`
	@Test
	public void testConfirmFormWithValidData() {
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode("1234");
		outboundRequisitionForm.setDeliveryDepartment("Electronics");
		outboundRequisitionForm.setDepartmentInCharge("John Doe");

		String confirmFormString = outboundRequisitionForm.confirmForm();
		assertNotNull(confirmFormString);
		assertTrue(confirmFormString.contains("Product Name: Valid Product"));
		assertTrue(confirmFormString.contains("Delivery Department: Electronics"));
	}

	@Test
	public void testConfirmFormWithEmptyProductName() {
		outboundRequisitionForm.setProductName(""); // Empty product name
		outboundRequisitionForm.setProductCode("1234");
		outboundRequisitionForm.setDeliveryDepartment("Electronics");
		assertThrows(IllegalArgumentException.class, () -> {
			outboundRequisitionForm.confirmForm(); // Should throw IllegalArgumentException
		});
	}

	@Test
	public void testConfirmFormWithEmptyProductCode() {
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode(""); // Empty product code
		outboundRequisitionForm.setDeliveryDepartment("Electronics");
		assertThrows(IllegalArgumentException.class, () -> {
			outboundRequisitionForm.confirmForm(); // Should throw IllegalArgumentException
		});
	}

	@Test
	public void testConfirmFormWithEmptyDeliveryDepartment() {
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode("1234");
		outboundRequisitionForm.setDeliveryDepartment(""); // Empty delivery department

		assertThrows(IllegalArgumentException.class, () -> {
			outboundRequisitionForm.confirmForm(); // Should throw IllegalArgumentException
		});

	}

	// 2. Test `displayAvailableLocations()`
	@Test
	public void testDisplayAvailableLocationsWithValidProductCode() {
		InboundRequisition.product = product; // Set product to static variable in InboundRequisition
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode("1234");
		outboundRequisitionForm.setDeliveryDepartment("Electronics");

		// Capture the output printed to System.out
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos));

		outboundRequisitionForm.displayAvailableLocations();

		String output = baos.toString();
		assertTrue(output.contains("Available Locations for product Code 1234:"));
		assertTrue(output.contains("Rack 1, Shelf 5"));
		assertTrue(output.contains("Rack 2, Shelf 6"));
	}

	@Test
	public void testDisplayAvailableLocationsWithNonMatchingProductCode() {
		InboundRequisition.product = product; // Set product to static variable in InboundRequisition
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode("9999"); // Non-matching product code
		outboundRequisitionForm.setDeliveryDepartment("Electronics");
		assertThrows(IllegalArgumentException.class, () -> {
			outboundRequisitionForm.displayAvailableLocations(); // Should throw IllegalArgumentException
		});
	}

	public void testDisplayAvailableLocationsWithEmptyLocations() {
		// Clear the product location list to simulate an empty list
		InboundRequisition.product.setLocationList(new ArrayList<>());
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode("1234");
		outboundRequisitionForm.setDeliveryDepartment("Electronics");
		assertThrows(InternalError.class, () -> {
			outboundRequisitionForm.displayAvailableLocations(); // Should throw IllegalArgumentException
		});

	}

	// 3. Test `toString()`
	@Test
	public void testToString() {
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode("1234");
		outboundRequisitionForm.setDeliveryDepartment("Electronics");
		outboundRequisitionForm.setDepartmentInCharge("John Doe");

		String result = outboundRequisitionForm.toString();
		assertTrue(result.contains("Product Name: Valid Product"));
		assertTrue(result.contains("Product Code: 1234"));
		assertTrue(result.contains("Delivery Department: Electronics"));
		assertTrue(result.contains("Department In-charge: John Doe"));
	}

	// 4. Test `displayDetails()`
	@Test
	public void testDisplayDetailsWithValidInput() throws Exception {
		String userInput = "Valid Product\n1234\nElectronics\n"; // Simulate user input
		System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream

		// Capture the output printed to System.out
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		System.setOut(new PrintStream(baos)); // Redirect output stream

		outboundRequisitionForm.displayDetails();

		String output = baos.toString();
		assertTrue(output.contains("Outbound Requisition Form"));
		assertTrue(output.contains("Product Name "));
		assertTrue(output.contains("Product Code "));
		assertTrue(output.contains("Delivery Department"));
		assertTrue(output.contains("Available Locations for product Code 1234:"));
		assertTrue(output.contains("Rack 1, Shelf 5"));
		assertTrue(output.contains("Rack 2, Shelf 6"));
		assertTrue(output.contains("Product Name: Valid Product"));
		assertTrue(output.contains("Product Code: 1234"));
		assertTrue(output.contains("Delivery Department: Electronics"));
	}

	@Test
	public void testDisplayDetailsWithEmptyProductName() throws Exception {
		// Simulate user input where the product name is empty
		String userInput = "\n1234\nElectronics\n"; // Empty product name
		System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream

		// Setup an empty location list or a mock product to avoid triggering
		// InternalError
		InboundRequisition.product = new Product("", "1234", "10", "11", "12", "13", "14", "15", "Sample Supplier",
				"Sample Supplier Location", "963852741", "12-12-2014", "14-12-2014");
		InboundRequisition.product.setLocationList(new ArrayList<>()); // Empty location list
		assertThrows(InternalError.class, () -> {
			outboundRequisitionForm.displayDetails(); // Should throw InternalErrorException
		});

	}

	@Test
	public void testDisplayDetailsWithEmptyProductCode() throws Exception {
		String userInput = "Valid Product\n\nElectronics\n"; // Empty product code
		System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream
		assertThrows(IllegalArgumentException.class, () -> {
			outboundRequisitionForm.displayDetails(); // Should throw IllegalArgumentException
		});

	}

	@Test
	public void testDisplayDetailsWithEmptyDeliveryDepartment() throws Exception {
		String userInput = "Valid Product\n1234\n\n"; // Empty delivery department
		System.setIn(new ByteArrayInputStream(userInput.getBytes())); // Redirect input stream

		assertThrows(IllegalArgumentException.class, () -> {
			outboundRequisitionForm.displayDetails(); // Should throw IllegalArgumentException
		});
	}
}
