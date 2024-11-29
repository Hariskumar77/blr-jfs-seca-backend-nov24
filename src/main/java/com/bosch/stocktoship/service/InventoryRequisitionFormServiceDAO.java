package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.bosch.stocktoship.entity.InventoryRequistionForm;
import com.bosch.stocktoship.repository.DBConnection;

/**
* Service class for Managing Inventory Requisition Form. This class provides
* methods for creating and viewing the Inventory Requisition Form.
*
* @author MKU1HYD
*/
public class InventoryRequisitionFormServiceDAO {
	
    // Method to save an Inventory Requisition Form to the database
    public void saveInventoryRequisitionForm(InventoryRequistionForm form, String name) {
        try (Connection connection = new DBConnection().getConnection(); // Get database connection
             PreparedStatement pstmt = connection.prepareStatement("insert into InventoryRequisitionForm" + name +
                     " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");) {

            // Set values in the PreparedStatement based on the form data
            pstmt.setString(1, form.getRequisitionerDepartment());
            pstmt.setTimestamp(2, new Timestamp(form.getDateOfRequisition().getTime()));
            pstmt.setString(3, form.getUniqueItemCode());
            pstmt.setString(4, form.getItemDescription());
            pstmt.setInt(5, form.getQuantity());
            pstmt.setString(6, form.getUnitOfMeasure());
            pstmt.setString(7, form.getPurposeOfRequisitions());
            pstmt.setString(8, form.getDeliveryDepartment());
            pstmt.setTimestamp(9, new Timestamp(form.getMaterialRequiredDate().getTime()));
            pstmt.setString(10, form.getNotesAndComments());

            // Set status based on the `name` parameter
            if (name == "") {
                pstmt.setString(11, "null");
            } else {
                if (name == "APPROVED") {
                    pstmt.setString(11, "Approved");
                } else {
                    pstmt.setString(11, "Rejected");
                }
            }

            // Execute the insert statement and check if a row was added
            int rowsInserted = pstmt.executeUpdate();

            // Create a statement to fetch records from the approved table if name is "APPROVED"
            Statement s = connection.createStatement();
            ResultSet rs = s.executeQuery("select * from InventoryRequisitionFormApproved");

            if (rowsInserted > 0 && name == "APPROVED") {
                // Print confirmation if insertion was successful
                System.out.println("A new requisition form " + name + " was inserted successfully!");
                System.out.println("Approved successfully");
                
                // Print table headers
                System.out.printf("%-12s%-13s%-17s%-13s%-17s%-13s%-18s%-18s%-15s%-15s%s\n", 
                                  "Req Dept", "Date of Req", "Item code", "Description", "Quantity", 
                                  "Unit of measure", "Purpose of Req", "Delivery Dep", "Material Req Date",
                                  "Comments", "Status");
                
                // Loop through the ResultSet and print each row for approved requisitions
                while (rs.next() && name == "APPROVED") {
                    System.out.format("%-12s%-13s%-17s%-13s%-17s%-13s%-18s%-18s%-15s%-15s%s\n",
                                      rs.getString(1), rs.getTimestamp(2), rs.getString(3), rs.getString(4), 
                                      rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8), 
                                      rs.getTimestamp(9), rs.getString(10), rs.getString(11));
                }
            } else if (name == "") {
                // Print confirmation if insertion was successful for non-approved entries
                System.out.println("A new requisition form was inserted successfully!");
            }
        } catch (SQLException e) {
            // Print error message if SQL exception occurs
            System.out.println("Error saving requisition form: " + e.getMessage());
        }
    }
}
