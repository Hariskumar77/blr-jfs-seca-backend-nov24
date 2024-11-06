package com.bosch.stocktoship.entity;

//Created by AKASH HEGDE P on 4th November Squad 1


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice { 

  // Instance variables for Invoice details
  private long invoiceNumber;
  private Date invoiceDate;
  private double amount;
  private Date dueDate;
  private long poNumber;

  // Static variable to track the invoice status across all invoices
  public static boolean invoiceStatus = false;

  // Static list to store all generated invoices
  public static List<Invoice> generatedInvoices = new ArrayList<>();

  // Constructor to initialize an Invoice object
  public Invoice(long invoiceNumber, Date invoiceDate, double amount, Date dueDate, long poNumber, boolean invoiceStatus) {
      this.invoiceNumber = invoiceNumber;
      this.invoiceDate = invoiceDate;
      this.amount = amount;
      this.dueDate = dueDate;
      this.poNumber = poNumber;
      this.invoiceStatus = invoiceStatus;
  }

  // Getter and Setter for invoiceNumber
  public long getInvoiceNumber() {
      return invoiceNumber;
  }

  public void setInvoiceNumber(long invoiceNumber) {
      this.invoiceNumber = invoiceNumber;
  }

  // Getter and Setter for invoiceDate
  public Date getInvoiceDate() {
      return invoiceDate;
  }

  public void setInvoiceDate(Date invoiceDate) {
      this.invoiceDate = invoiceDate;
  }

  // Getter and Setter for dueDate
  public Date getDueDate() {
      return dueDate;
  }

  public void setDueDate(Date dueDate) {
      this.dueDate = dueDate;
  }

  // Getter and Setter for amount
  public double getAmount() {
      return amount;
  }

  public void setAmount(double amount) {
      this.amount = amount;
  }

  // Getter and Setter for poNumber
  public long getPoNumber() {
      return poNumber;
  }

  public void setPoNumber(long poNumber) {
      this.poNumber = poNumber;
  }

  // Getter and Setter for invoiceStatus
  public boolean getInvoiceStatus() {
      return invoiceStatus;
  }

  public void setInvoiceStatus(boolean invoiceStatus) {
      this.invoiceStatus = invoiceStatus;
  }

  // Method to generate an invoice and add it to the list of generated invoices
  public Invoice generateInvoice() {
      System.out.println("Generating Invoice By Supplier");

      if (generatedInvoices.contains(this)) {
          System.out.println("This invoice is already in the list");
      } else {
          System.out.println("Adding new invoice to the list");
          generatedInvoices.add(this);
      }
      return this;
  }

  // Static method to get the list of generated invoices
  public static List<Invoice> getGeneratedInvoices() {
      return generatedInvoices;
  }
}
