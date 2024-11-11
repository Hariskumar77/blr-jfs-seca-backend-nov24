package com.bosch.stocktoship.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * @author WIV1COB
 */
public class InventoryRequistionForm {

	public InventoryRequistionForm() {
	}
	
	static int serialNo = 0;
	private int num;
	private String requisitionerDepartment;
	private Date dateOfRequisition;
	private String uniqueItemCode;
	private String itemDescription;
	private int quantity;
	private String unitOfMeasure;
	private String purposeOfRequisitions;
	private String deliveryDepartment;
	private Date materialRequiredDate;
	private String notesAndComments;
	private boolean status;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getRequisitionerDepartment() {
		return requisitionerDepartment;
	}

	public void setRequisitionerDepartment(String requisitionerDepartment) {
		this.requisitionerDepartment = requisitionerDepartment;
	}

	public Date getDateOfRequisition() {
		return dateOfRequisition;
	}

	public void setDateOfRequisition(Date dateOfRequisition) {
		this.dateOfRequisition = dateOfRequisition;
	}

	public String getUniqueItemCode() {
		return uniqueItemCode;
	}

	public void setUniqueItemCode(String uniqueItemCode) {
		this.uniqueItemCode = uniqueItemCode;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}

	public String getPurposeOfRequisitions() {
		return purposeOfRequisitions;
	}

	public void setPurposeOfRequisitions(String purposeOfRequisitions) {
		this.purposeOfRequisitions = purposeOfRequisitions;
	}

	public String getDeliveryDepartment() {
		return deliveryDepartment;
	}

	public void setDeliveryDepartment(String deliveryDepartment) {
		this.deliveryDepartment = deliveryDepartment;
	}

	public Date getMaterialRequiredDate() {
		return materialRequiredDate;
	}

	public void setMaterialRequiredDate(Date materialRequiredDate) {
		this.materialRequiredDate = materialRequiredDate;
	}

	public String getNotesAndComments() {
		return notesAndComments;
	}

	public void setNotesAndComments(String notesAndComments) {
		this.notesAndComments = notesAndComments;
	}

	public InventoryRequistionForm(String requisitionerDepartment, Date dateOfRequisition, String uniqueItemCode,
			String itemDescription, int quantity, String unitOfMeasure, String purposeOfRequisitions,
			String deliveryDepartment, Date materialRequiredDate, String notesAndComments, boolean status) {
		super();
		serialNo++;
		this.num = serialNo;
		this.requisitionerDepartment = requisitionerDepartment;
		this.dateOfRequisition = dateOfRequisition;
		this.uniqueItemCode = uniqueItemCode;
		this.itemDescription = itemDescription;
		this.quantity = quantity;
		this.unitOfMeasure = unitOfMeasure;
		this.purposeOfRequisitions = purposeOfRequisitions;
		this.deliveryDepartment = deliveryDepartment;
		this.materialRequiredDate = materialRequiredDate;
		this.notesAndComments = notesAndComments;
		this.status = status;
	}

	@Override
	public String toString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy");
		formatter.setLenient(false);
		return String.format("%-12s%-13s%-17s%-13s%-17s%-13s%-18s%-18s%-15s%-15s", getNum(), getRequisitionerDepartment(),
				formatter.format(getDateOfRequisition()), getUniqueItemCode(), getItemDescription(), getQuantity(), getUnitOfMeasure(),
				getPurposeOfRequisitions(), getDeliveryDepartment(), formatter.format(getMaterialRequiredDate()));
	}

}
