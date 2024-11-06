package com.bosch.stocktoship.entity;

/***
* Represents a Robot assigned to handle a product with a specific product code.
* AUTHOR: CHARUL SAINI(SIC2COB)
***/
public class Robot {
 
	// Code for the product the robot is assigned to
	private String code;
 
	// Constructor initializes the robot with a product code
	public Robot(String code) {
		this.code = code;
	}
 
	// Displays the assignment status of the robot for the given product code
	public void display() {
		System.out.println("Robot Assigned for Product Code: " + code);
		System.out.println("Status: " + true);
	}
}