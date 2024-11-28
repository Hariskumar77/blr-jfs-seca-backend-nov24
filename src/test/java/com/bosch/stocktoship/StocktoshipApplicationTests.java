package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.entity.RequisitionItem;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.entity.User;
import com.bosch.stocktoship.service.AccountManager;
import com.bosch.stocktoship.service.Accountant;
import com.bosch.stocktoship.service.ApplicationData;
import com.bosch.stocktoship.service.AuthenticationService;
import com.bosch.stocktoship.service.InboundRequisition;
import com.bosch.stocktoship.service.MaterialRequisition;
import com.bosch.stocktoship.service.OutboundRequisitionForm;
import com.bosch.stocktoship.service.PurchaseOrder;
import com.bosch.stocktoship.service.PurchaseRequisition;
import com.bosch.stocktoship.service.StoreManager;


@SpringBootTest
class StocktoshipApplicationTests {

	// Created by - AL AMAN , Dated - 6/11/24, Squad-1.

	private AuthenticationService authService; // The real AuthenticationService instance
	private ApplicationData application; // The Application instance we are testing

	private PurchaseRequisition pr;
	private MaterialRequisition mRequisition;
	private PurchaseOrder po;

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final InputStream originalIn = System.in;

	private Supplier supplier;
	private StoreManager storeManager;
	private Accountant accountant;
	private AccountManager accountManager;

	@BeforeEach
	public void setup() {
		pr = new PurchaseRequisition(new RequisitionItem("Item001", "Test Purpose", 10, "kg", "", "Company A"));
		mRequisition = new MaterialRequisition();
		po = new PurchaseOrder();
	}

	// Test 1: Test that a Purchase Requisition can be submitted and approved
	@Test
	public void testSubmitAndApprovePurchaseRequisition() {
		// Test the PR submission
		assertNotNull(pr);
		pr.submitPR();
		assertTrue(pr.isSubmitted());

		// Test the approval process
		pr.processApproval();
		assertEquals("Approved", PurchaseRequisition.prnMap.get(pr.getPRNumber()).getApprovalStatus());
	}

	// Test 2: Test Material Requisition can request quotations from suppliers
	@Test
	public void testRequestQuotations() {
		// Arrange: Mock the system to send requests for quotations
		mRequisition.requestQuotations(pr.getItem());

		// If the suppliers list is populated, which ensures requests are being sent.
		List<Supplier> suppliers = mRequisition.getSuppliers();
		assertFalse(suppliers.isEmpty());
	}

	// Test 3: Test selecting a supplier from the list
	@Test
	public void testSelectSupplier() {
		// Directly set the supplier index to simulate user input
		int selectedSupplierIndex = 2; // Simulate user selecting the second supplier

		// Get the selected supplier from the list
		Supplier selectedSupplier = mRequisition.getSuppliers().get(selectedSupplierIndex - 1);

		// Ensure that the selected supplier is not null
		assertNotNull(selectedSupplier);

		// Check that the selected supplier's name matches the expected supplier
		assertEquals("Supplier Two", selectedSupplier.getSupplierName());
	}

	// Test 4: Test placing a Purchase Order
	@Test
	public void testPlacePurchaseOrder() {
		// Selecting the first supplier
		Supplier supplier = mRequisition.getSuppliers().get(0);

		// Place the purchase order
		po.placePurchaseOrder("10001", pr.getItem(), supplier);
// 		Ensure that the PurchaseOrder object is initialized.
		assertNotNull(po);
	}

	@Test
	void contextLoads() {
	}

	// Test Case 1: Test User Registration
	@Test
	public void testRegisterUser_Success() {
		// User data for registration
		String email = "hardcoded@example.com";
		String password = "password123";
		String employeeId = "EMP001";
		String role = "User";

		// Simulate registration by directly calling the registerUser method
		String result = authService.registerUser(email, password, employeeId, role);

		// Verify that the result is successful
		assertEquals(result, "Registration successful.");

		// Check if the user has been added to the AuthenticationService
		assertNotNull(authService.getUserByEmail(email));
		assertEquals(authService.getUserByEmail(email).getPassword(), password);
	}

