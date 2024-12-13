package com.bosch.stocktoship;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.repository.DatabaseConnect;
import com.bosch.stocktoship.service.OutboundRequisitionForm;
import com.bosch.stocktoship.service.RobotDAO;

class InboundTest {

    private RobotDAO robotDAO;

    @BeforeEach
    void setUp() throws Exception {
        robotDAO = new RobotDAO();

        // Set up database tables for testing
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();

            // Create the 'robot' table for testing if it does not exist
            try {
                stmt.execute("CREATE TABLE robot (ID INT, CAPACITY INT, robot_name VARCHAR(255))");
            } catch (Exception e) {
                // Table already exists; continue
            }
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        // Drop the robot table after each test to reset the database
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            try {
                stmt.execute("DROP TABLE robot");
            } catch (Exception e) {
                // Table did not exist; continue
            }
        }
    }

    @Test
    void testInsertRobotDetails() throws Exception {
        // Call the insertRobotDetails() method to insert the predefined robots
        robotDAO.insertRobotDetails();

        // Verify that the robots were inserted into the database
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM robot");

            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                // Ensure that 5 robots have been inserted
                assertEquals(5, rowCount, "Expected 5 robots to be inserted into the robot table.");
            }
        }
    }

    @Test
    void testRobotDetailsInsertion() throws Exception {
        // Manually insert a robot and verify it is correctly inserted
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO robot (ID, CAPACITY, ROBOT_NAME) VALUES (6, 1500, 'Robo6')");

            // Retrieve and verify the inserted robot
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM robot WHERE ID = 6");

            if (resultSet.next()) {
                assertEquals(6, resultSet.getInt("ID"));
                assertEquals(1500, resultSet.getInt("CAPACITY"));
                assertEquals("Robo6", resultSet.getString("robot_name"));
            } 
        }
    }

    @Test
    void testRobotDetailsIntegrity() throws Exception {
        // Insert robot data using the method in RobotDAO
        robotDAO.insertRobotDetails();

        // Retrieve a robot by name and check its details
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM robot WHERE robot_name = 'BB8'");

            if (resultSet.next()) {
                assertEquals(1, resultSet.getInt("ID"));
                assertEquals(50, resultSet.getInt("CAPACITY"));
                assertEquals("BB8", resultSet.getString("robot_name"));
            } 
        }
    }

    @Test
    void testTruncateRobotTable() throws Exception {
        // First, insert robot data using insertRobotDetails
        robotDAO.insertRobotDetails();

        // Now, check if the table is truncated before insertion
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("TRUNCATE table robot");

            // Verify that the robot table is empty after truncation
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM robot");
            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                assertEquals(0, rowCount, "The robot table should be empty after truncation.");
            }
        }
    }

    @Test
    void testRobotCountAfterInsert() throws Exception {
        // Insert robot data and then verify the count of records
        robotDAO.insertRobotDetails();

        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM robot");

            if (resultSet.next()) {
                int rowCount = resultSet.getInt(1);
                // There should be 5 robots after calling insertRobotDetails
                assertEquals(5, rowCount, "Expected 5 robots to be inserted.");
            }
        }
    }
}
