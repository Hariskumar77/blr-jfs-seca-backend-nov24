package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import java.util.Set;

import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.repository.DatabaseConnect;

public class LocationAnalayzer {
    /**
     * @AUTHOR: CHARUL SAINI (SIC2COB)
     */
  
    Connection connection;

    /**
     * Fetches available and unavailable locations from the database.
     */
    public List<Location> fetchLocation() {
        Set<Location> locationSet = new HashSet<>();
        try {
            connection = DatabaseConnect.getConnection();

            // Fetch unavailable locations (those with a product assigned)
            String unavailable = "select rack,shelf from location where product_code IS NOT NULL ORDER BY DBMS_RANDOM.RANDOM";
            PreparedStatement preparedStatement = connection.prepareStatement(unavailable);
            ResultSet resultSet = preparedStatement.executeQuery();

            int rows = 0;
            while (rows < 3 && resultSet.next()) {
                int rack = resultSet.getInt(1);
                int shelf = resultSet.getInt(2);
                Location location = new Location(rack, shelf);
                locationSet.add(location);
                rows++;
            }

            // Fill the remaining locations with available ones (those without a product assigned)
            rows = 8 - rows;
            String available = "select rack,shelf from location where product_code IS NULL ORDER BY DBMS_RANDOM.RANDOM";

            PreparedStatement preparedStatement2 = connection.prepareStatement(available);
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while (rows > 0 && resultSet2.next()) {
                int rack = resultSet2.getInt(1);
                int shelf = resultSet2.getInt(2);
                Location location = new Location(rack, shelf);
                locationSet.add(location);
                rows--;
            }

        } catch (SQLException e) {
            System.out.println("Error in fetchLocation!!");
            e.printStackTrace();
        }

        return displayLocations(locationSet);
    }

    /**
     * Displays the fetched locations in a user-friendly format.
     */
    public List<Location> displayLocations(Set<Location> locationSet) {
        List<Location> locationList = new ArrayList<>();
        System.out.format("%-5s %s\n", "S no.", "Location");
        int sNo = 1;
        for (Location location : locationSet) {
            locationList.add(location);
            System.out.format("%-5s %s\n", sNo++, "Rack " + location.getRack() + ", Shelf " + location.getShelf());
        }

        return locationList;
    }

    /**
     * Updates the product code in the location and assigns quantity to the location.
     * Also assigns a robot based on product weight.
     */
    public boolean updateProductCodeInLocation(List<Location> locationList, Product product, int quantity, int locationInput) {

        try {
            connection = DatabaseConnect.getConnection();

            // Get the selected location based on user input
            Location location = locationList.get(locationInput - 1);
            int rack = location.getRack();
            int shelf = location.getShelf();

            // Check if the selected location is already occupied by another product
            String sql = "Select product_code from location where rack=? and shelf=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, rack);
            preparedStatement.setInt(2, shelf);
            ResultSet resultSet = preparedStatement.executeQuery();

            String productCode = null;
            if (resultSet.next())
                productCode = resultSet.getString(1);

            // If location is already occupied, return false
            if (productCode != null) {
                System.out.println("Place Unavailable");
                return false;
            }

            // Otherwise, assign the product code and quantity to the location
            productCode = product.getCode();
            String query = "Update location set product_code=? ,quantity =? where rack=? and shelf=?";
            PreparedStatement preparedStatement2 = connection.prepareStatement(query);
            preparedStatement2.setString(1, productCode);
            int lessQuantity = quantity < (10000 / product.getVolume()) ? quantity : (10000 / product.getVolume());
            preparedStatement2.setInt(2, lessQuantity);  // Update with available quantity
            preparedStatement2.setInt(3, rack);
            preparedStatement2.setInt(4, shelf);
            preparedStatement2.executeUpdate();
            System.out.println("Rack " + rack + ", Shelf " + shelf + " Blocked with product code: " + productCode);

            // Assign a robot based on the product's weight
            int weight = product.getWeight();
            Robot robot = assignRobotBasedOnCapacity(weight);
            displayRobotDetails(robot, productCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Displays the robot details assigned to handle a product.

     */
    private void displayRobotDetails(Robot robot, String productCode) {
        System.out.println("Robot " + robot.getName() + " assigned for Product Code: " + productCode);
    }

    /**
     * Assigns a robot with sufficient capacity to handle the given product weight.
     */
    public Robot assignRobotBasedOnCapacity(int weight) {
        Robot robot = null;
        try {
            connection = DatabaseConnect.getConnection();

            // Fetch the first robot with capacity greater than or equal to the product's weight
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

}
