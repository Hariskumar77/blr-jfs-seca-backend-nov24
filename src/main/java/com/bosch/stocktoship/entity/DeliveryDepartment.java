package com.bosch.stocktoship.entity;

public class DeliveryDepartment {
	/*
	 * AUTHOR: DAFEDAR MUJAHID
	 */

	int departmentId;
	String departmentName;
	String departmentIncharge;

	public DeliveryDepartment(int departmentId, String departmentName, String departmentIncharge) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.departmentIncharge = departmentIncharge;
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
