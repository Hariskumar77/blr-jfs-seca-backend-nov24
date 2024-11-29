package com.bosch.stocktoship.service;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import com.bosch.stocktoship.entity.InventoryRequistionForm;
import com.bosch.stocktoship.exception.InvalidItemCodeFormatExcpetion;
 
/**
* Service class for Managing Inventory Requisition Form. This class provides
* methods for creating and viewing the Inventory Requisition Form
*
* @author WIV1COB
*/
 
public class InventoryRequisitionFormService {
	
	
 
	public static List<InventoryRequistionForm> inventoryRequisitionFormList = new ArrayList<InventoryRequistionForm>();
	Map<Integer, List<InventoryRequistionForm>> IRAppriovalMap = new HashMap<Integer, List<InventoryRequistionForm>>();
 
	public List<InventoryRequistionForm> addItemsToIR() {
		return inventoryRequisitionFormList;
	}
	
	/**creating a object for InventoryRequisitionFormServiceDAO **/
	InventoryRequisitionFormServiceDAO IRFSDAO=new InventoryRequisitionFormServiceDAO ();
	
 
	/**
	 * This method accepts all the inputs from users and add it into
	 * inventoryRequisitionFormList. All the inputed inventory requisition form data
	 * will be available in this list.
	 *
	 * @param stream for taking input from user
	 */
	public void takeDataForInventoryRequistionForm(Scanner input) {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
		LocalDate inputDate;
		LocalDate currentDate = LocalDate.now();
		LocalDate fiveYearsFromNow = currentDate.plus(5, ChronoUnit.YEARS);
		formatDate.setLenient(false);
		InventoryRequistionForm inventoryRequistionForm = new InventoryRequistionForm();
		boolean check = true;
		while (check) {
			System.out.println("Enter Requisitioner Department");
			String requisitionerDepartment = input.nextLine();
			if (requisitionerDepartment.matches("[a-zA-Z0-9 ]*")) {
				check = false;
				inventoryRequistionForm.setRequisitionerDepartment(requisitionerDepartment);
			} else {
				System.out.println("Please enter a valid department name");
			}
		}
		check = true;
		while (check) {
			System.out.println("Enter Date of Requisition");
			String dateOfRequisition = input.nextLine();
			try {
				Date parse = formatDate.parse(dateOfRequisition);
				inputDate = LocalDate.parse(dateOfRequisition, formatter);
				if (!inputDate.isBefore(currentDate) && !inputDate.isAfter(fiveYearsFromNow)) {
					inventoryRequistionForm.setDateOfRequisition(parse);
					check = false;
				} else {
					System.out.println("Please enter a valid date between now and 5 years from now");
				}
			} catch (ParseException e) {
				System.out.println("Please check the date entered is correct. Expected date format is 'DD.MM.YYYY'");
			}
		}
		check = true;
		while (check) {
			try {
				System.out.println("Enter Unique Item code");
				String uniqueItemCode = input.nextLine().trim();
				if (uniqueItemCode.length() != 10 || !uniqueItemCode.matches("^[a-zA-Z0-9 ]+$")) {
					throw new InvalidItemCodeFormatExcpetion(
							"Item code should be alphanumeric and length should be 10");
				} else {
					check = false;
					inventoryRequistionForm.setUniqueItemCode(uniqueItemCode);
				}
			} catch (InvalidItemCodeFormatExcpetion e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("Enter Item Description");
		String itemDescription = input.nextLine();
		inventoryRequistionForm.setItemDescription(itemDescription);
		check = true;
		while (check) {
			try {
				System.out.println("Enter Quantity");
				String quantity = input.nextLine();
				inventoryRequistionForm.setQuantity(Integer.parseInt(quantity));
				check = false;
			} catch (Exception e) {
				System.out.println("Invalid quantity, quantity should be a number");
			}
		}
		check = true;
		while (check) {
			System.out.println("Enter Unit of measure");
			String unitOfMeasure = input.nextLine();
			if (unitOfMeasure.matches("[a-zA-Z0-9 ]*")) {
				check = false;
				inventoryRequistionForm.setUnitOfMeasure(unitOfMeasure);
			} else {
				System.out.println("Please make sure a valid unit of measure is provided");
			}
		}
		System.out.println("Enter Purpose of Requisitions");
		String purposeOfRequisitions = input.nextLine();
		inventoryRequistionForm.setPurposeOfRequisitions(purposeOfRequisitions);
		check = true;
		while (check) {
			System.out.println("Enter Delivery Department");
			String deliveryDepartment = input.nextLine();
			if (deliveryDepartment.matches("[a-zA-Z0-9 ]*")) {
				check = false;
				inventoryRequistionForm.setDeliveryDepartment(deliveryDepartment);
			} else {
				System.out.println("Please make sure a valid delivery department is provided");
			}
		}
		check = true;
		while (check) {
			try {
				System.out.println("Enter Material Required Date");
				String materialRequiredDate = input.nextLine();
				Date parse = formatDate.parse(materialRequiredDate);
				inputDate = LocalDate.parse(materialRequiredDate, formatter);
				if (!inputDate.isBefore(currentDate) && !inputDate.isAfter(fiveYearsFromNow)) {
					inventoryRequistionForm.setMaterialRequiredDate(parse);
					check = false;
				} else {
					System.out.println("Please enter a valid date between now and 5 years from now");
				}
			} catch (ParseException e) {
				System.out.println("Please check the date entered is correct. Expected date format is 'DD.MM.YY'");
			}
		}
		System.out.println("Enter Notes & Comments");
		String notesAndComments = input.nextLine();
		inventoryRequistionForm.setNotesAndComments(notesAndComments);
		inventoryRequisitionFormList.add(inventoryRequistionForm);
		IRFSDAO.saveInventoryRequisitionForm(inventoryRequistionForm,""); // Save to DB
	}
 
	/**
	 * This is the parent method which runs takeDataForInventoryRequistionForm and
	 * approveRequsitionForm internally and accepts all the validated requisition
	 * form. Also it adds this list into IRAppriovalMap where key is generated
	 * inventoryRequsitionNumber
	 */
	public void manageIRF(Scanner input) {
		while (true) {
			System.out.println("Enter the following details to raise Inventory Requisition");
			takeDataForInventoryRequistionForm(input);
			System.out.println("item added successfully");
			System.out.println("1. Add more items to IR\n2. Save");
			String s = input.nextLine();
			if (s.equals("1")) {
				continue;
			} else {
				int slNo = 1;
				System.out.printf("%-12s%-13s%-17s%-13s%-17s%-13s%-18s%-18s%-15s%-15s\n", "S no", "Req Dept",
						"Date of Req", "Item code", "Description", "Quantity", "Unit of measure", "Purpose of Req",
						"Delivery Dep", "Material Req Date");
				for (InventoryRequistionForm inventoryRequistionForm : inventoryRequisitionFormList) {
					System.out.printf("%-12s%-13s",slNo , inventoryRequistionForm.toString());
				}
				System.out.println("\n");
				System.out.println("1. Submit");
				input.nextLine();
				int inventoryRequsitionNumber;
				do {
					inventoryRequsitionNumber = 1000 + new Random().nextInt(9000);
				} while (IRAppriovalMap.get(inventoryRequsitionNumber) != null);
 
				IRAppriovalMap.put(inventoryRequsitionNumber, inventoryRequisitionFormList);
				System.out.println("Inventory Requisitions submitted successfully\n\nInventory Requisitions Number: "
						+ inventoryRequsitionNumber);
				break;
			}
		}
		approveRequsitionForm(input);
		input.close();
	}
	
	
 
 
	/**
	 * Method for approving the generated inventory requisition form
	 *
	 * @param stream for taking input from user
	 */
	public void approveRequsitionForm(Scanner input) {
		System.out.printf("%-13s%-17s%-13s%-17s%-13s%-18s%-18s%-15s%-15s\n", "Req Dept", "Date of Req",
				"Item code", "Description", "Quantity", "Unit of measure", "Purpose of Req", "Delivery Dep",
				"Material Req Date");
		for (InventoryRequistionForm form : inventoryRequisitionFormList) {
			System.out.println(form);
			System.out.println("Approval status : 1) Approve\t2) Reject");
			String line = input.nextLine();
			if (line.equals("1")) {
				form.setStatus(true);
				
	//			IRFSDAO.initializeTable("APPROVED");
				IRFSDAO.saveInventoryRequisitionForm(form,"APPROVED");
				
				
			} else {
				form.setStatus(false);
	//			IRFSDAO.initializeTable("REJECTED");
				IRFSDAO.saveInventoryRequisitionForm(form,"REJECTED");
				System.out.println("Form has been rejected");
			}
		}
		input.close();
	}
	
	
	
                       
	
}