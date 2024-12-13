package com.bosch.stocktoship.entity;

 
import jakarta.persistence.*;
 
//Author - Hariskumar S (ZEI1COB)


@Entity
@Table(name = "robot")
public class Robot {
	
	@Id
	 private int id;
	 private int capacity;
	 private String name;
	
	public Robot(int id, int capacity, String name) {
		this.id = id;
		this.capacity = capacity;
		this.name = name;
	}
	
	public Robot() {
		
	}
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public int getCapacity() {
		return capacity;
	}
 
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
	
 
}