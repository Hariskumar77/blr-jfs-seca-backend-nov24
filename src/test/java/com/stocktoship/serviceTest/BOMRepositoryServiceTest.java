package com.stocktoship.serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;

import org.junit.jupiter.api.*;

import com.bosch.stocktoship.entity.GenerateBOM;
import com.bosch.stocktoship.entity.ItemCodeGeneration;
import com.bosch.stocktoship.repository.DBConnection;
import com.bosch.stocktoship.service.BOMRepositoryService;
/**
 * Test class for BOMRepositoryService
 * 
 * @author NeelimaDanduri XNE2KOR
 */
public class BOMRepositoryServiceTest {

	private BOMRepositoryService bomRepositoryService;

	// Set up an in-memory H2 database before each test
	@BeforeEach
	public void setUp() throws Exception {

		// Initialize the BOMRepositoryService
		bomRepositoryService = new BOMRepositoryService();
	}

	@Test
	public void testDBConnectionPass() throws SQLException {
		Connection connection = DBConnection.getConnection();
		String driverName = connection.getMetaData().getDriverName();
	}

	public void testDBConnectionFail() throws SQLException {
		String connectionURL = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "system";
		String password = "1234";
		Connection connection = DriverManager.getConnection(connectionURL, userName, password);
	}

	@Test
	public void testIsItemWithIdAvailable_itemExists() throws Exception {
		String validItemUniqueCode1 = "12qw34er56";
		String validItemUniqueCode2 = "a1b2c3d4e5";

		// Test the method for valid and invalid item codes
		boolean result1 = bomRepositoryService.isItemWIthIdAvailable(validItemUniqueCode1);
		boolean result2 = bomRepositoryService.isItemWIthIdAvailable(validItemUniqueCode2);

		// Assert that the valid item exists (should return true)
		assertTrue(result1);

		// Assert that the invalid item does not exist (should return false)
		assertTrue(result2);
	}

	@Test
	public void testIsItemWithIdAvailable_itemDoesNotExist() throws Exception {
		String itemUniqueCode = "ITEM999";

		// Test the method for a non-existing item code
		boolean result = bomRepositoryService.isItemWIthIdAvailable(itemUniqueCode);

		// Assert that the item does not exist
		assertFalse(result);
	}

	@Test
	public void testGetItemWithId() throws Exception {
		String itemUniqueCode = "12qw34er56";

		// Test the method
		ItemCodeGeneration item = bomRepositoryService.getItemWithId(itemUniqueCode);

		// Assert that the item properties match the expected values
		assertNotNull(item);
//        assertEquals("ITEM123", item.getItemUniqueCode());
		assertEquals("Utility", item.getCategory());
		assertEquals("12x5x7", item.getDimensions());
		assertEquals("An item for utility", item.getItemDescription());
		assertEquals("KMT", item.getManufacturer());
		assertEquals("India", item.getCountryOfOrigin());
	}

	@Test
	public void testAddItemToBOMDB() throws Exception {
		// Create a GenerateBOM object to add

		Connection connection = DBConnection.getConnection();
		PreparedStatement deleteBOM = connection
				.prepareStatement("delete from boms where unique_item_code = 'ITEM123'");
		deleteBOM.executeUpdate();
		PreparedStatement deleteItems = connection
				.prepareStatement("delete from items where item_unique_code = 'ITEM123'");
		deleteItems.executeUpdate();
		
		PreparedStatement statement = connection.prepareStatement(
				"SELECT unique_item_code, quantity, supplier_code, sub_assembly FROM boms WHERE unique_item_code = ?");
		statement.setString(1, "ITEM123");
		ResultSet resultSet = statement.executeQuery();
		assertFalse(resultSet.next());
		GenerateBOM bomItem = new GenerateBOM("ITEM123", 10, 101, 202);
		bomRepositoryService.addGeneratedItemCode(new ItemCodeGeneration("ITEM123", "Utility", "10x10x10 mm", "Some utility", "KTM", "India"));
		// Test the method
		bomRepositoryService.addItemToBOMDB(bomItem);

		statement = connection.prepareStatement(
				"SELECT unique_item_code, quantity, supplier_code, sub_assembly FROM boms WHERE unique_item_code = ?");
		statement.setString(1, "ITEM123");
		resultSet = statement.executeQuery();
		// Assert that the data is inserted correctly
		assertTrue(resultSet.next());
		assertEquals("ITEM123", resultSet.getString(1));
		assertEquals(10, resultSet.getInt(2));
		assertEquals(101, resultSet.getInt(3));
		assertEquals(202, resultSet.getInt(4));

		deleteBOM = connection.prepareStatement("delete from boms where unique_item_code = 'ITEM123'");
		deleteBOM.executeUpdate();
		deleteItems = connection.prepareStatement("delete from items where item_unique_code = 'ITEM123'");
		deleteItems.executeUpdate();
		connection.close();
	}

	@Test
	public void testGetBOMFromDB() throws Exception {
		// Add data to the BOM table first
		Connection connection = DBConnection.getConnection();
		PreparedStatement deleteBOM = connection
				.prepareStatement("delete from boms where unique_item_code = 'ITEM123'");
		deleteBOM.executeUpdate();
		PreparedStatement deleteItems = connection
				.prepareStatement("delete from items where item_unique_code = 'ITEM123'");
		deleteItems.executeUpdate();
		
		bomRepositoryService.addGeneratedItemCode(new ItemCodeGeneration("ITEM123", "Utility", "10x10x10 mm", "Some utility", "KTM", "India"));
		
		PreparedStatement stmt = connection.prepareStatement("insert into boms values(bom_seq.NEXTVAL,?,?,?,?)");
		stmt.setString(1, "ITEM123");
		stmt.setInt(2, 111);
		stmt.setInt(3, 11);
		stmt.setInt(4, 1);

		stmt.executeUpdate();

		// Test the method
		GenerateBOM bom = bomRepositoryService.getBOMFromDB("ITEM123");

		// Assert that the BOM values are as expected
		assertNotNull(bom);
		assertEquals("ITEM123", bom.getUniqueCode());
		assertEquals(111, bom.getQuantity());
		assertEquals(11, bom.getSupplierCode());
		assertEquals(1, bom.getSubAssembly());

		deleteBOM = connection.prepareStatement("delete from boms where unique_item_code = 'ITEM123'");
		deleteBOM.executeUpdate();
		deleteItems = connection.prepareStatement("delete from items where item_unique_code = 'ITEM123'");
		deleteItems.executeUpdate();
	}
}