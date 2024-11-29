package com.bosch.stocktoship.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bosch.stocktoship.entity.GenerateBOM;
import com.bosch.stocktoship.entity.ItemCodeGeneration;
import com.bosch.stocktoship.repository.DBConnection;

/**
 * Service class for all the actions involving items and boms database
 * 
 * @author WIV1COB
 */
public class BOMRepositoryService {

	/**
	 * Method checks if the itemUniqueCode is available for items table
	 * 
	 * @param itemUniqueCode
	 * @return
	 * @throws SQLException
	 */
	public boolean isItemWIthIdAvailable(String itemUniqueCode) {
		try (Connection connection = DBConnection.getConnection()) {
			PreparedStatement preparedStatement = connection
					.prepareStatement("select * from items where item_unique_code = ?");
			preparedStatement.setString(1, itemUniqueCode);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Check if DB items have data with itemUniqueCode
	 * 
	 * @param itemUniqueCode
	 * @return true if present, false if absent
	 * @throws SQLException
	 */
	public ItemCodeGeneration getItemWithId(String itemUniqueCode) {
		try (Connection connection = DBConnection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"select category, dimensions, item_description, manufacturer, country_of_origin from items where item_unique_code = ?");
			statement.setString(1, itemUniqueCode);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return new ItemCodeGeneration(itemUniqueCode, resultSet.getString(1), resultSet.getString(2),
					resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void addGeneratedItemCode(ItemCodeGeneration itemGenerated) {
		try (Connection connection = DBConnection.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into items values(?,?,?,?,?,?)");
			preparedStatement.setString(1, itemGenerated.getItemUniqueCode());
			preparedStatement.setString(2, itemGenerated.getCategory());
			preparedStatement.setString(3, itemGenerated.getDimensions());
			preparedStatement.setString(4, itemGenerated.getItemDescription());
			preparedStatement.setString(5, itemGenerated.getManufacturer());
			preparedStatement.setString(6, itemGenerated.getCountryOfOrigin());
			preparedStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the BOM into DB
	 * 
	 * @param bomItem
	 * @throws SQLException for problems connecting to DB
	 */
	public void addItemToBOMDB(GenerateBOM bomItem) {
		try (Connection connection = DBConnection.getConnection()) {
			String itemCode = bomItem.getUniqueCode();
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into boms values(bom_seq.NEXTVAL,?,?,?,?)");
			preparedStatement.setString(1, itemCode);
			preparedStatement.setInt(2, bomItem.getQuantity());
			preparedStatement.setInt(3, bomItem.getSupplierCode());
			preparedStatement.setInt(4, bomItem.getSubAssembly());
			preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the BOM into DB
	 * 
	 * @param bomList list of boms
	 * @throws SQLException for problems connecting to DB
	 */
	public void addItemToBOMDB(List<GenerateBOM> bomList) throws SQLException {
		for (GenerateBOM bomItem : bomList) {
			addItemToBOMDB(bomItem);
		}
	}

	/**
	 * This will fetch the BOM which has been added to DB with matching item code
	 * 
	 * @param itemCode
	 * @return the BOM with itemCode
	 * @throws SQLException
	 */
	public GenerateBOM getBOMFromDB(String itemCode) {

		try (Connection connection = DBConnection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"select quantity, supplier_code, sub_assembly from boms where unique_item_code = ?");
			statement.setString(1, itemCode);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			GenerateBOM generateBOM = new GenerateBOM(itemCode, resultSet.getInt(1), resultSet.getInt(2),
					resultSet.getInt(3));
			ItemCodeGeneration itemCodeGeneration = getItemWithId(itemCode);
			if(itemCodeGeneration == null) {
				throw new SQLException();
			}
			generateBOM.setDimensions(itemCodeGeneration.getDimensions().split(" ")[0]);
			generateBOM.setUnitsOfMeasure(itemCodeGeneration.getDimensions().split(" ")[1]);

			return generateBOM;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<GenerateBOM> getAllBOMs(String uniqueItemCode) {
		List<GenerateBOM> generateBOM = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(
					"select quantity, supplier_code, sub_assembly from boms where unique_item_code=?");
			statement.setString(1, uniqueItemCode);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				generateBOM.add(
						new GenerateBOM(uniqueItemCode, resultSet.getInt(1), resultSet.getInt(2), resultSet.getInt(3)));
			}
			return generateBOM;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
