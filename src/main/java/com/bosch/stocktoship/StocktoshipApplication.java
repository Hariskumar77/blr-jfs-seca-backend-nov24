package com.bosch.stocktoship;

/**
*
* @authors Varun Joshi VJO3KOR, LDO1COB Soundarya, QEU3KOR Supriya
* 
* */

import java.sql.SQLException;
import java.util.*;

import com.bosch.stocktoship.entity.RequisitionItem;
import com.bosch.stocktoship.entity.Supplier;
import com.bosch.stocktoship.service.MaterialRequisition;
import com.bosch.stocktoship.service.PurchaseOrder;
import com.bosch.stocktoship.service.PurchaseOrderDAO;
import com.bosch.stocktoship.service.PurchaseRequisitioinDAO;
import com.bosch.stocktoship.service.PurchaseRequisition;


public class StocktoshipApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Initialize Scanner and DAO
        Scanner scanner = new Scanner(System.in);
        PurchaseOrderDAO poDAO = new PurchaseOrderDAO();
        PurchaseOrder po = new PurchaseOrder();
        MaterialRequisition mRequisition = new MaterialRequisition();

        // PR details section
        PurchaseRequisition pr = null;
        String prNo = null;

        // Collect PR details
        System.out.print("Item Code: ");
        String itemCode = scanner.nextLine();

        System.out.print("Purpose Description: ");
        String purposeDescription = scanner.nextLine();

        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Unit (kg/gm/barrel/meter etc.): ");
        String unit = scanner.nextLine();

        System.out.print("Department (optional, leave blank for none): ");
        String department = scanner.nextLine();

        System.out.print("Company Make: ");
        String companyMake = scanner.nextLine();

        // Create Purchase Requisition with items
        pr = new PurchaseRequisition(new RequisitionItem(itemCode, purposeDescription, quantity, unit, department, companyMake));

        // Display PR details
        pr.displayPRDetails();

        // Submit PR
        String submitResponse;
        do {
            System.out.print("\nType SUBMIT to Submit the PR: ");
            submitResponse = scanner.nextLine();
            if (submitResponse.equalsIgnoreCase("submit")) {
                

                // Pass the item details to DAO class to store in DB
                PurchaseRequisitioinDAO pDao = new PurchaseRequisitioinDAO();
                RequisitionItem rItem = new RequisitionItem(itemCode, purposeDescription, quantity, unit, department, companyMake);
                prNo = pDao.createPurchaseRequisitionAndGetPrNo(rItem);
                pr.submitPR(prNo);
                // Process approval and move items to departments
                pr.processApproval(prNo);
            } else {
                System.out.println("PR not submitted.");
            }
        } while (!submitResponse.equalsIgnoreCase("submit"));

        // PR order module
        System.out.println("\n***************** Material Requisition and Purchase Order Module *****************\n");

        System.out.println("The generated PR number from DB is " + prNo);

        // Fetch PR number from the database
        RequisitionItem item = new RequisitionItem();

        System.out.print("Enter the PR number: ");
        while (true) {
            prNo = scanner.nextLine();
            System.out.println("\n-----------------------------------------------------");

            // Fetch PR details from the database using PR number
            item = poDAO.getPurchaseRequisitionDetails(Integer.parseInt(prNo));

            if (item != null) {
                // Display PR details
                System.out.println("PR Details:");
                System.out.println("Item Code: " + item.getItemCode());
                System.out.println("Purpose: " + item.getPurposeDescription());
                System.out.println("Quantity: " + item.getQuantity());
                System.out.println("Unit: " + item.getUnit());
                System.out.println("Company Make: " + item.getCompanyMake());
                System.out.println("Approval Status: " + item.getApprovalStatus());

                // Checking PR status
                if (item.getApprovalStatus().equalsIgnoreCase("Approved")) {
                    System.out.println("\nPR is approved. Moving to Material requisition...\n");

                    mRequisition.requestQuotations(item);

                    List<Supplier> suppliers = mRequisition.getSuppliers();

                    System.out.println("\n-----------------------------------------------------");
                    System.out.println("Select a supplier from the list:");
                    System.out.println("-----------------------------------------------------");
                    // Display supplier list
                    for (int i = 0; i < suppliers.size(); i++) {
                        System.out.println((i + 1) + ". " + suppliers.get(i).toString());
                    }
                    System.out.println("\n-----------------------------------------------------");
                    System.out.print("Enter the serial number of the supplier you choose: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    // Get the supplier from the selected choice
                    Supplier selectedSupplier = mRequisition.selectSupplier(choice);

                    // Fetch supplier details from database using supplier code
                    Supplier supplierDetails = poDAO.getSupplierDetails(selectedSupplier.getSupplierCode());

                    if (supplierDetails != null) {
                        // Place the purchase order
                        po.displaySupplier(prNo, item, supplierDetails);

                        // Ask for final confirmation to submit the order
                        String placeOrderConfirmation;
                        System.out.print("\nDo you want to submit this Purchase Order? (yes/no): ");
                        placeOrderConfirmation = scanner.nextLine();

                        if (placeOrderConfirmation.equalsIgnoreCase("yes")) {
                            // Submit the purchase order and generate a PO number
                            //System.out.println("Purchase Order successfully placed!");
                            po.placePurchaseOrder(prNo, selectedSupplier);
                            
                            
                        } else {
                            System.out.println("Purchase Order was not submitted.");
                        }
                    } else {
                        System.out.println("Supplier details not found in the database.");
                    }
                } else {
                    System.out.println("PR is not approved yet.");
                }
                break;
            } else {
                System.out.println("PR number not found. Please enter a valid PR number.");
                continue;
            }
        }

        // Close the scanner
        scanner.close();
    }
}
