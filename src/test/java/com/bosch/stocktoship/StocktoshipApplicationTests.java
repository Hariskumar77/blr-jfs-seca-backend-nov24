package com.bosch.stocktoship;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.entity.User;
import com.bosch.stocktoship.service.AccountManager;
import com.bosch.stocktoship.service.Accountant;
import com.bosch.stocktoship.service.ApplicationData;
import com.bosch.stocktoship.service.AuthenticationService;
import com.bosch.stocktoship.service.StoreManager;

import static org.hamcrest.CoreMatchers.*;

import java.io.*;


@SpringBootTest
class StocktoshipApplicationTests {

	@Test
	void contextLoads() {
	}

	
	
	//Created by - AL AMAN , Dated - 6/11/24, Squad-1.




	
	    private AuthenticationService authService;  // The real AuthenticationService instance
	    private ApplicationData application;           // The Application instance we are testing

	    @Before
	    public void setUp(){
	        authService = new AuthenticationService();  // Instantiate the real AuthenticationService
	        application = new ApplicationData();  // Null Scanner as we won't be using it
	    }

	    // Test Case 1: Test User Registration 
	    @Test
	    public void testRegisterUser_Success(){
	        // User data for registration
	        String email = "hardcoded@example.com";
	        String password = "password123";
	        String employeeId = "EMP001";
	        String role = "User";

	        // Simulate registration by directly calling the registerUser method
	        String result = authService.registerUser(email, password, employeeId, role);

	        // Verify that the result is successful
	        assertThat(result, is("Registration successful."));

	        // Check if the user has been added to the AuthenticationService
	        assertNotNull(authService.getUserByEmail(email));
	        assertThat(authService.getUserByEmail(email).getPassword(), is(password));
	    }

	    // Test Case 2: Test Successful Login 
	    @Test
	    public void testLogin_Success(){
	        // Add a test user to the AuthenticationService 
	        String email = "test@example.com";
	        String password = "password123";
	        String employeeId = "EMP001";
	        String role = "Admin";
	        authService.registerUser(email, password, employeeId, role);  // Register the user first

	        // Simulate login (hardcoded email and password)
	        String result = authService.login(email, password);

	        // Verify that the login was successful
	        assertThat(result, is("Login successful."));

	        // Check if the user is marked as logged in
	        assertTrue(authService.getUserByEmail(email).isLoggedIn());
	    }

	    // Test Case 3: Test Failed Login ( Invalid credentials)
	    @Test
	    public void testLogin_Failure(){
	        // Simulate failed login attempt with wrong password 
	        String email = "test@example.com";
	        String password = "password123";
	        String wrongPassword = "wrongpassword";

	        // Register the user first
	        authService.registerUser(email, password, "EMP002", "User");

	        // Attempt to log in with incorrect password
	        String result = authService.login(email, wrongPassword);

	        // Verify that the login failed
	        assertThat(result, is("Invalid credentials."));

	        // Ensure the user is not logged in
	        assertFalse(authService.getUserByEmail(email).isLoggedIn());
	    }

	    // Test Case 4: Test Password Reset Success 
	    @Test
	    public void testPasswordReset_Success(){
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
	        user.setPassword(newPassword);  // Set the new password directly

	        // Verify that the password has been updated
	        assertThat(user.getPassword(), is(newPassword));
	    }

	    // Test Case 5: Test Password Reset Failure (Mismatched passwords)
	    @Test
	    public void testPasswordReset_Failure(){
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
	        if (!newPassword.equals(differentPassword)) 
	        {
	            System.out.println("Passwords do not match, please try again.");
	        } else 
	        {
	            user.setPassword(newPassword);  // This would only occur if passwords match
	        }

	        // Ensure the password has not been updated
	        assertThat(user.getPassword(), is(oldPassword));
	    }

	    // Test Case 6: Test User Registration - Already Exists (Hardcoded)
	    @Test
	    public void testRegisterUser_AlreadyExists(){
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
	        assertThat(result, is("User already exists."));
	    }
	

	    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    private final PrintStream originalOut = System.out;
	    private final InputStream originalIn = System.in;
	 
	    private Supplier supplier;
	    private StoreManager storeManager;
	    private Accountant accountant;
	    private AccountManager accountManager;
	 
