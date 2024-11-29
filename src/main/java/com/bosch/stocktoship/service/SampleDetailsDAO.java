package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.entity.SampleDetails;
import com.bosch.stocktoship.repository.DBConnection;

public class SampleDetailsDAO {

	private static final String INSERT_SAMPLE_QUERY = "INSERT INTO sample_details(sample_id, stage_name, part_number, sheet_metal_thickness, lubrication_condition, "
			+ "sheet_metal_strength, tool_geometry_die, tool_geometry_punch, punch_pressure, punch_speed, bend_radius, forming_force, "
			+ "paint_type, coating_thickness, alignment, functional_checks, ductility_of_welded_joint, welding_speed, welding_method) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_SAMPLE_QUERY = "UPDATE sample_details SET stage_name = ?, part_number = ?, sheet_metal_thickness = ?, lubrication_condition = ?, "
			+ "sheet_metal_strength = ?, tool_geometry_die = ?, tool_geometry_punch = ?, punch_pressure = ?, punch_speed = ?, "
			+ "bend_radius = ?, forming_force = ?, paint_type = ?, coating_thickness = ?, alignment = ?, functional_checks = ?, "
			+ "ductility_of_welded_joint = ?, welding_speed = ?, welding_method = ? WHERE sample_id = ?";

	private static final String DELETE_SAMPLE_QUERY = "DELETE FROM sample_details WHERE sample_id = ?";
	
	private static final String GET_SAMPLE_QUERY = "SELECT * FROM sample_details WHERE sample_id = ?";

	private static final String GET_ALL_SAMPLES_QUERY = "SELECT * FROM sample_details";

