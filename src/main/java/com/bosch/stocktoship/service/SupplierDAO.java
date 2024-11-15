package com.bosch.stocktoship.service;

//@author QEU3KOR Supriya

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.repository.DBConnection;

public class SupplierDAO {

    // Fetching all Supplier details from the database
    public List<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT supplier_code, supplier_name, supplier_city, company_name, " +
                     "payment_terms, supplier_bank_name, supplier_IFSCcode, supplier_AccNo " +
                     "FROM supplier";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql)) {
            
            ResultSet resultSet = pStatement.executeQuery();
            
            while (resultSet.next()) {
                Supplier supplier = new Supplier(
                        resultSet.getString("supplier_code"),
                        resultSet.getString("supplier_name"),
                        resultSet.getString("supplier_city"),
                        resultSet.getString("company_name"),
                        resultSet.getString("payment_terms"),
                        resultSet.getString("supplier_bank_name"),
                        resultSet.getString("supplier_IFSCcode"),
                        resultSet.getLong("supplier_AccNo")
                );
                suppliers.add(supplier);
            }
        }
        return suppliers;
    }

    // Fetch supplier details based on supplier code
    public Supplier getSupplierByCode(String supplierCode) throws SQLException, ClassNotFoundException {
        Supplier supplier = null;
        String sql = "SELECT supplier_code, supplier_name, supplier_city, company_name, " +
                     "payment_terms, supplier_bank_name, supplier_IFSCcode, supplier_AccNo " +
                     "FROM supplier WHERE supplier_code = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pStatement = connection.prepareStatement(sql)) {
            
            // Set the supplier code parameter for the query
            pStatement.setString(1, supplierCode);

            ResultSet resultSet = pStatement.executeQuery();

            // Check if we found a supplier with the given supplier code
            if (resultSet.next()) {
                supplier = new Supplier(
                        resultSet.getString("supplier_code"),
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
        return supplier;  // Returns null if supplier is not found
    }
}
