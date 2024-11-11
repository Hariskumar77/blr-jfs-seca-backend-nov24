package com.bosch.stocktoship.service;

//Created by Aakriti Sinha -- 04/11/2024(Squad -1)


import java.util.HashMap;
import java.util.Map;

import com.bosch.stocktoship.entity.User;

//AuthenticationService class manages user registration, login, and retrieval
public class AuthenticationService {
 // A Map to store users, using email as the key and User object as the value
 private Map<String, User> users = new HashMap<>();

 // Constructor to initialize the AuthenticationService with a sample user for testing
 public AuthenticationService() {
     // Adding a sample user to the users map for testing
     users.put("user@example.com", new User("user@example.com", "password123", "EMP001", "User"));
 }

 // Method to register a new user
 public String registerUser(String email, String password, String employeeId, String role) {
     // Check if the user already exists (based on the email)
     if (users.containsKey(email)) {
         // Return message if the user already exists
         return "User already exists.";
     }
     
     // Create a new User object and store it in the map
     users.put(email, new User(email, password, employeeId, role));
     
     // Return success message
     return "Registration successful.";
 }

 // Method to log in a user using their email and password
 public String login(String email, String password) {
     // Retrieve the user object from the map using email
     User user = users.get(email);

     // Check if the user exists and if the password matches
     if (user != null && user.getPassword().equals(password)) {
         // Set the user as logged in
         user.setLoggedIn(true);
         
         // Return success message
         return "Login successful.";
     }

     // Return message if the login credentials are invalid
     return "Invalid credentials.";
 }

 // Method to retrieve a user by their email
 public User getUserByEmail(String email) {
     // Return the user object associated with the given email, or null if not found
     return users.get(email);
 }
}
