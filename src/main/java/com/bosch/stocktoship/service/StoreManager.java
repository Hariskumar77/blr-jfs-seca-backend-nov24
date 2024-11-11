//Created by AKASH HEGDE P on 4th November Squad 1

package com.bosch.stocktoship.service;

import java.util.Scanner;

import com.bosch.stocktoship.entity.Invoice;

public class StoreManager {
    
    // Static initializer block, runs once when the class is loaded
    static {
        System.out.println();
        System.out.println("Inside Store Manager Class");
    }

    // Custom exception to handle cases when an invoice is not found
    public class InvoiceNotFoundException extends Exception {
        public InvoiceNotFoundException(String message) {
            super(message);
        }
    }

    // Method to select an invoice based on the PO number entered by the user
    public Invoice selectInvoice() {
        Invoice foundInvoice = null;
        while (foundInvoice == null) {
            try {
                System.out.println("Enter PO Number");
                Scanner sc = new Scanner(System.in);
                long poNumber = sc.nextLong();
                
                // Loop through the generated invoices to find a match
                for (Invoice invoice : Invoice.generatedInvoices) {
                    System.out.println("Checking Invoice with PO Number "); 
                    if (invoice.getPoNumber() == poNumber) {
                        System.out.println("Invoice found");
                        return invoice;
                    }
                }
                
                // If no matching invoice is found, throw an exception
                if (foundInvoice == null) {
                    throw new InvoiceNotFoundException("No matching invoice found for PO Number: " + poNumber);
                }

            } catch (InvoiceNotFoundException e) {
                // Handle the custom exception and prompt the user to enter a valid PO number
                System.out.println(e.getMessage() + " Please enter a valid PO number.");
            }
        }
        return foundInvoice;
    }

    // Method to display details of a selected invoice
    public void displayInvoice() {
        try {
            Invoice invoice = selectInvoice();
            System.out.println("Invoice Number: " + invoice.getInvoiceNumber());
            System.out.println("Invoice Date: " + invoice.getInvoiceDate());
            System.out.println("Amount: " + invoice.getAmount());
            System.out.println("Due Date: " + invoice.getDueDate());
            System.out.println("PO Number: " + invoice.getPoNumber());
            System.out.println("Invoice Status: " + invoice.getInvoiceStatus());
        } catch (Exception e) {
            System.out.println(e.getMessage()); 
        }
    }

    // Method to check for approval, based on user input
    public void checkForApproval() {
        Character convinced = null;
        Scanner sc = new Scanner(System.in);
        System.out.println("Are you convinced (y/n)");
        convinced = sc.next().charAt(0);

        if (convinced == 'y') {
            approveInvoice();
        } else if (convinced == 'n') {
            rejectInvoice();
        }
    }

    // Method to approve an invoice
    public void approveInvoice() {
        System.out.println("Approved by Store Manager");
        submit();
    }

    // Method to reject an invoice and set invoice status to false
    public void rejectInvoice() {
        System.out.println("Rejected by Store Manager");
        Invoice.invoiceStatus = false;
    }

    // Method to submit the approved invoice and notify the accountant
    public void submit() {
        Invoice.invoiceStatus = true;
        System.out.println("Sending Notification of Approval to Accountant");
        Accountant.notificationByStoreManager();
    }
}
