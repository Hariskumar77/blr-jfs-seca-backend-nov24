package com.bosch.stocktoship.entity;

import jakarta.persistence.*;

//Author - Gurpartap Singh (INU2COB)
//Author - Hariskumar S (ZEI1COB)


@Entity
@Table(name = "location")
public class Location {
	
	@Id
	private int id;
	private int rack;
	private int shelf;
	@ManyToOne
    @JoinColumn(name = "productCode", nullable = true)
	private Product product;
	private int capacity;
	private int quantity;
	private boolean isOccupied;
	
 
	
	public boolean isOccupied() {
		return isOccupied;
	}

	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public Location(int id, int rack, int shelf, Product product, int capacity, int quantity) {
		this.id = id;
		this.rack = rack;
		this.shelf = shelf;
		this.product = product;
		this.capacity = capacity;
		this.quantity = quantity;
		this.isOccupied = true;
	}
 
	public Location() {
		
	}
 
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
 
	public Product getProductCode() {
		return product;
	}
 
	public void setProductCode(Product product) {
		this.product = product;
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

	@Override
	public String toString() {
		return "Location [id=" + id + ", rack=" + rack + ", shelf=" + shelf + ", product=" + product + ", capacity="
				+ capacity + ", quantity=" + quantity + "]";
	}
	
	
	
	
}