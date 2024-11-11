package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.InventoryRequistionForm;
import com.bosch.stocktoship.service.InventoryRequisitionFormService;
 /**
  * @author XNE2KOR
  */
public class InventoryRequisitionFormServiceTest {
 
    private InventoryRequistionForm inventoryRequisitionForm;
    private InventoryRequisitionFormService inventoryRequisitionFormService;
 
    @BeforeEach
    public void setUp() {
        // Reset the static serialNo before each test to ensure it starts from 1 each time
        // Initialize a new instance of InventoryRequistionForm before each test
        inventoryRequisitionForm = new InventoryRequistionForm();
        inventoryRequisitionFormService=new InventoryRequisitionFormService();
    }
 
    @Test
    public void testToString() {
        // Set values to the form object
    	inventoryRequisitionForm.setNum(1);
    	inventoryRequisitionForm.setRequisitionerDepartment("IT Department");
        inventoryRequisitionForm.setUniqueItemCode("ITEM123");
        inventoryRequisitionForm.setItemDescription("Laptop");
        inventoryRequisitionForm.setQuantity(10);
        inventoryRequisitionForm.setUnitOfMeasure("pieces");
        inventoryRequisitionForm.setPurposeOfRequisitions("Hardware upgrade");
        inventoryRequisitionForm.setDeliveryDepartment("Tech Department");
 
        // Use SimpleDateFormat to format the date strings
        SimpleDateFormat format = new SimpleDateFormat("dd.mm.yy");
        try {
            inventoryRequisitionForm.setDateOfRequisition(format.parse("01.01.23"));
            inventoryRequisitionForm.setMaterialRequiredDate(format.parse("10.01.23"));
        } catch (Exception e) {
            e.printStackTrace();
        }
 
        // Generate the expected output string using the same format as the toString method
        String expectedOutput = String.format("%-12s%-13s%-17s%-13s%-17s%-13s%-18s%-18s%-15s%-15s",
                1, "IT Department", "01.01.2023", "ITEM123", "Laptop", 10, "pieces", "Hardware upgrade", "Tech Department", "10.01.2023");
        String actualOutput = inventoryRequisitionForm.toString();
        // Test if the toString method produces the expected output
        assertEquals(expectedOutput, actualOutput);
    }

    /**
     * test method for checking manageIRF is working properly
     */
    @Test
    public void testManageIRF() {
    	try {
			inventoryRequisitionFormService.manageIRF();
			assertEquals(1, InventoryRequisitionFormService.inventoryRequisitionFormList.size());
		} catch (Exception e) {
			fail("Exception occured whilerunning");
			e.printStackTrace();
		}
    }
}
