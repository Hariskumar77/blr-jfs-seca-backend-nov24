package com.bosch.stocktoship;

/**
 * @author MKU1HYD
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.exception.InvalidItemCodeException;

public class InvalidItemCodeExceptionTest {

    @Test
    public void testInvalidItemCodeExceptionMessage() {
        // Arrange: Define an expected error message
        String expectedMessage = "Invalid item code format";

        // Act: Create an InvalidItemCodeException with the expected message
        InvalidItemCodeException exception = new InvalidItemCodeException(expectedMessage);

        // Assert: Verify that the exception message matches the expected message
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void testExceptionIsThrown() {
        // Act & Assert: Use assertThrows to check that the exception is thrown
        InvalidItemCodeException exception = assertThrows(
            InvalidItemCodeException.class,
            () -> {
                // Simulate a condition that throws the exception
                throw new InvalidItemCodeException("Test message");
            }
        );

        // Optionally, verify the exception message if necessary
        assertEquals("Test message", exception.getMessage());
    }
}
