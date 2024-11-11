//Created by AKASH HEGDE P on 4th November Squad 1-

package com.bosch.stocktoship.service;

import java.util.*;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.Supplier;



public class AccountManager {
    
    private long poNumber;

    // Static initializer block, runs once when the class is loaded
    static {
        System.out.println();
        System.out.println("Inside Account Manager Class");
    }

    // Custom exception to handle cases when an invoice is not found
    public class InvoiceNotFoundException extends Exception {
        public InvoiceNotFoundException(String message) {
            super(message);
        }
    }

    // Method to receive a notification for approval from Accountant
    public static void receiveNotification() {
        System.out.println("Received Notification for Approval from Accountant");
    }

    // Method to view payment details for a specific supplier
    public void viewPaymentDetails(Supplier supplier) {
        Scanner sc = new Scanner(System.in);
        Invoice invoiceObject = null;
        int status = 0;
        System.out.println("Account Manager Entering Details");

        // Loop until a valid invoice is found
        while (status == 0) {
            try {
                System.out.println("Enter PO Number");
                poNumber = sc.nextLong();

                // Loop through generated invoices to find a match
                for (Invoice invoice : Invoice.generatedInvoices) {
                    if (invoice.getPoNumber() == poNumber) {
                        System.out.println("Invoice found");
                        invoiceObject = invoice;
                        status = 1;
                        break;
                    }
                }

                // If no matching invoice is found, throw an exception
                if (status == 0) {
                    throw new InvoiceNotFoundException("No matching invoice found for PO Number: ");
                }

            } catch (InvoiceNotFoundException e) {
                System.out.println(e.getMessage() + " Please enter a valid PO number.");
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid PO number.");
            }
        }

        // Display payment details if invoice is approved
        if (Invoice.invoiceStatus) {
            System.out.println("Entering payment details");

            System.out.println("Supplier Name: " + supplier.getSupplierName());
            System.out.println("Company Name: " + supplier.getComapanyName());
            System.out.println("Payment Terms: " + supplier.getPaymentTerms());

            System.out.println("Bank Details");
            System.out.println("Bank Name: " + supplier.getBankName());
            System.out.println("Bank Account Number: " + supplier.getBankAccountNumber());
            System.out.println("Bank IFSC: " + supplier.getIfscCode());

            System.out.println("Due Date: " + invoiceObject.getDueDate());
            System.out.println("Amount Due: " + invoiceObject.getAmount());

            System.out.println("Do you want to approve (y/n)");
            char ans = sc.next().charAt(0);
            if (ans == 'y') {
                approvePayment();
            } else if (ans == 'n') {
                rejectPayment();
            }
        } else {
            System.out.println("Invoice is rejected by Accountant");
        }
    }

    // Method to approve payment and confirm approval
    public void approvePayment() {
        System.out.println("Payment Approved");
    }

    // Method to reject payment and send a rejection notification to Accountant
    public void rejectPayment() {
        System.out.println("Sending Rejection message to Accountant");
        Accountant.notificationByAccountantManager(false);
    }
}
