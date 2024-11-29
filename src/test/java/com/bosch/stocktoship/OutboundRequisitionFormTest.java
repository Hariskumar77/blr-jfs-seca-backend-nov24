package com.bosch.stocktoship;
  
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
 
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
 
import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.repository.DatabaseConnect;
import com.bosch.stocktoship.service.OutboundRequisitionForm;
 
class OutboundRequisitionFormTest {
	
    /**
     * @AUTHOR: HARISKUMAR S (ZEI1COB)
     */
 
    private OutboundRequisitionForm form;
 
    @BeforeEach
    void setUp() throws Exception {
        form = new OutboundRequisitionForm();
        // Set up database tables for testing
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
 
            try {
                stmt.execute("CREATE TABLE DeliveryDepartment (DEPARTMENT_NAME VARCHAR(255), INCHARGE VARCHAR(255))");
            } catch (Exception e) {
                // Table already exists; continue
            }
 
            try {
                stmt.execute("CREATE TABLE LOCATION (ID INT, RACK INT, SHELF INT, PRODUCT_CODE VARCHAR(255), CAPACITY INT, QUANTITY INT)");
            } catch (Exception e) {
                // Table already exists; continue
            }
 
            try {
                stmt.execute("CREATE TABLE Robot (ID INT, CAPACITY INT, NAME VARCHAR(255))");
            } catch (Exception e) {
                // Table already exists; continue
            }
 
            try {
                stmt.execute("CREATE TABLE products (product_code VARCHAR(255), weight INT)");
            } catch (Exception e) {
                // Table already exists; continue
            }
        }
    }
 
    @AfterEach
    void tearDown() throws Exception {
        // Drop tables after each test to reset database
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
 
            try {
                stmt.execute("DROP TABLE DeliveryDepartment");
            } catch (Exception e) {
                // Table did not exist; continue
            }
            try {
                stmt.execute("DROP TABLE LOCATION");
            } catch (Exception e) {
                // Table did not exist; continue
            }
            try {
                stmt.execute("DROP TABLE Robot");
            } catch (Exception e) {
                // Table did not exist; continue
            }
            try {
                stmt.execute("DROP TABLE products");
            } catch (Exception e) {
                // Table did not exist; continue
            }
        }
    }
 
    @Test
    void testGetDeliveryInchargeBasedOnDepartmentName() throws Exception {
        String departmentName = "Electronics";
        // Insert test data into DeliveryDepartment table
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO DeliveryDepartment (DEPARTMENT_NAME, INCHARGE) VALUES ('Electronics', 'John Doe')");
        }
 
        assertDoesNotThrow(() -> form.getDeliveryInchargeBasedOnDepartmentName(departmentName));
    }
 
    @Test
    void testGetAvailableLocations() throws Exception {
        String productCode = "A001";
        // Insert test data into LOCATION table
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO LOCATION (ID, RACK, SHELF, PRODUCT_CODE, CAPACITY, QUANTITY) VALUES (1, 2, 3, 'A001', 100, 10)");
        }
 
        List<Location> locations = form.getAvailableLocations(productCode);
        // Assert that correct location data is retrieved
        assertEquals(1, locations.size());
        assertEquals("A001", locations.get(0).getProductCode());
    }
 
    @Test
    void testUpdateQuantityAndLocation() throws Exception {
        int locationId = 1;
        // Insert initial data for testing quantity update
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO LOCATION (ID, RACK, SHELF, PRODUCT_CODE, CAPACITY, QUANTITY) VALUES (1, 2, 3, 'A001', 100, 10)");
        }
 
        form.updateQuantityAndLocation(locationId);
 
        List<Location> locations = form.getAvailableLocations("A001");
        // Verify that quantity and product code match expected values
        assertEquals(10, locations.get(0).getQuantity());
        assertEquals("A001", locations.get(0).getProductCode());
    }
 
    @Test
    void testAssignRobotBasedOnCapacity() throws Exception {
        int weight = 50;
        // Insert test robot data
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO Robot (ID, CAPACITY, NAME) VALUES (1, 100, 'Robo1')");
        }
 
        Robot robot = form.assignRobotBasedOnCapacity(weight);
        // Ensure robot is assigned correctly based on capacity
        assertNotNull(robot);
        assertEquals("Robo1", robot.getName());
    }
 
    @Test
    void testGetProductWeight() throws Exception {
        String productCode = "A001";
        int expectedWeight = 20;
        // Insert test product data
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO products (product_code, weight) VALUES ('A001', 20)");
        }
 
        int weight = form.getProductWeight(productCode);
        // Verify that weight matches expected value
        assertEquals(expectedWeight, weight);
    }
 
    @Test
    void testDeliveryDepartments() throws Exception {
        // Insert test data into DeliveryDepartment table
        try (Connection conn = DatabaseConnect.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO DeliveryDepartment (DEPARTMENT_NAME) VALUES ('Electronics')");
            stmt.execute("INSERT INTO DeliveryDepartment (DEPARTMENT_NAME) VALUES ('Sales')");
        }
 
        List<String> departments = form.deliveryDeparments();
        // Check that all departments are retrieved
        assertTrue(departments.contains("Electronics"));
        assertTrue(departments.contains("Sales"));
    }
    @Test
    void testGetDeliveryInchargeBasedOnDepartmentName_DepartmentNotFound() throws Exception {
        // Test case to ensure no exception is thrown when a non-existent department is queried.
        String departmentName = "NonExistentDepartment";
        assertDoesNotThrow(() -> form.getDeliveryInchargeBasedOnDepartmentName(departmentName));
    }

    @Test
    void testGetAvailableLocations_EmptyResult() throws Exception {
        // Test case to verify that the method returns an empty list when a product code is not found.
        String productCode = "NonExistentCode";
        List<Location> locations = form.getAvailableLocations(productCode);
        assertTrue(locations.isEmpty());
    }

    @Test
    void testUpdateQuantityAndLocation_InvalidLocation() throws Exception {
        // Test case to ensure no exception is thrown when updating quantity for an invalid location ID.
        int invalidLocationId = 999;
        assertDoesNotThrow(() -> form.updateQuantityAndLocation(invalidLocationId));
    }

    @Test
    void testAssignRobotBasedOnCapacity_NoRobot() throws Exception {
        // Test case to validate that no robot is assigned if no robot can handle the given weight.
        int weight = 1000; // Assume no robot has this capacity.
        Robot robot = form.assignRobotBasedOnCapacity(weight);
        assertNull(robot);
    }

    @Test
    void testGetProductWeight_ProductNotFound() throws Exception {
        // Test case to ensure the method returns 0 weight when the product code does not exist.
        String productCode = "NonExistentCode";
        int weight = form.getProductWeight(productCode);
        assertEquals(0, weight);
    }

    @Test
    void testDeliveryDepartments_EmptyTable() throws Exception {
        // Test case to verify that an empty list is returned when there are no delivery departments.
        List<String> departments = form.deliveryDeparments();
        assertTrue(departments.isEmpty());
    }

    @Test
    void testValidLocation_EmptyList() {
        // Test case to check if a location is considered invalid when the location list is empty.
        List<Location> emptyLocations = new ArrayList<>();
        int locationId = 1;
        boolean isValid = form.validLocation(emptyLocations, locationId);
        assertFalse(isValid);
    }

 
 
     
}