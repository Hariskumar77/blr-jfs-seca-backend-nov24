package com.bosch.stocktoship.entity;

public class User {

	 private String email;        // Email address of the user
	    private String password;     // Password for the user account
	    private String employeeId;   // Unique identifier for the employee
	    private String role;         // Role of the user (e.g., admin, user, manager, etc.)
	    private boolean loggedIn;    // Flag to track if the user is logged in or not

	    // Constructor to initialize a new User object with email, password, employeeId, and role
	    public User(String email, String password, String employeeId, String role) {
	        this.email = email;              // Set the email of the user
	        this.password = password;        // Set the password of the user
	        this.employeeId = employeeId;    // Set the employee ID for the user
	        this.role = role;                // Set the role of the user
	        this.loggedIn = false;           // Initially, the user is not logged in
	    }

	    // Getter method to retrieve the email of the user
	    public String getEmail() {
	        return email;
	    }

	    // Getter method to retrieve the password of the user
	    public String getPassword() {
	        return password;
	    }

	    // Getter method to retrieve the employee ID of the user
	    public String getEmployeeId() {
	        return employeeId;
	    }

	    // Getter method to retrieve the role of the user
	    public String getRole() {
	        return role;
	    }

	    // Getter method to check if the user is logged in
	    public boolean isLoggedIn() {
	        return loggedIn;
	    }

	    // Setter method to update the loggedIn status (true or false)
	    public void setLoggedIn(boolean loggedIn) {
	        this.loggedIn = loggedIn;
	    }
	   
	    // Setter method to change the user's password
	    public void setPassword(String newPassword) {
	        this.password = newPassword;    // Set a new password for the user
	    }
}
