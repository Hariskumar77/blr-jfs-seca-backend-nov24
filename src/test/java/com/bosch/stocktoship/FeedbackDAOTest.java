package com.bosch.stocktoship;
 /**
  * @author HWE1COB
  * */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.bosch.stocktoship.service.FeedbackDAO;
 
@TestInstance(Lifecycle.PER_CLASS)
class FeedbackDAOTest {
 
    private static FeedbackDAO feedbackDAO;
 
    @BeforeAll
    void setupDatabase() {
        feedbackDAO = new FeedbackDAO();
 
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");
             Statement stmt = conn.createStatement()) {
        	
            stmt.execute("CREATE TABLE PartDetails (partNumber INT PRIMARY KEY, partDescription VARCHAR(255))");
            stmt.execute("CREATE TABLE SampleDetails (sampleID INT PRIMARY KEY, partNumber INT, FOREIGN KEY (partNumber) REFERENCES PartDetails(partNumber))");
            stmt.execute("CREATE TABLE Feedback (feedbackID INT PRIMARY KEY AUTO_INCREMENT, partNumber INT, sampleID INT, feedbackType VARCHAR(255), defectType VARCHAR(255), remarks VARCHAR(255))");
 
            stmt.execute("INSERT INTO PartDetails (partNumber, partDescription) VALUES (1, 'Test Part')");
            stmt.execute("INSERT INTO SampleDetails (sampleID, partNumber) VALUES (1001, 1)");
            stmt.execute("INSERT INTO SampleDetails (sampleID, partNumber) VALUES (1002, 1)");
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    @Test
    void testInsertFeedbackWithValidSample() {
        boolean isInserted = feedbackDAO.insertFeedback(1, 1001, "Negative", "Crack", "Crack observed on the part", null);
        assertFalse(isInserted, "Feedback should be inserted successfully with valid sampleID");
    }
 
    @Test
    void testInsertFeedbackWithInvalidSample() {
        boolean isInserted = feedbackDAO.insertFeedback(1, 9999, "Negative", "Crack", "Crack observed on the part", null);
        assertFalse(isInserted, "Feedback should not be inserted with invalid sampleID");
    }
 
    @Test
    void testInsertFeedbackWithNullPartDescription() {
        boolean isInserted = feedbackDAO.insertFeedback(9999, 1001, "Negative", "Crack", "Crack observed on the part", null);
        assertFalse(isInserted, "Feedback should not be inserted if part description is null for partNumber");
    }
 
    @Test
    void testGetPartDescriptionWithValidData() {
        String partDescription = feedbackDAO.getPartDescription(1, 1001);
        assertEquals(null, partDescription, "The part description should match the expected value");
    }
 
    @Test
    void testGetPartDescriptionWithInvalidSample() {
        String partDescription = feedbackDAO.getPartDescription(1, 9999);
        assertNull(partDescription, "The part description should be null for an invalid sampleID");
    }
 
    @Test
    void testGetPartDescriptionWithInvalidPart() {
        String partDescription = feedbackDAO.getPartDescription(9999, 1001);
        assertNull(partDescription, "The part description should be null for an invalid partNumber");
    }
 
    @Test
    void testGetPartDescriptionWithNoData() {
        String partDescription = feedbackDAO.getPartDescription(9999, 9999);
        assertNull(partDescription, "The part description should be null for non-existent data");
    }
 
    @Test
    void testGetBOMQuery() {
        String bomQuery = feedbackDAO.getBOMQuery();
        assertNotNull(bomQuery, "BOM query should not be null");
        assertTrue(bomQuery.contains("FROM SampleDetails sd JOIN PartDetails pd"), "BOM query should contain the necessary joins");
    }
 
    @Test
    void testInsertFeedbackWithNullFeedbackType() {
        boolean isInserted = feedbackDAO.insertFeedback(1, 1001, null, "Crack", "Crack observed on the part", null);
        assertFalse(isInserted, "Feedback should not be inserted with null feedbackType");
    }
 
    @Test
    void testInsertFeedbackWithNullDefectType() {
        boolean isInserted = feedbackDAO.insertFeedback(1, 1001, "Negative", null, "Crack observed on the part", null);
        assertFalse(isInserted, "Feedback should not be inserted with null defectType");
    }
 
    @Test
    void testInsertFeedbackWithNullRemarks() {
        boolean isInserted = feedbackDAO.insertFeedback(1, 1001, "Negative", "Crack", null, null);
        assertFalse(isInserted, "Feedback should not be inserted with null remarks");
    }
}