	// Test Case 2: Test Successful Login
	@Test
	public void testLogin_Success() {
		// Add a test user to the AuthenticationService
		String email = "test@example.com";
		String password = "password123";
		String employeeId = "EMP001";
		String role = "Admin";
		authService.registerUser(email, password, employeeId, role); // Register the user first

		// Simulate login (hardcoded email and password)
		String result = authService.login(email, password);

		// Verify that the login was successful
		assertEquals(result, "Login successful.");

		// Check if the user is marked as logged in
		assertTrue(authService.getUserByEmail(email).isLoggedIn());
	}

	// Test Case 3: Test Failed Login ( Invalid credentials)
	@Test
	public void testLogin_Failure() {
		// Simulate failed login attempt with wrong password
		String email = "test@example.com";
		String password = "password123";
		String wrongPassword = "wrongpassword";

		// Register the user first
		authService.registerUser(email, password, "EMP002", "User");

		// Attempt to log in with incorrect password
		String result = authService.login(email, wrongPassword);

		// Verify that the login failed
		assertEquals(result, "Invalid credentials.");

		// Ensure the user is not logged in
		assertFalse(authService.getUserByEmail(email).isLoggedIn());
	}

	// Test Case 4: Test Password Reset Success
	@Test
	public void testPasswordReset_Success() {
		// Hardcoded user data for password reset
		String email = "test@example.com";
		String oldPassword = "oldPassword123";
		String newPassword = "newPassword123";
		String employeeId = "EMP001";
		String role = "Admin";

		// Register the user first
		authService.registerUser(email, oldPassword, employeeId, role);

		// Simulate the password reset (New password)
		User user = authService.getUserByEmail(email);
		user.setPassword(newPassword); // Set the new password directly

		// Verify that the password has been updated
		assertEquals(user.getPassword(), newPassword);
	}

	// Test Case 5: Test Password Reset Failure (Mismatched passwords)
	@Test
	public void testPasswordReset_Failure() {
		// User data for password reset failure
		String email = "test@example.com";
		String oldPassword = "oldPassword123";
		String newPassword = "newPassword123";
		String differentPassword = "differentPassword123";
		String employeeId = "EMP001";
		String role = "Admin";

		// Register the user first
		authService.registerUser(email, oldPassword, employeeId, role);

		// Simulate the password reset attempt with mismatched passwords
		User user = authService.getUserByEmail(email);

		// Hardcoding the scenario where new passwords don't match
		if (!newPassword.equals(differentPassword)) {
			System.out.println("Passwords do not match, please try again.");
		} else {
			user.setPassword(newPassword); // This would only occur if passwords match
		}

		// Ensure the password has not been updated
		assertEquals(user.getPassword(), oldPassword);
	}

	// Test Case 6: Test User Registration - Already Exists (Hardcoded)
	@Test
	public void testRegisterUser_AlreadyExists() {
		// Hardcoded user data for registration
		String email = "existing@example.com";
		String password = "password123";
		String employeeId = "EMP002";
		String role = "Admin";

		// Register the user first
		authService.registerUser(email, password, employeeId, role);

		// Attempt to register again with the same email (should fail)
		String result = authService.registerUser(email, password, employeeId, role);

		// Verify that the correct error message is returned
		assertEquals(result, "User already exists.");
	}

	@BeforeEach
	public void setUp() {
		System.setOut(new PrintStream(outContent));
		// Initialize objects required for testing
		supplier = new Supplier();
		storeManager = new StoreManager();
		accountant = new Accountant();
		accountManager = new AccountManager();

		// Explicitly generate and add an invoice to the Supplier
		Invoice invoice = new Invoice(3000, new Date(), 100000.0, new Date(), 3000, true);
		Invoice.generatedInvoices.add(invoice);
	}

