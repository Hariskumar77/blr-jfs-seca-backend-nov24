package com.bosch.stocktoship;
/**
 * @author MKU1HYD
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.exception.MandatoryFieldMissingException;

public class MandatoryFieldMissingExceptionTest {

    @Test
    public void testMandatoryFieldMissingExceptionMessage() {
        try {
            // Simulate the condition where the mandatory field is missing
            throw new MandatoryFieldMissingException("Mandatory field is missing");

        } catch (MandatoryFieldMissingException e) {
            // Verify the exception message
            assertEquals("Mandatory field is missing", e.getMessage());
        }
    }

    @Test
    public void testMandatoryFieldMissingExceptionThrown() {
        try {
            // Act: Throw the exception
            throwMandatoryFieldMissingException();
            // If no exception is thrown, fail the test
            fail("Expected MandatoryFieldMissingException was not thrown.");
        } catch (MandatoryFieldMissingException e) {
            // Assert: Check if the exception message matches
            assertEquals("Mandatory field is missing", e.getMessage());
        }
    }

    // Helper method to simulate a condition that throws the exception
    private void throwMandatoryFieldMissingException() throws MandatoryFieldMissingException {
        throw new MandatoryFieldMissingException("Mandatory field is missing");
    }
}
