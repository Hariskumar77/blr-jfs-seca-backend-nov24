package com.bosch.stocktoship.entity;

public class Robot {
	/**
	 * @AUTHOR: CHARUL SAINI (SIC2COB)
	 * @AUTHOR: DAFEDAR MUJAHID
	 */
	int id, capacity;
	String name;
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
	public Robot(int id, int capacity, String name) {
		super();
		this.id = id;
		this.capacity = capacity;
		this.name = name;
	}
	
	

}