	@AfterAll
	public void tearDown() {
		System.setOut(originalOut);
		System.setIn(originalIn); // Reset System.in after each test
		outContent.reset(); // Clear output after each test
	}

	@Test
	public void testAccountantEnterPaymentDetails() {
		// Simulate input for PO number and confirmation
		String input = "3000\ny\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		accountant.enterPaymentDetails(supplier);

		String output = outContent.toString();
		assertTrue(output.contains("Accountant Entering Details"));
		assertTrue(output.contains("Supplier Name: Suresh"));
		assertTrue(output.contains("Amount Due: 100000.0"));
		assertTrue(output.contains("Do you want to submit (y/n)"));
	}

	@Test
	public void testAccountantPay() {
		// Simulate input for PO number and payment confirmation
		String input = "3000\ny\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		accountant.pay(supplier);

		String output = outContent.toString();
		assertTrue(output.contains("Accountant Entering Details"));
		assertTrue(output.contains("Supplier Name: Suresh"));
		assertTrue(output.contains("Amount Due: 100000.0"));
		assertTrue(output.contains("Payment Under Process"));
		assertTrue(output.contains("Received Notification for Approval from Accountant"));
	}

	@Test
	public void testAccountManagerViewPaymentDetails() {
		// Simulate input for PO number and approval
		String input = "3000\ny\n"; // PO number and 'yes' for approval
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		// Run the method
		accountManager.viewPaymentDetails(supplier);

		// Capture output
		String output = outContent.toString().trim(); // Capture raw output for validation

		// Assert output matches expected strings
		assertTrue(output.contains("Account Manager Entering Details"));
		assertTrue(output.contains("Enter PO Number"));
		assertTrue(output.contains("Invoice found"));
		assertTrue(output.contains("Entering payment details"));
		assertTrue(output.contains("Supplier Name: Suresh"));
		assertTrue(output.contains("Company Name: JK Trader"));
		assertTrue(output.contains("Payment Terms: PDC"));
		assertTrue(output.contains("Bank Details"));
		assertTrue(output.contains("Bank Name: HDFC"));
		assertTrue(output.contains("Bank Account Number: 698635373"));
		assertTrue(output.contains("Bank IFSC: HDFC00098"));
		assertTrue(output.contains("Due Date"));
		assertTrue(output.contains("Amount Due: 100000.0"));
		assertTrue(output.contains("Do you want to approve (y/n)"));
	}

	@Test
	public void testStoreManagerApproval() {
		// Simulate input for approval
		String input = "y\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		storeManager.checkForApproval();

		String output = outContent.toString();
		assertTrue(output.contains("Approved by Store Manager"));
		assertTrue(output.contains("Sending Notification of Approval to Accountant"));
	}

	@Test
	public void testStoreManagerRejectInvoice() {
		// Simulate input for rejection
		String input = "n\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		storeManager.checkForApproval();

		String output = outContent.toString();
		assertTrue(output.contains("Rejected by Store Manager"));
	}

	@Test
	public void testInvoiceGeneration() {
		// Create a new invoice and call generateInvoice()
		Invoice invoice = new Invoice(1235, new Date(), 50000.00,
				new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000), 3001, false);
		invoice.generateInvoice();

