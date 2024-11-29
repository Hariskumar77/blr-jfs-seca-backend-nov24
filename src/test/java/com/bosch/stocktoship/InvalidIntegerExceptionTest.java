package com.bosch.stocktoship;

/**
 * @author MKU1HYD
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.exception.InvalidIntegerException;

public class InvalidIntegerExceptionTest {

    @Test
    public void testInvalidIntegerExceptionMessage() {
        // Define an error message for the exception
        String errorMessage = "Invalid integer value provided";

        // Create an instance of InvalidIntegerException with the test message
        InvalidIntegerException exception = new InvalidIntegerException(errorMessage);

        // Assert that the exception message is as expected
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testExceptionThrowing() {
        // Use assertThrows to verify that InvalidIntegerException is thrown
        InvalidIntegerException exception = assertThrows(InvalidIntegerException.class, () -> {
            // Simulate a condition where the exception would be thrown
            throw new InvalidIntegerException("Invalid integer value provided");
        });

        // Optionally, check the message of the exception
        assertEquals("Invalid integer value provided", exception.getMessage());
    }
}
