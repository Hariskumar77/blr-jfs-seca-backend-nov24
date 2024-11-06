package com.bosch.stocktoship;
/**
*
* @authors Varun Joshi VJO3KOR
* 
* */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bosch.stocktoship.entity.*;
import com.bosch.stocktoship.service.*;
import java.util.*;
@SpringBootApplication
public class StocktoshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocktoshipApplication.class, args);
		Scanner scanner = new Scanner(System.in);
		
//      Enter PR details

        PurchaseRequisition pr = null;

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
		        pr.submitPR();
	    //Process approval and move items to departments
		        pr.processApproval();
		    } else {
		        System.out.println("PR not submitted.");
		    }
		    
        }while(!submitResponse.equalsIgnoreCase("submit"));
        

	}

}