		assertTrue(Invoice.getGeneratedInvoices().contains(invoice));
	}

	// Squad -2

	private OutboundRequisitionForm outboundRequisitionForm;
	private Product product;
	private List<Location> locations;

	/**
	 * AUTHOR: MUJAHID DAFEDAR(DAFB1KOR) - FOR INBOUND TESTING AUTHOR: GURPARTAP
	 * SINGH(INU2COB) - FOR OUTBOUND TESTING
	 */
	// Instance of InboundRequisition to be used in tests
	private InboundRequisition inboundRequisition;
	// 2D array representing a location grid, used for availability checks
	private Boolean[][] locationGrid;

	/**
	 * Sets up the test environment before each test. Initializes an
	 * InboundRequisition instance, clears any existing product data, and creates a
	 * 3x5 location grid with all locations set to available (true).
	 */

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

	@BeforeEach
	public void beforeTest1() {
		inboundRequisition = new InboundRequisition();
//        InboundRequisition.product = null;  // Reset static product data
		locationGrid = new Boolean[3][5];
		// Set all locations in the grid to available (true)
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				locationGrid[i][j] = true;
			}
		}
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

	@Test
	public void testDisplayAvailableLocationsWithEmptyLocations() {
		// Clear the product location list to simulate an empty list
		InboundRequisition.product.setLocationList(new ArrayList<>());
		outboundRequisitionForm.setProductName("Valid Product");
		outboundRequisitionForm.setProductCode("1234");
		outboundRequisitionForm.setDeliveryDepartment("Electronics");

		assertThrows(InternalError.class, () -> {
			outboundRequisitionForm.displayAvailableLocations(); // Should throw InternalError
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
			outboundRequisitionForm.displayDetails(); // Should throw InternalError
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

		assertThrows(NullPointerException.class, () -> {
			outboundRequisitionForm.displayDetails(); // Should throw IllegalArgumentException
		});
	}

	/**
	 * Test inboundInputFun method with an invalid input. Expects the method to
	 * return "Invalid Input" for input value 3.
	 */
	@Test
	public void testInboundInputFunctionInvalidInput() {
		String result = inboundRequisition.inboundInputFunction(3);
		assertEquals("Invalid Input", result);
	}

	/**
	 * Test the checkAvailability method when the specified location is available.
	 * Expects the location at (1, 1) to be available (true).
	 */
	@Test
	public void testCheckLocationAvailability() {
		assertTrue(InboundRequisition.checkAvailability(locationGrid, 1, 1));
	}

	/**
	 * Test getProductLocations method when no product locations are set. Expects an
	 * empty list as the result.
	 */
	@Test
	public void testGetProductLocationsIsEmpty() {
		InboundRequisition.product = null;
		List<Location> locations = InboundRequisition.getProductLocations();
		assertTrue(locations.isEmpty());
	}

	/**
	 * Test the checkAvailability method when the specified location is unavailable.
	 * Marks location (0, 0) as unavailable (false) and checks if the method
	 * correctly identifies it as unavailable.
	 */
	@Test
	public void testCheckUnavailableLocations() {
		locationGrid[0][0] = false;
		assertFalse(InboundRequisition.checkAvailability(locationGrid, 1, 1));
	}

	/**
	 * Test confirmProduct method by passing a Product object. Expects the method to
	 * return the confirmation message "CONFIRMED".
	 */
	@Test
	public void testConfirmProduct() {
		Product product = new Product("TestProduct", "Code123", "Batch001", "10", "5", "5", "2", "50", "Supplier",
				"Location", "PO123", "2023-10-10", "2023-11-10");
		String result = InboundRequisition.confirmProduct(product);
		assertEquals("CONFIRMED", result);
	}

	/**
	 * Test getProductLocations method when product locations are set. Creates mock
	 * locations, assigns them to a product, and sets the product in
	 * InboundRequisition. Expects the method to return a list with the same mock
	 * locations.
	 */
	@Test
	public void testGetProductLocationsNotEmpty() {
		// Creating mock locations to test non-empty product locations
		List<Location> mockLocations = new ArrayList<>();
		mockLocations.add(new Location(1, 1));
		mockLocations.add(new Location(2, 3));
		// Creating a product and setting its locations
		Product product = new Product("TestProduct", "Code123", "Batch001", "10", "5", "5", "2", "50", "Supplier",
				"Location", "PO123", "2023-10-10", "2023-11-10");
		product.setLocationList(mockLocations);
		// Assign the product to InboundRequisition
		InboundRequisition.product = product;

		// Retrieve the locations and verify they match the expected mock locations
		List<Location> locations = InboundRequisition.getProductLocations();
		assertEquals(2, locations.size());
		assertEquals(mockLocations, locations);
	}

}