	    @Before
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
	 
	    @After
	    public void tearDown() {
	        System.setOut(originalOut);
	        System.setIn(originalIn);  // Reset System.in after each test
	        outContent.reset(); // Clear output after each test
	    }
	 
	    @Test
	    public void testAccountantEnterPaymentDetails() {
	        // Simulate input for PO number and confirmation
	        String input = "3000\ny\n"; 
	        System.setIn(new ByteArrayInputStream(input.getBytes()));
	 
	        accountant.enterPaymentDetails(supplier);
	 
	        String output = outContent.toString();
	        assertTrue("Accountant entering details message missing", output.contains("Accountant Entering Details"));
	        assertTrue("Supplier name missing", output.contains("Supplier Name: Suresh"));
	        assertTrue("Amount Due missing", output.contains("Amount Due: 100000.0"));
	        assertTrue("Submission confirmation prompt missing", output.contains("Do you want to submit (y/n)"));
	    }
	 
	    @Test
	    public void testAccountantPay() {
	        // Simulate input for PO number and payment confirmation
	        String input = "3000\ny\n"; 
	        System.setIn(new ByteArrayInputStream(input.getBytes()));
	 
	        accountant.pay(supplier);
	 
	        String output = outContent.toString();
	        assertTrue("Accountant entering details message missing", output.contains("Accountant Entering Details"));
	        assertTrue("Supplier name missing", output.contains("Supplier Name: Suresh"));
	        assertTrue("Amount Due missing", output.contains("Amount Due: 100000.0"));
	        assertTrue("Payment process message missing", output.contains("Payment Under Process"));
	        assertTrue("Notification to Account Manager missing", output.contains("Received Notification for Approval from Accountant"));
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
	        assertTrue("Missing Account Manager entering details message", output.contains("Account Manager Entering Details"));
	        assertTrue("Prompt for PO number missing", output.contains("Enter PO Number"));
	        assertTrue("Invoice found confirmation missing", output.contains("Invoice found"));
	        assertTrue("Entering payment details prompt missing", output.contains("Entering payment details"));
	        assertTrue("Supplier name missing in details", output.contains("Supplier Name: Suresh"));
	        assertTrue("Company name missing in details", output.contains("Company Name: JK Trader"));
	        assertTrue("Payment terms missing in details", output.contains("Payment Terms: PDC"));
	        assertTrue("Bank details header missing", output.contains("Bank Details"));
	        assertTrue("Bank name missing", output.contains("Bank Name: HDFC"));
	        assertTrue("Bank account number missing", output.contains("Bank Account Number: 698635373"));
	        assertTrue("IFSC code missing", output.contains("Bank IFSC: HDFC00098"));
	        assertTrue("Due date missing", output.contains("Due Date"));
	        assertTrue("Amount due missing", output.contains("Amount Due: 100000.0"));
	        assertTrue("Approval prompt missing", output.contains("Do you want to approve (y/n)"));
	    }
	 
	 
	    @Test
	    public void testStoreManagerApproval() {
	        // Simulate input for approval
	        String input = "y\n";
	        System.setIn(new ByteArrayInputStream(input.getBytes()));
	 
	        storeManager.checkForApproval();
	 
	        String output = outContent.toString();
	        assertTrue("Approval message missing", output.contains("Approved by Store Manager"));
	        assertTrue("Notification to Accountant missing", output.contains("Sending Notification of Approval to Accountant"));
	    }
	 
	    @Test
	    public void testStoreManagerRejectInvoice() {
	        // Simulate input for rejection
	        String input = "n\n";
	        System.setIn(new ByteArrayInputStream(input.getBytes()));
	 
	        storeManager.checkForApproval();
	 
	        String output = outContent.toString();
	        assertTrue("Rejection message missing", output.contains("Rejected by Store Manager"));
	    }
	 
	    @Test
	    public void testInvoiceGeneration() {
	        // Create a new invoice and call generateInvoice()
	        Invoice invoice = new Invoice(1235, new Date(), 50000.00, new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000), 3001, false);
	        invoice.generateInvoice();
	 
	        assertTrue("Invoice was not added to the list", Invoice.getGeneratedInvoices().contains(invoice));
	    }






}


