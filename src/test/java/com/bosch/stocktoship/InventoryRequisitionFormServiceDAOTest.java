package com.bosch.stocktoship;

import com.bosch.stocktoship.entity.InventoryRequistionForm;
import com.bosch.stocktoship.repository.DBConnection;
import com.bosch.stocktoship.service.InventoryRequisitionFormServiceDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for InventoryRequisitionFormServiceDAO.
 * Provides unit tests for saving InventoryRequisitionForm entries with different statuses.
 * 
 * @author ANET1KOR
 */
public class InventoryRequisitionFormServiceDAOTest {

    // DAO for inventory requisition service
    private InventoryRequisitionFormServiceDAO inventoryServiceDAO;

    // Set up DAO before each test
    @BeforeEach
    public void setup() {
        inventoryServiceDAO = new InventoryRequisitionFormServiceDAO();
    }

    // Test for saving an InventoryRequisitionForm with a null status
    @Test
    public void testSaveInventoryRequisitionForm() throws SQLException, ParseException {
        // Prepare date format and parse test dates
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = formatDate.parse("12.12.2025");
        Date date2 = formatDate.parse("01.02.2026");

        // Establish database connection and clean up any existing test data
        Connection connection = DBConnection.getConnection();
        PreparedStatement deleteForm = connection.prepareStatement("delete from InventoryRequisitionForm where unique_item_code = '0123456789'");
        deleteForm.executeQuery();

        // Create a test form and save it with an empty status
        InventoryRequistionForm form = new InventoryRequistionForm("Dept1", date1, "0123456789", "random description", 111, "cm", "No purpose", "Dept2", date2, "No comments all good", false);
        inventoryServiceDAO.saveInventoryRequisitionForm(form, "");

        // Verify the saved form status is "null"
        PreparedStatement preparedStatement = connection.prepareStatement("select status from InventoryRequisitionForm where unique_item_code = '0123456789'");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("null", resultSet.getString(1));

        // Clean up test data
        deleteForm = connection.prepareStatement("delete from InventoryRequisitionForm where unique_item_code = '0123456789'");
        deleteForm.executeQuery();
        connection.close();
    }

    // Test for saving an InventoryRequisitionForm with an approved status
    @Test
    public void testSaveInventoryRequisitionFormApproved() throws SQLException, ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = formatDate.parse("12.12.2025");
        Date date2 = formatDate.parse("01.02.2026");

        Connection connection = DBConnection.getConnection();
        PreparedStatement deleteForm = connection.prepareStatement("delete from InventoryRequisitionFormApproved where unique_item_code = '0123456789'");
        deleteForm.executeQuery();

        // Create a test form with an approved status and save it
        InventoryRequistionForm form = new InventoryRequistionForm("Dept1", date1, "0123456789", "random description", 111, "cm", "No purpose", "Dept2", date2, "No comments all good", true);
        inventoryServiceDAO.saveInventoryRequisitionForm(form, "APPROVED");

        // Verify the saved form status is "Approved"
        PreparedStatement preparedStatement = connection.prepareStatement("select status from InventoryRequisitionFormApproved where unique_item_code = '0123456789'");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Approved", resultSet.getString(1));

        // Clean up test data
        deleteForm = connection.prepareStatement("delete from InventoryRequisitionFormApproved where unique_item_code = '0123456789'");
        deleteForm.executeQuery();
        connection.close();
    }

    // Test for saving an InventoryRequisitionForm with a rejected status
    @Test
    public void testSaveInventoryRequisitionFormRejected() throws SQLException, ParseException {
        SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
        Date date1 = formatDate.parse("12.12.2025");
        Date date2 = formatDate.parse("01.02.2026");

        Connection connection = DBConnection.getConnection();
        PreparedStatement deleteForm = connection.prepareStatement("delete from InventoryRequisitionFormRejected where unique_item_code = '0123456789'");
        deleteForm.executeQuery();

        // Create a test form with a rejected status and save it
        InventoryRequistionForm form = new InventoryRequistionForm("Dept1", date1, "0123456789", "random description", 111, "cm", "No purpose", "Dept2", date2, "No comments all good", false);
        inventoryServiceDAO.saveInventoryRequisitionForm(form, "REJECTED");

        // Verify the saved form status is "Rejected"
        PreparedStatement preparedStatement = connection.prepareStatement("select status from InventoryRequisitionFormRejected where unique_item_code = '0123456789'");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        assertEquals("Rejected", resultSet.getString(1));

        // Clean up test data
        deleteForm = connection.prepareStatement("delete from InventoryRequisitionFormRejected where unique_item_code = '0123456789'");
        deleteForm.executeQuery();
        connection.close();
    }
}
