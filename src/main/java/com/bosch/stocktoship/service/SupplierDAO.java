package com.bosch.stocktoship.service;

// Created by AL AMAN - QLM2KOR - Sprint 2 (squad-1) 13/1/2024
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bosch.stocktoship.entity.Supplier;
public class SupplierDAO {

    // Database connection parameters
    private static final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Change with your DB URL
    private static final String JDBC_USERNAME = "system"; // Change with your DB username
    private static final String JDBC_PASSWORD = "6969"; // Change with your DB password
    
    // Create a new Supplier in the database
    public boolean createSupplier(Supplier supplier) {
        String sql = "INSERT INTO Supplier (supplier_id, supplier_name, company_name, payment_terms,city, bank_name, bank_account_number, ifsc_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, supplier.getSupplierId());
            pstmt.setString(2, supplier.getSupplierName());
            pstmt.setString(3, supplier.getComapanyName());
            pstmt.setString(4, supplier.getPaymentTerms());
            pstmt.setString(5, supplier.getCity());
            pstmt.setString(6, supplier.getBankName());
            pstmt.setLong(7, supplier.getBankAccountNumber());
            pstmt.setString(8, supplier.getIfscCode());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was inserted       
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Retrieve a Supplier by ID

    public Supplier getSupplierById(String id) {
        String sql = "SELECT * FROM Supplier WHERE supplier_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToSupplier(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if no supplier is found
    }

    // Retrieve all Suppliers

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    
    // Update Supplier details

    public boolean updateSupplier(Supplier supplier,String id) {

        String sql = "UPDATE Supplier SET supplier_id = ?,supplier_name = ?, company_name = ?, payment_terms = ?,city = ?, bank_name = ?, bank_account_number = ?, ifsc_code = ? WHERE supplier_id = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, supplier.getSupplierId());
            pstmt.setString(2, supplier.getSupplierName());
            pstmt.setString(3, supplier.getComapanyName());
            pstmt.setString(4, supplier.getPaymentTerms());
            pstmt.setString(5, supplier.getCity());
            pstmt.setString(6, supplier.getBankName());
            pstmt.setLong(7, supplier.getBankAccountNumber());
            pstmt.setString(8, supplier.getIfscCode());
            
            pstmt.setString(9, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was updated
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete Supplier by ID

    public boolean deleteSupplier(String id) {
        String sql = "DELETE FROM Supplier WHERE supplier_id = ?";
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to map ResultSet to Supplier object

    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        String id = rs.getString("supplier_id");
        String supplierName = rs.getString("supplier_name");
        String companyName = rs.getString("company_name");
        String paymentTerms = rs.getString("payment_terms");
        String city = rs.getString("city");
        String bankName = rs.getString("bank_name");
        Long bankAccountNumber = rs.getLong("bank_account_number");
        String ifscCode = rs.getString("ifsc_code");

        Supplier supplier = new Supplier();
        supplier.setSupplierId(id);
        supplier.setSupplierName(supplierName);
        supplier.setComapanyName(companyName);
        supplier.setPaymentTerms(paymentTerms);
        supplier.setBankName(bankName);
        supplier.setBankAccountNumber(bankAccountNumber);
        supplier.setIfscCode(ifscCode);
        supplier.setCity(city);
        return supplier;
    }
}