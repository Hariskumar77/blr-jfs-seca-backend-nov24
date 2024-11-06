package com.bosch.stocktoship.entity;


/**
 * @author LDO1COB Soundarya 
 * */
public class Supplier {

	private String supplierCode;
    private String supplierName;
    private String supplierCity;
    private String companyName;
    private String paymentTerms;
    private String bankDetails;
    

	public Supplier(String supplierCode, String supplierName, String supplierCity, String companyName,
			String paymentTerms, String bankDetails) {
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.supplierCity = supplierCity;
		this.companyName = companyName;
		this.paymentTerms = paymentTerms;
		this.bankDetails = bankDetails;
		
	}
    
    
	public String getSupplierCode() {
		return supplierCode;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public String getSupplierCity() {
		return supplierCity;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public String getBankDetails() {
		return bankDetails;
	}
    
	@Override
	public String toString() {
		return supplierName;
	}
}
