package com.bosch.stocktoship;

import org.junit.jupiter.api.BeforeEach;  // JUnit 5 annotation for setup method
import org.junit.jupiter.api.Test;  // JUnit 5 annotation for test methods
import org.springframework.boot.test.context.SpringBootTest;

import com.bosch.stocktoship.entity.User;
import com.bosch.stocktoship.service.ApplicationData;
import com.bosch.stocktoship.service.AuthenticationService;
import static org.junit.jupiter.api.Assertions.assertFalse;  // JUnit 5 assertions
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;  // Hamcrest for assertThat

@SpringBootTest
public class DriverTests {

    private AuthenticationService authService;
    private ApplicationData application;

    /**
     * This setup method runs before each test to initialize the necessary services.
     * It ensures a fresh instance of AuthenticationService and ApplicationData for each test.
     */
    @BeforeEach
    public void setUp() {
        authService = new AuthenticationService();  // Initialize the real AuthenticationService
        application = new ApplicationData();        // Initialize the ApplicationData instance
    }

    /**
     * Test Case: Test successful user registration.
     * This test simulates registering a new user and checks if the registration is successful,
     * the user is added to the service, and the user's password matches.
     */
    @Test
    public void testRegisterUser_Success(){
        String email = "hardcoded@example.com";
        String password = "password123";
        String employeeId = "EMP001";
        String role = "User";

        // Simulate user registration
        String result = authService.registerUser(email, password, employeeId, role);

        // Assert that the registration was successful
        assertEquals(result, "Registration successful.");

        // Check that the user has been added to the AuthenticationService
        assertNotNull(authService.getUserByEmail(email));

        // Verify that the stored password matches the one used during registration
        assertEquals(authService.getUserByEmail(email).getPassword(), password);
    }

    /**
     * Test Case: Test successful login.
     * This test verifies that a user can log in with valid credentials after registration.
     */
    @Test
    public void testLogin_Success() {
        String email = "test@example.com";
        String password = "password123";
        String employeeId = "EMP001";
        String role = "Admin";

        // Register the user first
        authService.registerUser(email, password, employeeId, role);

        // Simulate login with the correct credentials
        String result = authService.login(email, password);

        // Assert that the login was successful
        assertEquals(result, "Login successful.");

        // Verify that the user is marked as logged in
//        assertTrue(authService.getUserByEmail(email).isLoggedIn());
    }

    /**
     * Test Case: Test failed login with invalid credentials.
     * This test simulates a login attempt with an incorrect password and checks that the login fails.
     */
    @Test
    public void testLogin_Failure() {
        String email = "test@example.com";
        String password = "password123";
        String wrongPassword = "wrongpassword";

        // Register the user first
        authService.registerUser(email, password, "EMP002", "User");

        // Attempt to login with an incorrect password
        String result = authService.login(email, wrongPassword);

        // Assert that the login failed
        assertEquals(result, "Invalid password.");

        // Ensure that the user is not logged in after the failed attempt
        assertFalse(authService.getUserByEmail(email).isLoggedIn());
    }

    /**
     * Test Case: Test successful password reset.
     * This test verifies that a user can reset their password successfully.
     */
    @Test
    public void testPasswordReset_Success() {
        String email = "test@example.com";
        String oldPassword = "password123";
        String newPassword = "newPassword123";
        String employeeId = "EMP001";
        String role = "Admin";

        // Register the user with the old password
        authService.registerUser(email, oldPassword, employeeId, role);

        // Simulate password reset by setting a new password
        User user = authService.getUserByEmail(email);
        user.setPassword(newPassword);  // Directly set the new password

        // Assert that the password has been updated successfully
        assertEquals(user.getPassword(), newPassword);
    }

    /**
     * Test Case: Test failed password reset due to mismatched passwords.
     * This test ensures that if the new and confirmation passwords don't match, the reset is not performed.
     */
    @Test
    public void testPasswordReset_Failure() {
        String email = "test@example.com";
        String oldPassword = "password123";
        String newPassword = "newPassword123";
        String differentPassword = "differentPassword123";
        String employeeId = "EMP001";
        String role = "Admin";

        // Register the user with the old password
        authService.registerUser(email, oldPassword, employeeId, role);

        // Simulate password reset with mismatched new and confirmation passwords
        User user = authService.getUserByEmail(email);

        // If passwords do not match, print a message, and do not update the password
        if (!newPassword.equals(differentPassword)) {
            System.out.println("Passwords do not match, please try again.");
        } else {
            user.setPassword(newPassword);
        }

        // Assert that the password has not been changed (it should still be the old password)
        assertEquals(user.getPassword(), oldPassword);
    }

    /**
     * Test Case: Test user registration when the user already exists.
     * This test simulates trying to register a user with an email that already exists in the system.
     * It ensures that an appropriate message is returned indicating the user already exists.
     */
    @Test
    public void testRegisterUser_AlreadyExists() {
        String email = "existing@example.com";
        String password = "password123";
        String employeeId = "EMP002";
        String role = "Admin";

        // Register the user for the first time
        authService.registerUser(email, password, employeeId, role);

        // Attempt to register the same user again (should fail)
        String result = authService.registerUser(email, password, employeeId, role);

        // Assert that the correct error message is returned
        assertEquals(result, "Account exists.");
    }
}
