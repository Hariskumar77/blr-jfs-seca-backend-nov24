package com.bosch.stocktoship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.DefectType;
import com.bosch.stocktoship.entity.Feedback;
import com.bosch.stocktoship.service.FeedbackService;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTests {

    private Feedback feedback;
    private FeedbackService feedbackService;

    @BeforeEach
    public void setUp() {
        // Initialize the objects before each test
        feedback = new Feedback();
        feedbackService = new FeedbackService();
    }

    @Test
    public void testFeedbackGettersAndSetters() {
        // Set values for the feedback object
        feedback.setStage("Production");
        feedback.setPartNumber("PN1234");
        feedback.setPartDescription("Test Part");
        feedback.setFeedbackType("QA Failed");
        feedback.setDefectType(DefectType.DIMENSIONAL_ERROR);
        feedback.setRemarks("Check dimensions");

        // Validate that the getters return the correct values
        assertEquals("Production", feedback.getStage());
        assertEquals("PN1234", feedback.getPartNumber());
        assertEquals("Test Part", feedback.getPartDescription());
        assertEquals("QA Failed", feedback.getFeedbackType());
        assertEquals(DefectType.DIMENSIONAL_ERROR, feedback.getDefectType());
        assertEquals("Check dimensions", feedback.getRemarks());
    }

    @Test
    public void testFeedbackToString() {
        // Set values for feedback
        feedback.setStage("Testing");
        feedback.setPartNumber("PN5678");
        feedback.setPartDescription("Another Part");
        feedback.setFeedbackType("Met the Requirements");
        feedback.setDefectType(null); // No defect type
        feedback.setRemarks("All good");

        // Expected string output from the toString method
        String expectedString = "Stage: Testing\n" +
                                "Part Number: PN5678\n" +
                                "Part Description: Another Part\n" +
                                "Feedback: Met the Requirements\n" +
                                "Remarks: All good\n";
        
        // Assert that the toString method returns the expected string
        assertEquals(expectedString, feedback.toString());
    }

    @Test
    public void testDefectTypeFromChoice() {
        // Test the fromChoice method in DefectType enum
        assertEquals(DefectType.DIMENSIONAL_ERROR, DefectType.fromChoice(1));
        assertEquals(DefectType.DEFECTIVE_PART, DefectType.fromChoice(2));
        assertEquals(DefectType.MISSING_PART, DefectType.fromChoice(3));
        assertNull(DefectType.fromChoice(4));  // Invalid choice should return null
    }

    @Test
    public void testFeedbackServiceSubmitFeedback() {
        // Set values for feedback
        feedback.setStage("Design");
        feedback.setPartNumber("PN001");
        feedback.setPartDescription("Sample Part");
        feedback.setFeedbackType("QA Failed");
        feedback.setDefectType(DefectType.DEFECTIVE_PART);
        feedback.setRemarks("Part is defective");

        // Expected output from submitFeedback method
        String expectedOutput = "\n--- Feedback Submitted ---\n" +
                                "Stage: Design\n" +
                                "Part Number: PN001\n" +
                                "Part Description: Sample Part\n" +
                                "Feedback: QA Failed\n" +
                                "Defect Type: Defective Part\n" +
                                "Remarks: Part is defective\n";

        // Assert that the feedback submission output matches the expected string
        assertEquals(expectedOutput, feedbackService.submitFeedback(feedback));
    }

    @Test
    public void testFeedbackServiceGenerateBOM() {
        // Test the BOM generation output
        String expectedOutput = "BOM generated!!!";
        assertEquals(expectedOutput, feedbackService.generateBOM());
    }
}
