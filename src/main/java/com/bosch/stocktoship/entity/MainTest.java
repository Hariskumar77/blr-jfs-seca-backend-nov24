package com.bosch.stocktoship.entity;

import java.util.*;

public class MainTest {
    /*
     * AUTHOR: AYUSH ARYA (YYA2COB)
     * AUTHOR: CHARUL SAINI (SIC2COB)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Display main menu options
        System.out.println("STOCK-TO-SHIP");
        System.out.println("Enter your choice");
        System.out.println("1. Ware-You-Go");
        System.out.println("2. Car-O-Drive");
        System.out.println("3. Stock Vault");
        
        // Read user choice for main menu
        int choice = Integer.parseInt(sc.nextLine());
        
        switch (choice) {
            case 1:
                // Handle Ware-You-Go option (Inbound or Outbound Management)
                System.out.println("Enter your choice");
                System.out.println("1. Inbound Management");
                System.out.println("2. OutBound Management");
                int managementChoice = Integer.parseInt(sc.nextLine());
                
                if (managementChoice == 1) {
                    // Inbound Management
                    InboundRequisition inboundRequisition = new InboundRequisition();
                    inboundRequisition.inboundMgmt();
                } else if (managementChoice == 2) {
                    // Outbound Management
                    OutboundRequisitionForm outboundRequisitionForm = new OutboundRequisitionForm();
                    outboundRequisitionForm.displayDetails();
                } else {
                    System.out.println("Invalid Input");
                }
                break;
            case 2:
                // Handle Car-O-Drive option
                System.out.println("Car-O-Drive");
                break;
            case 3:
                // Handle Stock Vault option
                System.out.println("Stock Vault");
                break;
            default:
                // Handle invalid choice
                System.out.println("Invalid Input");
                break;
        }
    }
}
