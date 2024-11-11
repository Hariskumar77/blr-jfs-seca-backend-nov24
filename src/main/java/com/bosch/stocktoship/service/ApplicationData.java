package com.bosch.stocktoship.service;

//Created by Aakriti Sinha -- 04/11/2024(Squad -1)


import java.util.Scanner;

import com.bosch.stocktoship.entity.User;

//Main application class to interact with users for login, registration, and password reset.
public class ApplicationData {
 // Instance of AuthenticationService to handle user authentication and registration
 private AuthenticationService authService = new AuthenticationService();
 // Scanner instance to read user input from the console
 private Scanner scanner = new Scanner(System.in);

 // Method to start the application and display the menu to the user
 public void start() {
     while (true) {
         // Display the main menu options
         System.out.println("1. Login");
         System.out.println("2. Register");
         System.out.println("3. Request Password Reset");
         System.out.println("4. Exit");
         // Read the user's choice
         int choice = scanner.nextInt();
         scanner.nextLine(); // Consume the newline character left by nextInt()

         // Perform the action based on the user's choice
         switch (choice) {
             case 1:
                 loginUser(); // Call loginUser method if the user selects "Login"
                 break;
             case 2:
                 registerUser(); // Call registerUser method if the user selects "Register"
                 break;
             case 3:
                 requestPasswordReset(); // Call requestPasswordReset method if the user selects "Request Password Reset"
                 break;
             case 4:
                 System.out.println("Exiting..."); // Exit the application if the user selects "Exit"
                 return; // Exit the while loop and stop the program
             default:
                 System.out.println("Invalid choice. Try again."); // Inform the user of an invalid choice
         }
     }
 }

 // Method to handle user registration
 private void registerUser() {
     System.out.print("Enter email: ");
     String email = scanner.nextLine(); // Read the user's email

     // Loop to ensure that the passwords entered by the user match
     Boolean bool1 = true;
     String password = null, password1;
     while (bool1) {
         System.out.print("Enter password: ");
         password = scanner.nextLine(); // Read the user's password
         System.out.print("Confirm password: ");
         password1 = scanner.nextLine(); // Read the password confirmation

         // Check if the passwords match
         if (password.equals(password1)) {
             System.out.println("Passwords match, please proceed");
             bool1 = false; // Exit the loop if passwords match
         } else {
             System.out.println("Passwords do not match."); // Prompt the user to enter matching passwords
             bool1 = true; // Continue the loop if passwords don't match
         }
     }

     System.out.print("Enter employee ID: ");
     String employeeId = scanner.nextLine(); // Read the user's employee ID
     System.out.print("Enter role: ");
     String role = scanner.nextLine(); // Read the user's role
     String result = authService.registerUser(email, password, employeeId, role); // Call registerUser method from AuthenticationService
     System.out.println(result); // Print the result of the registration
 }

 // Method to handle user login
 private void loginUser() {
     System.out.print("Enter email: ");
     String email = scanner.nextLine(); // Read the user's email
     System.out.print("Enter password: ");
     String password = scanner.nextLine(); // Read the user's password
     String result = authService.login(email, password); // Call login method from AuthenticationService
     System.out.println(result); // Print the result of the login attempt

     // If login is successful, display the user's dashboard
     if ("Login successful.".equals(result)) {
         displayDashboard(email); // Call displayDashboard method if login is successful
     }
 }

 // Method to handle password reset requests
 private void requestPasswordReset() {
     System.out.print("Enter your email: ");
     String email = scanner.nextLine(); // Read the user's email to reset password

     User user = authService.getUserByEmail(email); // Get the user by email from AuthenticationService

     // Ensure the user enters a new password that matches the confirmation
     boolean x = true;
     String newPassword = null, newPassword1;
     while (x) {
         System.out.print("Enter your new password: ");
         newPassword = scanner.nextLine(); // Read the new password

         // Ensure the new password doesn't match the old password
         if (newPassword.equals(user.getPassword())) {
             // Optionally, add logic to check if the new password is different from the old one
         }
         System.out.print("Confirm your new password: ");
         newPassword1 = scanner.nextLine(); // Read the password confirmation

         // Check if the new passwords match
         if (newPassword.equals(newPassword1)) {
             user.setPassword(newPassword); // Update the user's password in the system
             System.out.println("Password has been reset successfully.");
             x = false; // Exit the loop once the password is updated
         } else {
             System.out.println("Passwords do not match, please try again."); // Prompt the user to retry if passwords don't match
             x = true; // Continue the loop if passwords don't match
         }
     }
 }

 // Method to display the user's dashboard once logged in
 private void displayDashboard(String email) {
     User user = authService.getUserByEmail(email); // Get the user by email

     // Check if the user is logged in and display their dashboard
     if (user != null && user.isLoggedIn()) {
         System.out.println("Dashboard: Welcome " + user.getEmail() +
                            ", Role: " + user.getRole() +
                            ", Employee ID: " + user.getEmployeeId());
     }
 }



}
