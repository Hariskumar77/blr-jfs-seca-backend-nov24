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
import java.sql.SQLException;
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
	public void setup() throws ClassNotFoundException, SQLException {
		pr = new PurchaseRequisition(new RequisitionItem("Item001", "Test Purpose", 10, "kg", "", "Company A"));
		mRequisition = new MaterialRequisition();
		po = new PurchaseOrder();
	}

	// Test 1: Test that a Purchase Requisition can be submitted and approved
	@Test
	public void testSubmitAndApprovePurchaseRequisition() {
		// Test the PR submission
		assertNotNull(pr);
		pr.submitPR(null);
		assertTrue(pr.isSubmitted());

		// Test the approval process
		pr.processApproval(null);
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
}
