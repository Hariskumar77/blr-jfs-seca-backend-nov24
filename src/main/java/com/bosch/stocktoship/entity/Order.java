package com.bosch.stocktoship.entity;

import java.util.Date;

import jakarta.persistence.*;
 
//Author - Gurpartap Singh (INU2COB)


@Entity
@Table(name="orders")
public class Order {
 
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE.UUID)
    private int id;
    private int productCode;
    private String name;
    private String supplierName;
    private String supplierLocation;
    private Integer batch;
    private Integer length;
    private Integer breadth;
    private Integer height;
    private Integer weight;
    private Integer quantity;
    private Integer orderNo;
    private Integer volume;
 
    @Temporal(TemporalType.DATE)
    private Date issueDate;
    
    
 
	public Order(int productCode, String name, String supplierName, String supplierLocation, Integer batch,
			Integer length, Integer breadth, Integer height, Integer weight, Integer quantity, Integer orderNo,
			Integer volume, Date deliveryDate, Date issueDate) {
		super();
		this.productCode = productCode;
		this.name = name;
		this.supplierName = supplierName;
		this.supplierLocation = supplierLocation;
		this.batch = batch;
		this.length = length;
		this.breadth = breadth;
		this.height = height;
		this.weight = weight;
		this.quantity = quantity;
		this.orderNo = orderNo;
		this.volume = volume;
		this.issueDate = issueDate;
	}
 
	public Order() {
		
	}
 
	public int getProductCode() {
		return productCode;
	}
 
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getSupplierName() {
		return supplierName;
	}
 
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
 
	@Override
	public String toString() {
		return "Order [id=" + id + ", productCode=" + productCode + ", name=" + name + ", supplierName=" + supplierName
				+ ", supplierLocation=" + supplierLocation + ", batch=" + batch + ", length=" + length + ", breadth="
				+ breadth + ", height=" + height + ", weight=" + weight + ", quantity=" + quantity + ", orderNo="
				+ orderNo + ", volume=" + volume + ", issueDate=" + issueDate + "]";
	}

	public String getSupplierLocation() {
		return supplierLocation;
	}
 
	public void setSupplierLocation(String supplierLocation) {
		this.supplierLocation = supplierLocation;
	}
 
	public Integer getBatch() {
		return batch;
	}
 
	public void setBatch(Integer batch) {
		this.batch = batch;
	}
 
	public Integer getLength() {
		return length;
	}
 
	public void setLength(Integer length) {
		this.length = length;
	}
 
	public Integer getBreadth() {
		return breadth;
	}
 
	public void setBreadth(Integer breadth) {
		this.breadth = breadth;
	}
 
	public Integer getHeight() {
		return height;
	}
 
	public void setHeight(Integer height) {
		this.height = height;
	}
 
	public Integer getWeight() {
		return weight;
	}
 
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
 
	public Integer getQuantity() {
		return quantity;
	}
 
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
 
	public Integer getOrderNo() {
		return orderNo;
	}
 
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
 
	public Integer getVolume() {
		return volume;
	}
 
	public void setVolume(Integer volume) {
		this.volume = volume;
	}
 
	public Date getIssueDate() {
		return issueDate;
	}
 
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
    
    
}
