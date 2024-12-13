package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.repository.DatabaseConnect;
import com.bosch.stocktoship.service.LocationAnalayzer;

class LocationAnalyzerTest {

    private LocationAnalayzer locationAnalyzer;

    @BeforeEach
    void setUp() throws Exception {
        locationAnalyzer = new LocationAnalayzer();

        // Set up the tables for testing
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();

            // Create 'location' table
            stmt.execute("CREATE TABLE location (rack INT, shelf INT, product_code VARCHAR(255), quantity INT)");

            // Create 'robot' table
            stmt.execute("CREATE TABLE robot (id INT, capacity INT, name VARCHAR(255))");

            // Insert sample data into 'robot' table
            stmt.execute("INSERT INTO robot (id, capacity, name) VALUES (1, 100, 'Robot1')");
            stmt.execute("INSERT INTO robot (id, capacity, name) VALUES (2, 200, 'Robot2')");
            stmt.execute("INSERT INTO robot (id, capacity, name) VALUES (3, 500, 'Robot3')");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            // Drop the tables after the test
            stmt.execute("DROP TABLE location");
            stmt.execute("DROP TABLE robot");
        }
    }

    @Test
    void testFetchLocation() throws SQLException {
        // Insert sample data into 'location' table
        try (Connection conn = DatabaseConnect.getConnection()) {
            String insertSQL = "INSERT INTO location (rack, shelf, product_code, quantity) VALUES (1, 1, 'P001', 10)";
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.executeUpdate();

            insertSQL = "INSERT INTO location (rack, shelf, product_code, quantity) VALUES (2, 2, NULL, NULL)";
            stmt = conn.prepareStatement(insertSQL);
            stmt.executeUpdate();
        }

        // Fetch locations using the method
        List<Location> locations = locationAnalyzer.fetchLocation();

        // Verify that we fetched locations
        assertNotNull(locations);
        assertTrue(locations.size() > 0, "Locations should be fetched");

        // Verify that one location is available and one is unavailable
        assertTrue(locations.stream().anyMatch(location -> location.getRack() == 2 && location.getShelf() == 2),
                "Location (2, 2) should be available.");
        assertTrue(locations.stream().anyMatch(location -> location.getRack() == 1 && location.getShelf() == 1),
                "Location (1, 1) should be unavailable.");
    }

  

    @Test
    void testAssignRobotBasedOnCapacity() {
        // Assign a robot based on product weight
        int productWeight = 150; // Weight that falls under Robot2
        Robot assignedRobot = locationAnalyzer.assignRobotBasedOnCapacity(productWeight);

        // Verify the assigned robot
        assertNotNull(assignedRobot, "A robot should be assigned.");
        assertEquals("Robot2", assignedRobot.getName(), "The robot assigned should be Robot2.");
    }

    @Test
    void testAssignRobotForHeavyProduct() {
        // Assign a robot for a heavy product (greater than Robot2's capacity)
        int heavyProductWeight = 350; // Weight that exceeds Robot2's capacity
        Robot assignedRobot = locationAnalyzer.assignRobotBasedOnCapacity(heavyProductWeight);

        // Verify that the robot assigned is Robot3 (which has enough capacity)
        assertNotNull(assignedRobot, "A robot should be assigned.");
        assertEquals("Robot3", assignedRobot.getName(), "The robot assigned should be Robot3.");
    }
}
