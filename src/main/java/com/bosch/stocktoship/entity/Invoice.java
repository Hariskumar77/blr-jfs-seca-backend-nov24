package com.bosch.stocktoship.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@Entity
@Table(name="invoices")
public class Invoice {
 
	
    private long invoiceNumber;
    private Date invoiceDate;
    private double amount;
    private Date dueDate;
    @Id
    private long poNumber;
    private long supplierId;
    private boolean approvedByStoreManager;
    private boolean approvedByAccount;
    private boolean approvedByAccountManager;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplierId", insertable = false, updatable = false)
    private Supplier supplier;
    
	public long getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public Date getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public long getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(long poNumber) {
		this.poNumber = poNumber;
	}
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    

	@Override
	public String toString() {
		return "Invoice [invoiceNumber=" + invoiceNumber + ", invoiceDate=" + invoiceDate + ", amount=" + amount
				+ ", dueDate=" + dueDate + ", poNumber=" + poNumber + ", supplierId=" + supplierId
				+ ", approvedByStoreManager=" + approvedByStoreManager + ", approvedByAccount=" + approvedByAccount
				+ ", approvedByAccountManager=" + approvedByAccountManager + ", supplier=" + supplier + "]";
	}
	public boolean isApprovedByStoreManager() {
		return approvedByStoreManager;
	}
	public void setApprovedByStoreManager(boolean approvedByStoreManager) {
		this.approvedByStoreManager = approvedByStoreManager;
	}
	public boolean isApprovedByAccountManager() {
		return approvedByAccountManager;
	}
	public void setApprovedByAccountManager(boolean approvedByAccountManager) {
		this.approvedByAccountManager = approvedByAccountManager;
	}
	public boolean isApprovedByAccount() {
		return approvedByAccount;
	}
	public void setApprovedByAccount(boolean approvedByAccount) {
		this.approvedByAccount = approvedByAccount;
	}
 
 
}