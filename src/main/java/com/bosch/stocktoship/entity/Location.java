package com.bosch.stocktoship.entity;

public class Location {
	/**
	 * @AUTHOR: CHARUL SAINI (SIC2COB)
	 */
	int id;
	int rack;
	int shelf;
	String productCode;
	int capacity;
	int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRack() {
		return rack;
	}

	public void setRack(int rack) {
		this.rack = rack;
	}

	public int getShelf() {
		return shelf;
	}

	public void setShelf(int shelf) {
		this.shelf = shelf;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Location(int rack, int shelf) {
		super();
		this.rack = rack;
		this.shelf = shelf;
	}

	public Location() {
		super();
	}

}
