package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.repository.DBConnection;
	
public class PartDetailsDAO {

	private static final String INSERT_PART_QUERY = "INSERT INTO part_details(part_number, part_name, part_description) VALUES (?, ?, ?)";
	private static final String SELECT_PART_QUERY = "SELECT part_name, part_description FROM part_details WHERE part_number = ?";
	private static final String DELETE_PART_QUERY = "DELETE FROM part_details WHERE part_number = ?";

	public int savePartDetails(PartDetails partDetails) throws SQLException, ClassNotFoundException {
		int result = 0;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PART_QUERY)) {

			preparedStatement.setInt(1, partDetails.getPartNumber());
			preparedStatement.setString(2, partDetails.getPartName());
			preparedStatement.setString(3, partDetails.getPartDescription());
			result = preparedStatement.executeUpdate();
		}
		return result;
	}

	public PartDetails getPartDetails(int partNumber) throws SQLException, ClassNotFoundException {
		PartDetails partDetails = null;

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PART_QUERY)) {

			preparedStatement.setInt(1, partNumber);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					partDetails = new PartDetails(resultSet.getString("part_name"),
							resultSet.getString("part_description"));
				}
			}
		}

		return partDetails;
	}

	public void deletePartDetails(int partNumber) throws SQLException, ClassNotFoundException {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PART_QUERY)) {

			preparedStatement.setInt(1, partNumber);
			preparedStatement.executeUpdate();
		}
	}
}

