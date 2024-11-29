package com.bosch.stocktoship;

/**
 * @author MKU1HYD
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.exception.InvalidCodeException;

public class InvalidCodeExceptionTest {

    @Test
    public void testInvalidCodeExceptionMessage() {
        // Test message for the exception
        String errorMessage = "Invalid code format";

        // Create the exception with the test message
        InvalidCodeException exception = new InvalidCodeException(errorMessage);

        // Check if the message in the exception matches the expected message
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    public void testExceptionThrowing() {
        // Use assertThrows to verify that InvalidCodeException is thrown
        assertThrows(InvalidCodeException.class, () -> {
            // Simulate a situation where the exception would be thrown
            throw new InvalidCodeException("Invalid code format");
        });
    }
}
