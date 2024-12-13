package com.bosch.stocktoship.entity;


import jakarta.persistence.*;

//Author - Hariskumar S (ZEI1COB)


@Entity
@Table(name = "deliveryDepartment")
public class DeliveryDepartment {
	
	@Id
	private int departmentId;
	private String departmentName;
	private String departmentIncharge;
		
	public DeliveryDepartment(int departmentId, String departmentName, String departmentIncharge) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.departmentIncharge = departmentIncharge;
	}
	
	public DeliveryDepartment() {
	}
 
	public int getDepartmentId() {
		return departmentId;
	}
 
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
 
	public String getDepartmentName() {
		return departmentName;
	}
 
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
 
	public String getDepartmentIncharge() {
		return departmentIncharge;
	}
 
	public void setDepartmentIncharge(String departmentIncharge) {
		this.departmentIncharge = departmentIncharge;
	}
	
	
}