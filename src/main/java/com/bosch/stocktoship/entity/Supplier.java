package com.bosch.stocktoship.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="supplier")
public class Supplier {
 
    // Supplier details
	@Id
    private long supplierId;
    private String supplierName;
    private String comapanyName;
    private String paymentTerms;
    private String city;
    private String bankName;
    private long bankAccountNumber; 
    private String ifscCode;
    
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getComapanyName() {
		return comapanyName;
	}
	public void setComapanyName(String comapanyName) {
		this.comapanyName = comapanyName;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public long getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(long bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}
	public String getIfscCode() {
		return ifscCode;
	}
	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	@Override
	public String toString() {
		return "Supplier [supplierId=" + supplierId + ", supplierName=" + supplierName + ", comapanyName="
				+ comapanyName + ", paymentTerms=" + paymentTerms + ", city=" + city + ", bankName=" + bankName
				+ ", bankAccountNumber=" + bankAccountNumber + ", ifscCode=" + ifscCode + "]";
	}
    
    
	
    
}