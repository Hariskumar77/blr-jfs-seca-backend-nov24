package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.bosch.stocktoship.repository.DatabaseConnect;

public class DeliveryDepartmentDAO {
	/*
	 * AUTHOR: DAFEDAR MUJAHID
	 */
	Connection con;

	public void insertDeliveryDepartmentDetails() {
		try {

			con = DatabaseConnect.getConnection();

			String query = "TRUNCATE table deliveryDepartment";
			Statement statement = con.createStatement();
			statement.executeUpdate(query);

			List<String> departmentDetails = Arrays.asList(
					"INSERT INTO deliveryDepartment VALUES (1, 'HealthCare', 'Alice')",
					"INSERT INTO deliveryDepartment VALUES (2, 'Clothes', 'Diana')",
					"INSERT INTO deliveryDepartment VALUES (3, 'Electronics', 'Eve')",
					"INSERT INTO deliveryDepartment VALUES (4, 'Groceries', 'Ivy')",
					"INSERT INTO deliveryDepartment VALUES (5, 'Sports', 'Kohli')");

			for (String row : departmentDetails) {
				Statement statement2 = con.createStatement();
				statement2.executeUpdate(row);
				statement2.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
