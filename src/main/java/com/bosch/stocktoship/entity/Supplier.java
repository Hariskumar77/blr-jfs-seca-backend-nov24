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
    private String supplierBankName;
    private String supplierIFSCCode;
    private long supplierAccNo;

    // Constructor
    public Supplier(String supplierCode, String supplierName, String supplierCity, 
                           String companyName, String paymentTerms, String supplierBankName, 
                           String supplierIFSCCode, long supplierAccNo) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
        this.supplierCity = supplierCity;
        this.companyName = companyName;
        this.paymentTerms = paymentTerms;
        this.supplierBankName = supplierBankName;
        this.supplierIFSCCode = supplierIFSCCode;
        this.supplierAccNo = supplierAccNo;
    }

    // Getters and Setters
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCity() {
        return supplierCity;
    }

    public void setSupplierCity(String supplierCity) {
        this.supplierCity = supplierCity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getSupplierBankName() {
        return supplierBankName;
    }

    public void setSupplierBankName(String supplierBankName) {
        this.supplierBankName = supplierBankName;
    }

    public String getSupplierIFSCCode() {
        return supplierIFSCCode;
    }

    public void setSupplierIFSCCode(String supplierIFSCCode) {
        this.supplierIFSCCode = supplierIFSCCode;
    }

    public long getSupplierAccNo() {
        return supplierAccNo;
    }

    public void setSupplierAccNo(long supplierAccNo) {
        this.supplierAccNo = supplierAccNo;
    }

    @Override
    public String toString() {
        return "SupplierDetails{" +
               "supplierCode='" + supplierCode + '\'' +
               ", supplierName='" + supplierName + '\'' +
               '}';
    }
}