package com.bosch.stocktoship;

/**
*
* @authors Varun Joshi VJO3KOR, LDO1COB Soundarya 
* 
* */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bosch.stocktoship.entity.*;
import com.bosch.stocktoship.service.*;

import java.sql.SQLException;
import java.util.*;
@SpringBootApplication
public class StocktoshipApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(StocktoshipApplication.class, args);
		Scanner scanner = new Scanner(System.in);
		
//      Enter PR details

        PurchaseRequisition pr = null;
        
        String prNo = null;

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

        //Submit PR
        String submitResponse;
        do {
        	System.out.print("\nType SUBMIT to Submit the PR");
	        submitResponse = scanner.nextLine();
		    if (submitResponse.equalsIgnoreCase("submit")) {
		        pr.submitPR(prNo);
		        
		        // Passing the item details to DAO class to store in DB
		        PurchaseRequisitioinDAO pDao = new PurchaseRequisitioinDAO();
		        RequisitionItem rItem =  new RequisitionItem(itemCode, purposeDescription, quantity, unit, department, companyMake);
		       prNo = pDao.createPurchaseRequisitionAndGetPrNo(rItem);
		        
	    //Process approval and move items to departments
		        pr.processApproval(prNo);
		    } else {
		        System.out.println("PR not submitted.");
		    }
		    
        }while(!submitResponse.equalsIgnoreCase("submit"));
        
        PurchaseOrder po = new PurchaseOrder();
		MaterialRequisition mRequisition = new MaterialRequisition();
		
			
		System.out.println("\n*****************Material requisition and Purchase Order Module*****************\n");
		
		System.out.println("the generated PR number from DB is "+ prNo);
		
		RequisitionItem item = new RequisitionItem();
		System.out.println("Enter the PR number ");
		
		while(true) {
		
			 prNo = scanner.nextLine();
			 
			 if(PurchaseRequisition.prnMap.containsKey(prNo)){
				 
				 item = PurchaseRequisition.prnMap.get(prNo);
			
				// checking for PR status
				if(item.approvalStatus.equalsIgnoreCase("Approved")){
					
					System.out.println("\nPR is approved. So, moving to Material requisition\n");
					
			        mRequisition.requestQuotations(item);
			        
			        List<Supplier> suppliers = new ArrayList<>();
			        suppliers = mRequisition.getSuppliers();
			        
			        System.out.println("\nSelect a supplier from the list:");
			        for (int i = 0; i < suppliers.size(); i++) {
			            System.out.println((i + 1) + ". " + suppliers.get(i).toString());
			        }
			        System.out.println("Enter the serial no of the supplier you choose: ");
			        int choice = scanner.nextInt();
			        
			        Supplier supplier = mRequisition.selectSupplier(choice);
			        
			        po.placePurchaseOrder(prNo, item, supplier);
				} else {
					System.out.println("PR is not approved yet...");
				}
				break;
			 } else {
				 System.out.println("Enter the correct PR number");
				 continue;
			 }
		}
		
        scanner.close();
        

	}

}
