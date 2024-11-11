//Created by AKASH HEGDE P on 4th November Squad 1

package com.bosch.stocktoship.service;

import java.util.*;

import com.bosch.stocktoship.entity.Invoice;
import com.bosch.stocktoship.entity.Supplier;





public class Accountant {

    // Static initializer block, runs once when the class is loaded
    static {
        System.out.println();
        System.out.println("Inside Accountant Class");
    }

    // Custom exception to handle cases when an invoice is not found
    public class InvoiceNotFoundException extends Exception {
        public InvoiceNotFoundException(String message) {
            super(message);
        }
    }

    // Method to notify the Accountant about verification
    public static void notificationByStoreManager() {
        System.out.println("Received for Invoice Verification");
    }

    // Method to notify about payment processing or rejection
    public static void notificationByAccountantManager(boolean toDo) {
        if (toDo) {
            System.out.println("Received for Payment Process");
        } else {
            System.out.println("Payment Rejected by Manager");
        }
    }

    // Method for Accountant to enter payment details
    public void enterPaymentDetails(Supplier supplier) {
        Invoice invoiceObject = null;
        int status = 0;
        Scanner sc = new Scanner(System.in);

        // Loop until a valid invoice is found
        while (status == 0) {
            try {
                System.out.println("Accountant Entering Details");
                System.out.println("Enter PO Number");
                long poNum = sc.nextLong();

                // Loop through generated invoices to find a match
                for (Invoice invoice : Invoice.generatedInvoices) {
                    if (invoice.getPoNumber() == poNum) {
                        System.out.println("Invoice found");
                        invoiceObject = invoice;
                        status = 1;
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

        // If invoice is approved, display payment details
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

            System.out.println("Do you want to submit (y/n)");
            char ans = sc.next().charAt(0);
            if (ans == 'y') {
                submit();
            } else {
                Invoice.invoiceStatus = false;
            }

        } else {
            System.out.println("Invoice is rejected by Store Manager");
        }
    }

    // Method to submit the payment details and notify based on due date
    public void submit() {
        System.out.println("Sends Notification on due date");
    }

    // Method to initiate the payment process
    public void pay(Supplier supplier) {
        Scanner sc = new Scanner(System.in);
        Invoice in1 = null;
        int status = 0;

        System.out.println("Accountant Entering Details");

        // Loop until a valid invoice is found
        while (status == 0) {
            try {
                System.out.println("Enter PO Number");
                long poNum = sc.nextLong();

                // Loop through generated invoices to find a match
                for (Invoice invoice : Invoice.generatedInvoices) {
                    if (invoice.getPoNumber() == poNum) {
                        System.out.println("Invoice found");
                        in1 = invoice;
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
                sc.nextLine();
            }
        }

        // Display payment details
        System.out.println("Entering payment details");

        System.out.println("Supplier Name: " + supplier.getSupplierName());
        System.out.println("Company Name: " + supplier.getComapanyName());
        System.out.println("Payment Terms: " + supplier.getPaymentTerms());

        System.out.println("Bank Details");
        System.out.println("Bank Name: " + supplier.getBankName());
        System.out.println("Bank Account Number: " + supplier.getBankAccountNumber());
        System.out.println("Bank IFSC: " + supplier.getIfscCode());

        System.out.println("Due Date: " + in1.getDueDate());
        System.out.println("Amount Due: " + in1.getAmount());

        System.out.println("Do you want to pay (y/n)");
        char ans = sc.next().charAt(0);
        if (ans == 'y') {
            System.out.println("Payment Under Process");
            Invoice.invoiceStatus = true;
        }
        else
        	Invoice.invoiceStatus = false;

        AccountManager.receiveNotification();
    }
}
