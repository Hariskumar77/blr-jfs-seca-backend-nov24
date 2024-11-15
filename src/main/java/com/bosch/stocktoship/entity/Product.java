package com.bosch.stocktoship.entity;

import java.util.Date;

public class Product {
	/**
	 * @AUTHOR: CHARUL SAINI (SIC2COB)
	 */

	String name, supplierName, supplierLocation;
	String code;
	Integer batch;
	Integer length;
	Integer breadth;
	Integer height, weight, quantity, orderNo, volume;
	Date deliveryDate, issueDate;

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

	public String getSupplierLocation() {
		return supplierLocation;
	}

	public void setSupplierLocation(String supplierLocation) {
		this.supplierLocation = supplierLocation;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Product(String name, String supplierName, String supplierLocation, String code, Integer batch,
			Integer length, Integer breadth, Integer height, Integer weight, Integer quantity, Integer orderNo,
			Integer volume, Date deliveryDate, Date issueDate) {
		super();
		this.name = name;
		this.supplierName = supplierName;
		this.supplierLocation = supplierLocation;
		this.code = code;
		this.batch = batch;
		this.length = length;
		this.breadth = breadth;
		this.height = height;
		this.weight = weight;
		this.quantity = quantity;
		this.orderNo = orderNo;
		this.volume = volume;
		this.deliveryDate = deliveryDate;
		this.issueDate = issueDate;
	}

	public Product(String supplierName, String supplierLocation, Integer batch, Integer quantity, Integer orderNo,
			Date deliveryDate, Date issueDate) {
		super();
		this.supplierName = supplierName;
		this.supplierLocation = supplierLocation;
		this.batch = batch;
		this.quantity = quantity;
		this.orderNo = orderNo;
		this.deliveryDate = deliveryDate;
		this.issueDate = issueDate;
	}

	public Product() {
		// TODO Auto-generated constructor stub
	}

}