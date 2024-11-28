package com.bosch.stocktoship;
 
//Created by Aman Kumar Rai -- (Squad-1)
 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
 
import com.bosch.stocktoship.entity.User;
import com.bosch.stocktoship.repository.DBConnection;
import com.bosch.stocktoship.service.ApplicationData;
import com.bosch.stocktoship.service.AuthenticationService;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
 
 
@SpringBootTest
public class AuthenticationTest {
 
  private AuthenticationService authService;
  private ApplicationData app;
 
  @BeforeAll
  public static void setupClass() {
      try {
		DBConnection.getConnection();
	} catch (SQLException e) {
		e.printStackTrace();
	} 
  }
 
  @BeforeEach
  public void setUp() {
      authService = new AuthenticationService();
      app = new ApplicationData();
      // Clean up before tests
      deleteUser("testuser@example.com");
      deleteUser("existinguser@example.com");
  }
 
  @AfterEach
  public void tearDown() {
      deleteUser("testuser@example.com");
      deleteUser("existinguser@example.com");
  }
 
  // Helper method to delete a user by email from the database
  private void deleteUser(String email) {
      try (Connection connection = DBConnection.getConnection();
           PreparedStatement ps = connection.prepareStatement("DELETE FROM user_details WHERE email = ?")) {
          ps.setString(1, email);
          ps.executeUpdate();
      } catch (SQLException e) {
          e.printStackTrace();
      }
  }
//Test Case1: created by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testRegisterUser_Success() {
      String result = authService.registerUser("testuser@example.com", "password123", "EMP001", "User");
      assertEquals("Registration successful.", result);
  }
//Test Case2: created by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testRegisterUser_UserAlreadyExists() {
      // Pre-register a user
      authService.registerUser("existinguser@example.com", "password", "EMP002", "User");
      // Attempt to register the same user again
      String result = authService.registerUser("existinguser@example.com", "password", "EMP002", "User");
      assertEquals("Account exists.", result);
  }
 
  //Test Case3: Modified by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testLogin_Success() {
      authService.registerUser("testuser@example.com", "password123", "EMP001", "User");
      String result = authService.login("testuser@example.com", "password123");
      assertEquals("Login successful.", result);
  }
//Test Case4: created by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testLogin_InvalidPassword() {
      authService.registerUser("testuser@example.com", "password123", "EMP001", "User");
      String result = authService.login("testuser@example.com", "wrongpassword");
      assertEquals("Invalid password.", result);
  }
//Test Case5: created by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testLogin_UserDoesNotExist() {
      String result = authService.login("nonexistentuser@example.com", "password123");
      assertEquals("User doesn't exist.", result);
  }
  //Test Case6: Modified by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testChangePassword_Success() {
      authService.registerUser("testuser@example.com", "password123", "EMP001", "User");
      String result = authService.changePassword("testuser@example.com", "newpassword123");
      assertEquals("Password changed successfully", result);
 
      // Verify login with the new password
      String loginResult = authService.login("testuser@example.com", "newpassword123");
      assertEquals("Login successful.", loginResult);
  }
  //Test Case7: Modified by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testChangePassword_UserDoesNotExist() {
      String result = authService.changePassword("nonexistentuser@example.com", "newpassword123");
      assertEquals("No user found with the provided email", result);
  }
  //Test Case8: Modified by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testGetUserByEmail_UserExists() {
      authService.registerUser("testuser@example.com", "password123", "EMP001", "User");
      User user = authService.getUserByEmail("testuser@example.com");
      assertNotNull(user);
      assertEquals("testuser@example.com", user.getEmail());
      assertEquals("EMP001", user.getEmployeeId());
      assertEquals("User", user.getRole());
  }
//Test Case9: created by Aman Kumar Rai -- (Squad-1)
  @Test
  public void testGetUserByEmail_UserDoesNotExist() {
      User user = authService.getUserByEmail("nonexistentuser@example.com");
      assertNull(user);
  }
}