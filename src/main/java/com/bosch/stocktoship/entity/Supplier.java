package com.bosch.stocktoship.entity;

import java.util.Date;

public class Supplier {

	//

	// Supplier values modified by AL AMAN(sprint 2) 13/11/2024
	private String supplierId;
	private String supplierName;
	private String comapanyName;
	private String paymentTerms;
	private String city;
	private String bankName;
	private long bankAccountNumber;
	private String ifscCode;

	// no argument constructor modified by AL AMAN(sprint 2) 13/11/2024
	public Supplier() {
		this.supplierId = "SD12";
		this.supplierName = "Suresh";
		this.comapanyName = "JK Trader";
		this.paymentTerms = "PDC";
		this.city = "Bangalore";
		this.bankName = "HDFC";
		this.bankAccountNumber = 698635373;
		this.ifscCode = "HDFC00098";
	}

	// parameterized constructor modified by AL AMAN(sprint 2) 13/11/2024
	public Supplier(String sup_id, String sup_name, String company_name, String terms, String city, String bank_name,
			long ac_number, String ifsc) {
		this.supplierId = sup_id;
		this.supplierName = sup_name;
		this.comapanyName = company_name;
		this.paymentTerms = terms;
		this.city = city;
		this.bankName = bank_name;
		this.bankAccountNumber = ac_number;
		this.ifscCode = ifsc;
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