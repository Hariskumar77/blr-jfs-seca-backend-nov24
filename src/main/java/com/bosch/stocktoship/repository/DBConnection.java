package com.bosch.stocktoship.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class exclusively for establishing connection with database
 * 
 * @author WIV1COB
 */
public class DBConnection {

	public static Connection getConnection() throws SQLException {	
		String connectionURL = "jdbc:oracle:thin:@localhost:1522:xe";
		String userName = "system";
		String password = "1234";
		Connection connection = DriverManager.getConnection(connectionURL, userName, password);
		return connection;
	}
}
