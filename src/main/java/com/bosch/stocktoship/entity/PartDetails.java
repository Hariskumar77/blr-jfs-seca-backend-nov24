package com.bosch.stocktoship.entity;

public class PartDetails {
	private int partNumber;
	private String partName;
	private String partDescription;

	public PartDetails(int partNumber, String partName, String partDescription) {
		super();
		this.partNumber = partNumber;
		this.partName = partName;
		this.partDescription = partDescription;
	}

	public PartDetails() {
		// TODO Auto-generated constructor stub
	}

	public PartDetails(String partName, String partDescription) {
		super();
		this.partName = partName;
		this.partDescription = partDescription;
	}

	public PartDetails(int partNumber) {
		this.partNumber = partNumber;
 	}

	public int getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(int partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

}
