package com.bosch.stocktoship.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";  // Modify with your actual DB details
    private static final String USER = "system";  // Replace with your Oracle username
    private static final String PASSWORD = "1234";  // Replace with your Oracle password

    // JDBC variables for opening, closing connection
    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null || connection.isClosed()) {
            // Load the Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
