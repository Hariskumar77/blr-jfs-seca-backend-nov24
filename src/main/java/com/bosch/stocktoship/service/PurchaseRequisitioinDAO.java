package com.bosch.stocktoship.service;

/**
 * 
 *  @author LDO1COB Soundarya 
 * 
 * */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bosch.stocktoship.entity.RequisitionItem;
import com.bosch.stocktoship.repository.DBConnection;

public class PurchaseRequisitioinDAO {

	// Creating a purchase requisition
	public String createPurchaseRequisitionAndGetPrNo(RequisitionItem pr) throws ClassNotFoundException, SQLException {
		
		String sql = "insert into purchase_requisition(item_code,purpose_description,quantity,unit,department,company_make,approval_status)"
				+ " values(?,?,?,?,?,?,?)";
		
		String prNo=null;
		try(Connection connection = DBConnection.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);){
			
			
			
			pStatement.setString(1, pr.getItemCode());
			pStatement.setString(2, pr.getPurposeDescription());
			pStatement.setInt(3,pr.getQuantity());
			pStatement.setString(4, pr.getUnit());
			pStatement.setString(5, pr.getDepartment());
			pStatement.setString(6, pr.getCompanyMake());
			pStatement.setString(7, pr.getApprovalStatus());
			
			 pStatement.executeUpdate();
			 
			 
			 String sqlForPRno = "select purchase_requisition_seq.CURRVAL from DUAL";
			 
			 Statement statement = connection.createStatement();
			 
			 ResultSet rSet = statement.executeQuery(sqlForPRno);
			 if(rSet.next())
			 {
				 prNo = rSet.getString(1);
			 }
			 
			 return prNo;
		}

	}
	
	
	
	// Retrieving item details through PR number
	public RequisitionItem getPurchaseRequisitionItem(int prNo) throws ClassNotFoundException, SQLException {
		
		String sql = "select * from purchase_requisition where pr_number = ?";
		
		try(Connection connection = DBConnection.getConnection();
				PreparedStatement pStatement = connection.prepareStatement(sql);){
			
			pStatement.setInt(1, prNo);
			
			ResultSet resultSet = pStatement.executeQuery();
			
			if(resultSet.next())
			{
				return new RequisitionItem(
						prNo,
						resultSet.getString("item_code"),
						resultSet.getString("purpose_description"),
						resultSet.getInt("quantity"),
						resultSet.getString("unit"),
						resultSet.getString("department"),
						resultSet.getString("company_make"),
						resultSet.getString("approval_status")
						);
			}
			
		}
		
		return null;
		
	}
	
	
	
}
