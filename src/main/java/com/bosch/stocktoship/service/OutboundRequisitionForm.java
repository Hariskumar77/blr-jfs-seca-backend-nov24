package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.repository.DatabaseConnect;

public class OutboundRequisitionForm {
    /*
     * AUTHOR: DAFEDAR MUJAHID
     * Class provides methods for managing outbound requisitions, including 
     * fetching incharge details, locations, robots, and updating inventory data.
     */

    /**
     * Fetches the delivery incharge name for a given department.
     * @param departmentName The name of the department.
     * @throws SQLException If a database access error occurs.
     */
    public void getDeliveryInchargeBasedOnDepartmentName(String departmentName) throws SQLException {
        String sql = "SELECT * FROM DeliveryDepartment WHERE DEPARTMENT_NAME = ?";
        try {
            Connection connection = DatabaseConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, departmentName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Department Incharge : " + resultSet.getString(3));
            } else {
                System.out.println("Department does not exist");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all available locations for a given product code.
     * @param productCode The code of the product.
     * @return List of locations containing the product.
     * @throws SQLException If a database access error occurs.
     */
    public List<Location> getAvailableLocations(String productCode) throws SQLException {
        String sql = "SELECT * FROM LOCATION WHERE PRODUCT_CODE = ?";
        List<Location> locations = new ArrayList<>();
        try {
            Connection connection = DatabaseConnect.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCode);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Location location = new Location();
                location.setId(resultSet.getInt(1));
                location.setRack(resultSet.getInt(2));
                location.setShelf(resultSet.getInt(3));
                location.setProductCode(resultSet.getString(4));
                location.setCapacity(resultSet.getInt(5));
                location.setQuantity(resultSet.getInt(6));
                locations.add(location);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return locations;
    }

    /**
     * Updates the quantity and clears the product code for a given location.
     * @param locationId The ID of the location to update.
     * @throws SQLException If a database access error occurs.
     */
    public void updateQuantityAndLocation(int locationId) throws SQLException {
        String sql = "UPDATE location SET QUANTITY = 0, product_code=NULL WHERE LOCATION_ID =" + locationId;
        try {
            Connection connection = DatabaseConnect.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Assigns a robot based on the weight capacity requirement.
     * @param weight The weight that the robot needs to handle.
     * @return A Robot instance that meets the capacity requirement, or null if unavailable.
     */
    public Robot assignRobotBasedOnCapacity(int weight) {
        Robot robot = null;
        try {
            Connection connection = DatabaseConnect.getConnection();
            String query = "Select * from robot where capacity>=? fetch first 1 row only";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, weight);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                robot = new Robot(resultSet.getInt(1), resultSet.getInt(2), resultSet.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return robot;
    }

    /**
     * Displays robot details and the product it is assigned to handle.
     * @param robot The robot instance.
     * @param productCode The product code assigned to the robot.
     */
    public void displayRobotDetails(Robot robot, String productCode) {
        System.out.println("Robot " + robot.getName() + " assigned for Product Code :" + productCode);
    }

    /**
     * Fetches the weight of a product based on its product code.
     * @param productCode The product code.
     * @return The weight of the product, or 0 if not found.
     */
    public int getProductWeight(String productCode) {
        int weight = 0;

        try (Connection connection = DatabaseConnect.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement("SELECT weight FROM products WHERE product_code = ?")) {

            preparedStatement.setString(1, productCode);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    weight = resultSet.getInt("weight");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error occurred while fetching product weight: " + e.getMessage());
        }

        return weight;
    }

    /**
     * Retrieves the list of all delivery department names.
     * @return List of department names.
     */
    public List<String> deliveryDeparments() {
        List<String> departmentList = new ArrayList<>();
        try {
            Connection connection = DatabaseConnect.getConnection();
            String sql = "Select DEPARTMENT_NAME from deliveryDepartment";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                departmentList.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departmentList;
    }

    /**
     * Checks if a given location ID is valid within the list of locations.
     * @param locations The list of locations to search within.
     * @param locationId The ID of the location to validate.
     * @return True if the location ID exists in the list, otherwise false.
     */
    public boolean validLocation(List<Location> locations, int locationId) {
        for (Location location : locations) {
            if (location.getId() == locationId)
                return true;
        }
        return false;
    }
}
