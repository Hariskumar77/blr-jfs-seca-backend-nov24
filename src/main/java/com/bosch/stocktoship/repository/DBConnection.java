package com.bosch.stocktoship.repository;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// DBConnection class provides a method to establish a connection to the database
public class DBConnection {

    // Method to get a connection to the database
    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Attempt to establish a connection using the JDBC URL, username, and password
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "akash");
        } catch (SQLException e) {
            // Print the stack trace if an SQLException occurs
            e.printStackTrace();
        }

        // Return the established connection (or null if failed)
        return connection;
    }
}
