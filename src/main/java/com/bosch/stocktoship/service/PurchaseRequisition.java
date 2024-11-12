package com.bosch.stocktoship.service;

/**
 *  
 *  @author Varun Joshi VJO3KOR
 *  
 * */
import com.bosch.stocktoship.entity.*; 
import java.util.Map;
import java.util.TreeMap;

public class PurchaseRequisition {
    static int PRCounter = 10000;
    String prNumber;
    public static Map<String, RequisitionItem> prnMap = new TreeMap<>();
    boolean isSubmitted;

    public PurchaseRequisition(RequisitionItem items) {
        this.prNumber = generatePRNumber();
        prnMap.put(prNumber,items);
        this.isSubmitted = false;
    }
    
    //Getter Setter Methods
    
    public String getPRNumber() {
    	return prNumber;
    }
    
    public static int getPRCounter() {
		return PRCounter;
	}
	
	public RequisitionItem getItem() {
		return prnMap.get(prNumber);
	}
	
	public boolean isSubmitted() {
		return isSubmitted;
	}
	
	private String generatePRNumber() {
        PRCounter++;
        return String.valueOf(PRCounter);
    }
	
	// Submitting PR
	
    public void submitPR() {
        if (!isSubmitted) {
            isSubmitted = true;
            System.out.println("PR Submitted Successfully! PR Number: " + prNumber);
        } else {
            System.out.println("PR already submitted.");
        }
    }

    // Display PR Details
    
    public void displayPRDetails() {
        for (String prn : prnMap.keySet()) {
        	System.out.println(prnMap.get(prn));
        }
    }

    //Processing Approval of PR
    
    public void processApproval() {
        System.out.println("\nProcessing approval...");
        for (String prn : prnMap.keySet()) {
            if (prnMap.get(prn).approvalStatus.equals("Approved")) {
                System.out.println("PR number " + prn + " directly moves to the Purchase Department.");
            } else {
                System.out.println("PR number " + prn + " requires 2nd and 3rd level approval.");
            }
        }
    }
}