package com.bosch.stocktoship.entity;

/**
 *  
 *  @author Varun Joshi VJO3KOR
 * 
 * */

public class RequisitionItem {
    public String itemCode;
    public String purposeDescription;
    public int quantity;
    public String unit;
    public String department;
    public String companyMake;
    final int maxOrderQty=50;
    public String approvalStatus="Need Approval";

    public RequisitionItem() {
	
	}
    public RequisitionItem(String itemCode, String purposeDescription, int quantity, String unit, String department, String companyMake) {
        this.itemCode = itemCode;
        this.purposeDescription = purposeDescription;
        this.quantity = quantity;
        this.unit = unit;
        this.department = department;
        this.companyMake = companyMake;
        this.approvalStatus = (quantity <= maxOrderQty) ? "Approved" : "Need Approval";
    }

    //Getter Setter Methods
    public String getItemCode() {
		return itemCode;
	}


	public String getPurposeDescription() {
		return purposeDescription;
	}


	public int getQuantity() {
		return quantity;
	}


	public String getUnit() {
		return unit;
	}


	public String getDepartment() {
		return department;
	}


	public String getCompanyMake() {
		return companyMake;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}


	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}


	public void setPurposeDescription(String purposeDescription) {
		this.purposeDescription = purposeDescription;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public void setCompanyMake(String companyMake) {
		this.companyMake = companyMake;
	}


	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	// Updating Approval Status

	public void updateApprovalStatus() {
        if (quantity <= maxOrderQty) {
            this.approvalStatus = "Approved";
        } else {
            this.approvalStatus = "Need Approval";
        }
    }

    @Override
    public String toString() {
        return "Item Code: " + itemCode + ", Purpose: " + purposeDescription + ", Quantity: " + quantity + unit +
                ", Department: " + department + ", Company Make: " + companyMake + ", Approval Status: " + approvalStatus;
    }
}