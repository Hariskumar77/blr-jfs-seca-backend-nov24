package com.bosch.stocktoship;

import org.junit.jupiter.api.AfterEach;  // JUnit 5 annotation for teardown method
import org.junit.jupiter.api.BeforeEach; // JUnit 5 annotation for setup method
import org.junit.jupiter.api.Test;      // JUnit 5 annotation for test methods
import java.io.*;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.AccountManager;
import com.bosch.stocktoship.service.Accountant;
import com.bosch.stocktoship.service.StoreManager;

// created by Aman Kumar
public class PaymentTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    private Supplier supplier;
    private StoreManager storeManager;
    private Accountant accountant;
    private AccountManager accountManager;

    /**
     * This setup method runs before each test to initialize the necessary services and 
     * prepares the environment for the tests.
     */
    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output during the tests
        System.setOut(new PrintStream(outContent));

        // Initialize required objects
        supplier = new Supplier();
        storeManager = new StoreManager();
        accountant = new Accountant();
        accountManager = new AccountManager();

        // Create and add an invoice to the supplier
        Invoice invoice = new Invoice(3000, new Date(), 100000.0, new Date(), 3000, true);
        Invoice.generatedInvoices.add(invoice);
    }

    /**
     * This teardown method runs after each test to reset the output streams and clean up
     * after the test execution.
     */
    @AfterEach
    public void tearDown() {
        // Reset the output streams and cleanup
        System.setOut(originalOut);
        System.setIn(originalIn);  // Reset System.in
        outContent.reset(); // Clear the captured output
    }

    /**
     * Test Case: Test Accountant entering payment details for a supplier.
     * This test simulates the accountant entering the payment details for a supplier,
     * and ensures that the correct prompts and messages are printed to the output.
     */
    @Test
    public void testAccountantEnterPaymentDetails() {
        // Simulate user input for PO number and confirmation (yes)
        String input = "3000\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Simulate the accountant entering payment details
        accountant.enterPaymentDetails(supplier);

        // Capture the output
        String output = outContent.toString();

        // Assert that the expected messages are present in the output
        assertTrue(output.contains("Accountant Entering Details"), "Accountant entering details message missing");
        assertTrue(output.contains("Supplier Name: Suresh"), "Supplier name missing");
        assertTrue(output.contains("Amount Due: 100000.0"), "Amount Due missing");
        assertTrue(output.contains("Do you want to submit (y/n)"), "Submission confirmation prompt missing");
    }

    /**
     * Test Case: Test Accountant making a payment.
     * This test verifies that the accountant correctly processes a payment, with the
     * expected messages printed to the output.
     */
    @Test
    public void testAccountantPay() {
        // Simulate user input for PO number and payment confirmation (yes)
        String input = "3000\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Simulate the accountant making a payment
        accountant.pay(supplier);

        // Capture the output
        String output = outContent.toString();

        // Assert that the expected messages are present in the output
        assertTrue(output.contains("Accountant Entering Details"), "Accountant entering details message missing");
        assertTrue(output.contains("Supplier Name: Suresh"), "Supplier name missing");
        assertTrue(output.contains("Amount Due: 100000.0"), "Amount Due missing");
        assertTrue(output.contains("Payment Under Process"), "Payment process message missing");
        assertTrue(output.contains("Received Notification for Approval from Accountant"), "Notification to Account Manager missing");
    }

    /**
     * Test Case: Test Account Manager viewing and approving payment details.
     * This test simulates the Account Manager reviewing and approving the payment details,
     * ensuring that the correct messages are printed.
     */
    @Test
    public void testAccountManagerViewPaymentDetails() {
        // Simulate user input for PO number and approval confirmation (yes)
        String input = "3000\ny\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Simulate Account Manager viewing payment details
        accountManager.viewPaymentDetails(supplier);

        // Capture the output
        String output = outContent.toString().trim();

        // Assert that expected details are printed in the output
        assertTrue(output.contains("Account Manager Entering Details"), "Missing Account Manager entering details message");
        assertTrue(output.contains("Enter PO Number"), "Prompt for PO number missing");
        assertTrue(output.contains("Invoice found"), "Invoice found confirmation missing");
        assertTrue(output.contains("Entering payment details"), "Entering payment details prompt missing");
        assertTrue(output.contains("Supplier Name: Suresh"), "Supplier name missing in details");
        assertTrue(output.contains("Company Name: JK Trader"), "Company name missing in details");
        assertTrue(output.contains("Payment Terms: PDC"), "Payment terms missing in details");
        assertTrue(output.contains("Bank Details"), "Bank details header missing");
        assertTrue(output.contains("Bank Name: HDFC"), "Bank name missing");
        assertTrue(output.contains("Bank Account Number: 698635373"), "Bank account number missing");
        assertTrue(output.contains("Bank IFSC: HDFC00098"), "IFSC code missing");
        assertTrue(output.contains("Due Date"), "Due date missing");
        assertTrue(output.contains("Amount Due: 100000.0"), "Amount due missing");
        assertTrue(output.contains("Do you want to approve (y/n)"), "Approval prompt missing");
    }

    /**
     * Test Case: Test Store Manager approval of payment.
     * This test simulates the Store Manager approving the payment and ensures the correct messages are printed.
     */
    @Test
    public void testStoreManagerApproval() {
        // Simulate user input for approval confirmation (yes)
        String input = "y\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Simulate the Store Manager approval process
        storeManager.checkForApproval();

        // Capture the output
        String output = outContent.toString();

        // Assert that the approval and notification messages are printed correctly
        assertTrue(output.contains("Approved by Store Manager"), "Approval message missing");
        assertTrue(output.contains("Sending Notification of Approval to Accountant"), "Notification to Accountant missing");
    }

    /**
     * Test Case: Test Store Manager rejection of an invoice.
     * This test simulates the Store Manager rejecting the payment and ensures the rejection message is printed.
     */
    @Test
    public void testStoreManagerRejectInvoice() {
        // Simulate user input for rejection confirmation (no)
        String input = "n\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Simulate the Store Manager rejection process
        storeManager.checkForApproval();

        // Capture the output
        String output = outContent.toString();

        // Assert that the rejection message is printed correctly
        assertTrue(output.contains("Rejected by Store Manager"), "Rejection message missing");
    }

    /**
     * Test Case: Test invoice generation and adding it to the list.
     * This test ensures that an invoice is successfully generated and added to the generated invoice list.
     */
    @Test
    public void testInvoiceGeneration() {
        // Create a new invoice and call generateInvoice()
        Invoice invoice = new Invoice(1235, new Date(), 50000.00, new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000), 3001, false);
        invoice.generateInvoice();

        // Assert that the generated invoice is added to the list
        assertTrue(Invoice.getGeneratedInvoices().contains(invoice), "Invoice was not added to the list");
    }
}
