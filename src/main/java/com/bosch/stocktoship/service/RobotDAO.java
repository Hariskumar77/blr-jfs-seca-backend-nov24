package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.bosch.stocktoship.repository.DatabaseConnect;

public class RobotDAO {
    /*
     * AUTHOR: CHARUL SAINI (SIC2COB)
     * AUTHOR: DAFEDAR MUJAHID
     */
    
    // Database connection object
    Connection con;

    /**
     * This method inserts predefined robot details into the 'robot' table in the database.
     * It first clears any existing data in the 'robot' table by truncating it,
     * and then it inserts a predefined set of robot records with ID, capacity, and name.
     */
    public void insertRobotDetails() {
        try {
            // Establish connection to the database
            con = DatabaseConnect.getConnection();

            // SQL query to truncate (clear) the 'robot' table before inserting new data
            String query = "TRUNCATE table robot";
            Statement statement = con.createStatement();
            statement.executeUpdate(query);  // Execute the truncate query to remove existing records

            // List of predefined SQL insert queries for adding new robot records
            List<String> robotDetails = Arrays.asList(
                "INSERT INTO robot VALUES (1, 50, 'BB8')",   
                "INSERT INTO robot VALUES (2, 100, 'Groot')",  
                "INSERT INTO robot VALUES (3, 200, 'Vision')",
                "INSERT INTO robot VALUES (4, 500, 'Ultron')",
                "INSERT INTO robot VALUES (5, 1000, 'Chitti')"
            );

            // Loop through each robot record and execute the corresponding insert query
            for (String row : robotDetails) {
                Statement statement2 = con.createStatement();
                statement2.executeUpdate(row);  // Insert the robot record
                statement2.close();  // Close the statement after execution
            }

        } catch (SQLException e) {
                       e.printStackTrace();
        }
    }
}
