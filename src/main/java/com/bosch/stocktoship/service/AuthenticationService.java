package com.bosch.stocktoship.service;

// Created by Aakriti Sinha -- 04/11/2024 (Squad -1)
// Modified by Akash Hegde P -- 11/11/2024 (Squad-1)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.bosch.stocktoship.repository.DBConnection;
import com.bosch.stocktoship.entity.User;

//Modified by Akash Hegde P -- 11/11/2024 (Squad-1)
// AuthenticationService class manages user registration, login, and retrieval
public class AuthenticationService {
    
    // A Map to store users, using email as the key and User object as the value
    private Map<String, User> users = new HashMap<>();

    // Constructor to initialize the AuthenticationService with a sample user for testing
    public AuthenticationService() {
        setupDatabase(); 
    }
 
    // Modified by Akash Hegde P -- 11/11/2024 (Squad-1)
    // Method to set up the user_details table if it does not exist
    private void setupDatabase() {
        String checkTableQuery = "select count(*) from user_tables where table_name = 'USER_DETAILS'";
        String createTableQuery = "create table user_details (" +
                                  "email varchar2(255) primary key, " +
                                  "password varchar2(255) not null, " +
                                  "employeeId varchar2(50), " +
                                  "role varchar2(50))";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement psCheck = connection.prepareStatement(checkTableQuery);
             ResultSet rs = psCheck.executeQuery()) {

            // Check if the table exists
            if (rs.next() && rs.getInt(1) == 0) { // Table doesn't exist
                // Create the user_details table if it does not exist
                try (PreparedStatement psCreate = connection.prepareStatement(createTableQuery)) {
                    psCreate.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Modified by Akash Hegde P -- 11/11/2024 (Squad-1)
    // Method to register a new user
    public String registerUser(String email, String password, String employeeId, String role) {
        String insertQuery = "insert into user_details (email, password, employeeId, role) values (?, ?, ?, ?)";
        String checkUserQuery = "select count(*) from user_details where email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
             PreparedStatement checkStatement = connection.prepareStatement(checkUserQuery)) {

            // Check if the user already exists (based on the email)
            checkStatement.setString(1, email);
            ResultSet rs = checkStatement.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                // Return message if the user already exists
                return "Account exists.";
            }

            // Create a new User object and store it in the map
            User user = new User(email, password, employeeId, role);
            users.put(email, user);

            // Insert the new user into the database
            insertStatement.setString(1, user.getEmail());
            insertStatement.setString(2, user.getPassword());
            insertStatement.setString(3, user.getEmployeeId());
            insertStatement.setString(4, user.getRole());

            insertStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return success message
        return "Registration successful.";
    }

    // Modified by Akash Hegde P -- 11/11/2024 (Squad-1)
    // Method to log in a user using their email and password
    public String login(String email, String password) {
        String checkUserQuery = "select * from user_details where email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(checkUserQuery)) {

            checkStatement.setString(1, email);
            ResultSet rs = checkStatement.executeQuery();

            // Check if user exists and validate the password
            if (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    // Return message if login is successful
                    return "Login successful."; 
                } else {
                    // Return message if password is invalid
                    return "Invalid password.";
                }
            } else {
                // Return message if the user doesn't exist
                return "User doesn't exist.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return message if there is an error
        return "Invalid credentials.";
    }
    
    // Modified by Akash Hegde P -- 11/11/2024 (Squad-1)
    // Method to change the user's password
    public String changePassword(String email, String newPassword) {
        String updateQuery = "update user_details set password = ? where email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

            updateStatement.setString(1, newPassword);
            updateStatement.setString(2, email);

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected == 1) {
                return "Password changed successfully";
            } else {
                return "No user found with the provided email";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return null if there is an issue with the operation
        return null;
    }
 
    // Modified by Akash Hegde P -- 11/11/2024 (Squad-1)
    // Method to retrieve a user by their email
    public User getUserByEmail(String email) {
        User user = null;
        String checkUserQuery = "select * from user_details where email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(checkUserQuery)) {

            selectStatement.setString(1, email);
            ResultSet rs = selectStatement.executeQuery();

            // Retrieve user details if the user exists
            if (rs.next()) {
                String retrievedEmail = rs.getString("email");
                String password = rs.getString("password");
                String employeeId = rs.getString("employeeId");
                String role = rs.getString("role");

                // Create a User object and return it
                user = new User(retrievedEmail, password, employeeId, role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return the retrieved user, or null if not found
        return user;
    }
}
