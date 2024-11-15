package com.bosch.stocktoship.service;

/**
 * @author QEU3KOR Supriya
 * */

import java.sql.*;
import java.util.*;

import com.bosch.stocktoship.entity.RequisitionItem;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.repository.DBConnection;


public class PurchaseOrderDAO {

    // Fetching Purchase Requisition details based on PR number
    public RequisitionItem getPurchaseRequisitionDetails(int prNo) throws SQLException, ClassNotFoundException {
        String sql = "SELECT pr.item_code, pr.purpose_description, pr.quantity, pr.unit, pr.department, pr.company_make, " +
                     "pr.approval_status, " +  
                     "i.name AS item_name, i.description AS item_description " +
                     "FROM purchase_requisition pr " +
                     "JOIN items i ON pr.item_code = i.item_code " +
                     "WHERE pr.pr_number = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql)) {
            
            pStatement.setInt(1, prNo);
            
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new RequisitionItem(
                            prNo,  // PR number passed
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
        }
        return null;
    }

    // Fetching Supplier details based on Supplier ID
    public Supplier getSupplierDetails(String supplierCode) throws SQLException, ClassNotFoundException {
        String sql = "SELECT supplier_name, supplier_city, company_name, payment_terms, " +
                     "supplier_bank_name, supplier_IFSCcode, supplier_AccNo " +
                     "FROM supplier WHERE supplier_code = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql)) {
            
            pStatement.setString(1, supplierCode);
            
            try (ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Supplier(
                            supplierCode,
                            resultSet.getString("supplier_name"),
                            resultSet.getString("supplier_city"),
                            resultSet.getString("company_name"),
                            resultSet.getString("payment_terms"),
                            resultSet.getString("supplier_bank_name"),
                            resultSet.getString("supplier_IFSCcode"),
                            resultSet.getLong("supplier_AccNo")
                    );
                }
            }
        }
        return null;
    }

    // Fetching all Supplier Codes
    public List<String> getSupplierCodes() throws SQLException, ClassNotFoundException {
        List<String> supplierCodes = new ArrayList<>();
        String sql = "SELECT supplier_code FROM supplier";  // SQL query to fetch all supplier codes

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = pStatement.executeQuery()) {
                // Iterate through the result set and add supplier codes to the list
                while (resultSet.next()) {
                    supplierCodes.add(resultSet.getString("supplier_code"));
                }
            }
        }
        return supplierCodes;  // Return the list of supplier codes
    }

    // Get the next Purchase Order number (using a sequence in the database)
    public int getNextPurchaseOrderNumber() throws SQLException, ClassNotFoundException {
        String sql = "SELECT purchase_order_seq.NEXTVAL FROM dual";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql);
             ResultSet resultSet = pStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return -1;  // Return -1 in case of failure to fetch the next PO number
    }

    // Insert the Purchase Order record into the database
    public boolean insertPurchaseOrder(int purchaseOrderNumber, String purchaseReqNo, String supplierCode) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO purchase_order (purchase_order_number, pr_number, supplier_code) " +
                     "VALUES (?, ?, ?)";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql)) {
            
            pStatement.setInt(1, purchaseOrderNumber);
            pStatement.setString(2, purchaseReqNo);
            pStatement.setString(3, supplierCode);
            
            return pStatement.executeUpdate() > 0;  // Return true if record is inserted successfully
        }
    }
}