	public SampleDetails getSampleDetails(int sampleId) throws SQLException, ClassNotFoundException {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_SAMPLE_QUERY)) {

			preparedStatement.setInt(1, sampleId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return mapResultSetToSampleDetails(resultSet);
				}
			}
		}
		return null;
	}

	public List<SampleDetails> getAllSampleDetails() throws SQLException, ClassNotFoundException {
		List<SampleDetails> sampleDetailsList = new ArrayList<>();

		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_SAMPLES_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				SampleDetails sampleDetails = mapResultSetToSampleDetails(resultSet);
				sampleDetailsList.add(sampleDetails);
			}
		}
		return sampleDetailsList;
	}

	private SampleDetails mapResultSetToSampleDetails(ResultSet resultSet) throws SQLException {
		SampleDetails sampleDetails = new SampleDetails();

		sampleDetails.setSampleId(resultSet.getInt("sample_id"));
		sampleDetails.setStageName(resultSet.getString("stage_name"));
		sampleDetails.setSheetMetalThickness(resultSet.getDouble("sheet_metal_thickness"));
		sampleDetails.setLubricationCondition(resultSet.getString("lubrication_condition"));
		sampleDetails.setSheetMetalStrength(resultSet.getString("sheet_metal_strength"));
		sampleDetails.setToolGeometryDie(resultSet.getString("tool_geometry_die"));
		sampleDetails.setToolGeometryPunch(resultSet.getString("tool_geometry_punch"));
		sampleDetails.setPunchPressure(resultSet.getDouble("punch_pressure"));
		sampleDetails.setPunchSpeed(resultSet.getDouble("punch_speed"));
		sampleDetails.setBendRadius(resultSet.getDouble("bend_radius"));
		sampleDetails.setFormingForce(resultSet.getDouble("forming_force"));
		sampleDetails.setPaintType(resultSet.getString("paint_type"));
		sampleDetails.setCoatingThickness(resultSet.getDouble("coating_thickness"));
		sampleDetails.setAlignment(resultSet.getString("alignment"));
		sampleDetails.setFunctionalChecks(resultSet.getString("functional_checks"));
		sampleDetails.setDuctility(resultSet.getString("ductility_of_welded_joint"));
		sampleDetails.setWeldingSpeed(resultSet.getDouble("welding_speed"));
		sampleDetails.setWeldingMethod(resultSet.getString("welding_method"));

		PartDetails partDetails = new PartDetails();
		partDetails.setPartNumber(resultSet.getInt("part_number"));
		sampleDetails.setPartDetails(partDetails);

		return sampleDetails;
	}

	public int[] saveSampleDetails(List<SampleDetails> sampleDetailsList) throws ClassNotFoundException, SQLException {
	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SAMPLE_QUERY)) {

	        for (SampleDetails sampleDetails : sampleDetailsList) {
	            int partNumber = sampleDetails.getPartDetails().getPartNumber();
	            
	            if (!partDetailsExists(connection, partNumber)) {
	                throw new SQLException("PartDetails with partNumber " + partNumber + " does not exist.");
	            }

	            preparedStatement.setInt(1, sampleDetails.getSampleId());
	            preparedStatement.setString(2, sampleDetails.getStageName());
	            preparedStatement.setInt(3, partNumber); // Part number
	            preparedStatement.setDouble(4, sampleDetails.getSheetMetalThickness());
	            preparedStatement.setString(5, sampleDetails.getLubricationCondition());
	            preparedStatement.setString(6, sampleDetails.getSheetMetalStrength());
	            preparedStatement.setString(7, sampleDetails.getToolGeometryDie());
	            preparedStatement.setString(8, sampleDetails.getToolGeometryPunch());
	            preparedStatement.setDouble(9, sampleDetails.getPunchPressure());
	            preparedStatement.setDouble(10, sampleDetails.getPunchSpeed());
	            preparedStatement.setDouble(11, sampleDetails.getBendRadius());
	            preparedStatement.setDouble(12, sampleDetails.getFormingForce());
	            preparedStatement.setString(13, sampleDetails.getPaintType());
	            preparedStatement.setDouble(14, sampleDetails.getCoatingThickness());
	            preparedStatement.setString(15, sampleDetails.getAlignment());
	            preparedStatement.setString(16, sampleDetails.getFunctionalChecks());
	            preparedStatement.setString(17, sampleDetails.getDuctility());
	            preparedStatement.setDouble(18, sampleDetails.getWeldingSpeed());
	            preparedStatement.setString(19, sampleDetails.getWeldingMethod());

	            preparedStatement.addBatch();
	        }

	        int i[] = preparedStatement.executeBatch();
	        return i;
	    }
	}

	// Helper method to check if partDetails exists
	private boolean partDetailsExists(Connection connection, int partNumber) throws SQLException {
	    String checkQuery = "SELECT COUNT(*) FROM part_details WHERE part_number = ?";
	    try (PreparedStatement stmt = connection.prepareStatement(checkQuery)) {
	        stmt.setInt(1, partNumber);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0; // Return true if partDetails exists
	        }
	    }
	    return false; // PartDetails does not exist
	}

	public void updateSampleDetails(SampleDetails sampleDetails) throws ClassNotFoundException, SQLException {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SAMPLE_QUERY)) {

			preparedStatement.setString(1, sampleDetails.getStageName());
			preparedStatement.setInt(2, sampleDetails.getPartDetails().getPartNumber());
			preparedStatement.setDouble(3, sampleDetails.getSheetMetalThickness());
			preparedStatement.setString(4, sampleDetails.getLubricationCondition());
			preparedStatement.setString(5, sampleDetails.getSheetMetalStrength());
			preparedStatement.setString(6, sampleDetails.getToolGeometryDie());
			preparedStatement.setString(7, sampleDetails.getToolGeometryPunch());
			preparedStatement.setDouble(8, sampleDetails.getPunchPressure());
			preparedStatement.setDouble(9, sampleDetails.getPunchSpeed());
			preparedStatement.setDouble(10, sampleDetails.getBendRadius());
			preparedStatement.setDouble(11, sampleDetails.getFormingForce());
			preparedStatement.setString(12, sampleDetails.getPaintType());
			preparedStatement.setDouble(13, sampleDetails.getCoatingThickness());
			preparedStatement.setString(14, sampleDetails.getAlignment());
			preparedStatement.setString(15, sampleDetails.getFunctionalChecks());
			preparedStatement.setString(16, sampleDetails.getDuctility());
			preparedStatement.setDouble(17, sampleDetails.getWeldingSpeed());
			preparedStatement.setString(18, sampleDetails.getWeldingMethod());
			preparedStatement.setInt(19, sampleDetails.getSampleId());

			preparedStatement.executeUpdate();
		}
	}

	public int deleteSampleDetails(int sampleId) throws SQLException, ClassNotFoundException {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(DELETE_SAMPLE_QUERY)) {

			preparedStatement.setInt(1, sampleId);
			return preparedStatement.executeUpdate();
		}
	}
	private static final String GET_SAMPLES_BY_PART_NUMBER_QUERY = "SELECT * FROM sample_details WHERE part_number = ?";
	 
	public List<SampleDetails> getSamplesByPartNumber(int partNumber) throws SQLException, ClassNotFoundException {
	    List<SampleDetails> sampleDetailsList = new ArrayList<>();
 
	    try (Connection connection = DBConnection.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(GET_SAMPLES_BY_PART_NUMBER_QUERY)) {
 
	        preparedStatement.setInt(1, partNumber);
 
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                SampleDetails sampleDetails = mapResultSetToSampleDetails(resultSet);
	                sampleDetailsList.add(sampleDetails);
	            }
	        }
	    }
	    return sampleDetailsList;
	}
}
