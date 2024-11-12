//Created by AKASH HEGDE P on 4th November Squad 1

package com.bosch.stocktoship.entity;

import java.util.Date;



public class Supplier {

    // Supplier details
    private String supplierId = "SD12";
    private String supplierName = "Suresh";
    private String comapanyName = "JK Trader";
    private String paymentTerms = "PDC";
    private String city = "Bangalore";
    private String bankName = "HDFC";
    private long bankAccountNumber = 698635373;
    private String ifscCode = "HDFC00098";

    public Supplier() {
    	
    }
    
    public Supplier(String supplierId, String supplierName, String comapanyName, String paymentTerms, String city,
			String bankName, long bankAccountNumber, String ifscCode) {
		super();
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.comapanyName = comapanyName;
		this.paymentTerms = paymentTerms;
		this.city = city;
		this.bankName = bankName;
		this.bankAccountNumber = bankAccountNumber;
		this.ifscCode = ifscCode;
	}

	// Getter and Setter for paymentTerms
    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    // Getter and Setter for companyName
    public String getComapanyName() {
        return comapanyName;
    }

    public void setComapanyName(String comapanyName) {
        this.comapanyName = comapanyName;
    }

    // Getter and Setter for supplierId
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    // Getter and Setter for supplierName
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    // Getter and Setter for city
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // Getter and Setter for bankName
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    // Getter and Setter for bankAccountNumber
    public long getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    // Getter and Setter for ifscCode
    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    // Predefined invoice details for this supplier
    Date invDate = new Date(); // Current date as invoice date
    Date due = new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000); // Due date set 7 days after invoice date
    long invNum = 1234; // Example invoice number
    double amt = 100000.00; // Example amount
    long poNum = 3000; // Example purchase order number

    // Method to create and generate a new Invoice for this Supplier
    public void callInvoice() {
        Invoice inv = new Invoice(invNum, invDate, amt, due, poNum, false);
        Invoice generatedInvoice = inv.generateInvoice(); // Adds the invoice to the list of generated invoices
    }
}
