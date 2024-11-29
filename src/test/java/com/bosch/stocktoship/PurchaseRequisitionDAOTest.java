
package com.bosch.stocktoship;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.RequisitionItem;
import com.bosch.stocktoship.repository.DBConnection;
import com.bosch.stocktoship.service.PurchaseRequisitioinDAO;


/**
 * 
 * @author Shubham Singh Tomer - (NGI2COB)
 * 
 * **/

public class PurchaseRequisitionDAOTest {
	
	private PurchaseRequisitioinDAO prDAO;
	private RequisitionItem pr;
	
//	Test 1: Testing for the getetPurchaseRequisitionItem() method,
	@Test
	public void testGetPurchaseRequisitionItem() throws ClassNotFoundException, SQLException {
		prDAO = new PurchaseRequisitioinDAO();
		
//		case of exception where the pr_number does not exist in the database
		
		pr = prDAO.getPurchaseRequisitionItem(99999);
//		the object should be null test
		assertNull(pr);
		
//		case of normal flow where the pr_number already exists and methods returns the object
		
		pr = prDAO.getPurchaseRequisitionItem(10031);
		assertNotNull(pr);
		assertEquals("IT001", pr.getItemCode());
		assertEquals(10031, pr.getPr_number());
		assertEquals("Crafting", pr.getPurposeDescription());
		assertEquals(20, pr.getQuantity());
		assertEquals("kg", pr.getUnit());
		assertEquals("Wood", pr.getDepartment());
		assertEquals("Joshi Woods", pr.getCompanyMake());
		assertEquals("Approved", pr.getApprovalStatus());

	}
	
	
//	Test 2: Testing for the insertion of data in the purchase_requisition table
//			Also checking the insertion of data and generation of pr_number
	@Test
	public void testCreatePurchaseRequisitionAndGetPrNo() {
		prDAO = new PurchaseRequisitioinDAO();
		pr = new RequisitionItem("IT001", "Carpanting", 5,"kg","Woodwork","Shubh Woods");
		try (Connection connection = DBConnection.getConnection()){
//			Generating pr_number
			String temp = prDAO.createPurchaseRequisitionAndGetPrNo(pr);
			assertNotNull(pr);
			
//			Comparing the inserted and generated data in the purchase_requisition
			RequisitionItem pr1 = prDAO.getPurchaseRequisitionItem(Integer.parseInt(temp));
			assertEquals(pr.getItemCode(), pr1.getItemCode());
			assertEquals(Integer.parseInt(temp), pr1.getPr_number());
			assertEquals(pr.getPurposeDescription(), pr1.getPurposeDescription());
			assertEquals(pr.getQuantity(), pr1.getQuantity());
			assertEquals(pr.getUnit(), pr1.getUnit());
			assertEquals(pr.getDepartment(), pr1.getDepartment());
			assertEquals(pr.getCompanyMake(), pr1.getCompanyMake());
			assertEquals(pr.getApprovalStatus(), pr1.getApprovalStatus());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
