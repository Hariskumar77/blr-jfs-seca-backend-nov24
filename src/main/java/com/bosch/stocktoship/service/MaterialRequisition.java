package com.bosch.stocktoship.service;

/**
 *  @author LDO1COB Soundarya
 *  @author QEU3KOR	Supriya
 * 
 * */
import com.bosch.stocktoship.entity.*;

import java.sql.SQLException;
import java.util.List;



public class MaterialRequisition {

    private List<Supplier> suppliers;

    // Constructor now uses the DAO to fetch suppliers from the database
    public MaterialRequisition() throws SQLException, ClassNotFoundException {
        SupplierDAO supplierDAO = new SupplierDAO();
        suppliers = supplierDAO.getAllSuppliers();  // Fetch the suppliers from the database
    }

    // Getter Method for Suppliers
    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    // Requesting Quotations from Suppliers
    public void requestQuotations(RequisitionItem item) {
        System.out.println("------------------------------------------\nSending item details to suppliers for quotation\n------------------------------------------");

        for (Supplier supplier : suppliers) {
            System.out.println("Sent a mail to " + supplier.toString());
        }
    }

    // Selecting Supplier based on user choice
    public Supplier selectSupplier(int choice) {
        if (choice > 0 && choice <= suppliers.size()) {
            return suppliers.get(choice - 1);  // Since choice is 1-based
        }
        return null;  // Return null if the choice is invalid
    }
}