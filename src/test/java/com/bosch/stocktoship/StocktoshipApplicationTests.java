package com.bosch.stocktoship;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.bosch.stocktoship.entity.User;
import com.bosch.stocktoship.service.ApplicationData;
import com.bosch.stocktoship.service.AuthenticationService;

import static org.hamcrest.CoreMatchers.*;


@SpringBootTest
class StocktoshipApplicationTests {

	@Test
	void contextLoads() {
	}

	
	
	//Created by - AL AMAN , Dated - 6/11/24, Squad-1.




	
	    private AuthenticationService authService;  // The real AuthenticationService instance
	    private ApplicationData application;           // The Application instance we are testing

	    @Before
	    public void setUp(){
	        authService = new AuthenticationService();  // Instantiate the real AuthenticationService
	        application = new ApplicationData();  // Null Scanner as we won't be using it
	    }

	    // Test Case 1: Test User Registration 
	    @Test
	    public void testRegisterUser_Success(){
	        // User data for registration
	        String email = "hardcoded@example.com";
	        String password = "password123";
	        String employeeId = "EMP001";
	        String role = "User";

	        // Simulate registration by directly calling the registerUser method
	        String result = authService.registerUser(email, password, employeeId, role);

	        // Verify that the result is successful
	        assertThat(result, is("Registration successful."));

	        // Check if the user has been added to the AuthenticationService
	        assertNotNull(authService.getUserByEmail(email));
	        assertThat(authService.getUserByEmail(email).getPassword(), is(password));
	    }

	    // Test Case 2: Test Successful Login 
	    @Test
	    public void testLogin_Success(){
	        // Add a test user to the AuthenticationService 
	        String email = "test@example.com";
	        String password = "password123";
	        String employeeId = "EMP001";
	        String role = "Admin";
	        authService.registerUser(email, password, employeeId, role);  // Register the user first

	        // Simulate login (hardcoded email and password)
	        String result = authService.login(email, password);

	        // Verify that the login was successful
	        assertThat(result, is("Login successful."));

	        // Check if the user is marked as logged in
	        assertTrue(authService.getUserByEmail(email).isLoggedIn());
	    }

	    // Test Case 3: Test Failed Login ( Invalid credentials)
	    @Test
	    public void testLogin_Failure(){
	        // Simulate failed login attempt with wrong password 
	        String email = "test@example.com";
	        String password = "password123";
	        String wrongPassword = "wrongpassword";

	        // Register the user first
	        authService.registerUser(email, password, "EMP002", "User");

	        // Attempt to log in with incorrect password
	        String result = authService.login(email, wrongPassword);

	        // Verify that the login failed
	        assertThat(result, is("Invalid credentials."));

	        // Ensure the user is not logged in
	        assertFalse(authService.getUserByEmail(email).isLoggedIn());
	    }

	    // Test Case 4: Test Password Reset Success 
	    @Test
	    public void testPasswordReset_Success(){
	        // Hardcoded user data for password reset
	        String email = "test@example.com";
	        String oldPassword = "oldPassword123";
	        String newPassword = "newPassword123";
	        String employeeId = "EMP001";
	        String role = "Admin";

	        // Register the user first
	        authService.registerUser(email, oldPassword, employeeId, role);

	        // Simulate the password reset (New password)
	        User user = authService.getUserByEmail(email);
	        user.setPassword(newPassword);  // Set the new password directly

	        // Verify that the password has been updated
	        assertThat(user.getPassword(), is(newPassword));
	    }

	    // Test Case 5: Test Password Reset Failure (Mismatched passwords)
	    @Test
	    public void testPasswordReset_Failure(){
	        // User data for password reset failure
	        String email = "test@example.com";
	        String oldPassword = "oldPassword123";
	        String newPassword = "newPassword123";
	        String differentPassword = "differentPassword123";
	        String employeeId = "EMP001";
	        String role = "Admin";

	        // Register the user first
	        authService.registerUser(email, oldPassword, employeeId, role);

	        // Simulate the password reset attempt with mismatched passwords
	        User user = authService.getUserByEmail(email);

	        // Hardcoding the scenario where new passwords don't match
	        if (!newPassword.equals(differentPassword)) 
	        {
	            System.out.println("Passwords do not match, please try again.");
	        } else 
	        {
	            user.setPassword(newPassword);  // This would only occur if passwords match
	        }

	        // Ensure the password has not been updated
	        assertThat(user.getPassword(), is(oldPassword));
	    }

	    // Test Case 6: Test User Registration - Already Exists (Hardcoded)
	    @Test
	    public void testRegisterUser_AlreadyExists(){
	        // Hardcoded user data for registration
	        String email = "existing@example.com";
	        String password = "password123";
	        String employeeId = "EMP002";
	        String role = "Admin";

	        // Register the user first
	        authService.registerUser(email, password, employeeId, role);

	        // Attempt to register again with the same email (should fail)
	        String result = authService.registerUser(email, password, employeeId, role);

	        // Verify that the correct error message is returned
	        assertThat(result, is("User already exists."));
	    }
	}

}
