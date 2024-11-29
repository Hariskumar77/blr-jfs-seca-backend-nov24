package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.bosch.stocktoship.repository.DatabaseConnect;

public class LocationDAO {
    /*
     * AUTHOR: CHARUL SAINI (SIC2COB)
     */

    // Database connection
    Connection con;

    /**
     * This method inserts predefined location details into the database.
     * It first truncates the 'location' table to clear any existing data.
     * Then, it inserts a set of predefined location records.
     */
    public void insertLocationDetails() {
        try {
            // Establish database connection
            con = DatabaseConnect.getConnection();

            // Query to truncate (clear) the 'location' table
            String query = "TRUNCATE table location";
            Statement statement = con.createStatement();
            statement.executeUpdate(query);  // Execute truncate query to clear existing data

            // List of predefined SQL insert queries for adding new location details
            List<String> locationDetails = Arrays.asList(
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (1, 1, 1, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (2, 1, 2, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (3, 1, 3, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (4, 1, 4, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (5, 1, 5, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (6, 2, 1, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (7, 2, 2, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (8, 2, 3, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (9, 2, 4, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (10, 2, 5, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (11, 3, 1, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (12, 3, 2, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (13, 3, 3, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (14, 3, 4, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (15, 3, 5, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (16, 4, 1, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (17, 4, 2, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (18, 4, 3, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (19, 4, 4, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (20, 4, 5, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (21, 5, 1, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (22, 5, 2, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (23, 5, 3, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (24, 5, 4, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (25, 5, 5, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (26, 6, 1, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (27, 6, 2, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (28, 6, 3, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (29, 6, 4, 10000)",
                "INSERT INTO location(location_id, rack, shelf, capacity) VALUES (30, 6, 5, 10000)"
            );

            // Execute each insert query to populate the 'location' table
            for (String row : locationDetails) {
                Statement statement2 = con.createStatement();
                statement2.executeUpdate(row);  // Insert each location record
                statement2.close();  // Close the statement after execution
            }

        } catch (SQLException e) {
            // Print the stack trace in case of an SQL error
            e.printStackTrace();
        }
    }
}
