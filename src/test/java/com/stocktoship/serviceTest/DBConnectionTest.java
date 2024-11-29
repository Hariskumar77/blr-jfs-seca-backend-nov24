package com.stocktoship.serviceTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.repository.DBConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * Test class for DBConnection
 * 
 * @author NeelimaDanduri XNE2KOR
 */
public class DBConnectionTest {

	private Connection connection;

	@BeforeEach
	public void setUp() throws SQLException {
		// Ensure the OracleDriver is loaded (optional if Class.forName is called in
		// DBConnection)
		connection = DBConnection.getConnection();
	}

	@AfterEach
	public void tearDown() throws SQLException {
		if (connection != null) {
			connection.close(); // Close the connection after each test
		}
	}

	@Test
	public void testGetConnection_ValidConnection() {
		assertNotNull(connection);
	}

	@Test
	public void testGetConnection_ValidDatabaseType() throws SQLException {
		assertEquals("Oracle", connection.getMetaData().getDatabaseProductName());
	}

	@Test
	public void testGetConnection_InvalidCredentials() throws SQLException {
		// Use incorrect credentials to simulate a failure
		String validURL = "jdbc:oracle:thin:@localhost:1521:xe";
		String validUsername = "system";
		String validPassword = "1234";

		// Trying to establish a connection with invalid credentials
		assertThrows(SQLException.class, () -> {
			DriverManager.getConnection(validURL, validUsername, validPassword);
		});

	}
}
