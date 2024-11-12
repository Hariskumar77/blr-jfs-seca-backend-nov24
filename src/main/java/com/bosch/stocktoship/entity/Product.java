package com.bosch.stocktoship.entity;
import java.util.List;
/***
* Represents a Product with various details including dimensions, supplier info,
* and assigned storage locations.
* AUTHOR: AYUSH ARYA(YYA2COB)
***/
public class Product {
	// Product attributes including dimensions, supplier information, and storage details
	private String name, code, batch, supplierName, location, orderNo, poDate, deliveryDate;
	private String length, breadth, height, weight, quantity;
	private List<Location> locationList;  // List of storage locations for the product
	// Default constructor
	public Product() {
	}
	// Constructor to initialize product attributes
	public Product(String name, String code, String batch, String length, String breadth, String height, String weight,
			String quantity, String supplierName, String location, String orderNo, String poDate, String deliveryDate) {
		this.name = name;
		this.code = code;
		this.batch = batch;
		this.length = length;
		this.breadth = breadth;
		this.height = height;
		this.weight = weight;
		this.quantity = quantity;
		this.supplierName = supplierName;
		this.location = location;
		this.orderNo = orderNo;
		this.poDate = poDate;
		this.deliveryDate = deliveryDate;
	}
	// Getters and setters for each attribute
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getBreadth() {
		return breadth;
	}
	public void setBreadth(String breadth) {
		this.breadth = breadth;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public List<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}
}