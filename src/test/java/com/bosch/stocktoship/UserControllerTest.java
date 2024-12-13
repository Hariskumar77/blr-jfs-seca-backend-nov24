// Test case for Use Case -1 Created by Aakriti Sinha Squad -1
 
package com.bosch.stocktoship;
 
import com.bosch.stocktoship.entity.LoginRequest;
import com.bosch.stocktoship.entity.LoginResponse;
import com.bosch.stocktoship.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
 
 
// Importing static methods for mocking and assertions
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
 
@SpringBootTest // Annotation for Spring Boot test context
@AutoConfigureMockMvc  // Configures MockMvc automatically for testing
public class UserControllerTest {
 
    @Autowired
    private MockMvc mockMvc;  // MockMvc is injected for simulating HTTP requests
 
    @MockBean
    private UserService userService;  // MockBean to mock the UserService layer
 
    @Test
    public void testLogin_Success() throws Exception {
        // Given: Setting up test data and expected response
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser"); // Simulating a valid username
        loginRequest.setPassword("testPassword"); // Simulating a valid password
 
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(null);  // Mocking successful login; user field can be adjusted
        loginResponse.setMessage("Login Successful"); // Expected message on successful login
 
        // When: Mocking the UserService's login method to return the expected response
        when(userService.login(any(LoginRequest.class))).thenReturn(loginResponse);
 
        // Then: Sending a POST request to /login and verifying the response
        mockMvc.perform(post("/login")
                        .contentType("application/json") // Specifying the content type as JSON
                        .content("{\"username\":\"testUser\", \"password\":\"testPassword\"}")) // JSON payload
                .andExpect(status().isOk()) // Expecting HTTP 200 OK status
                .andExpect(jsonPath("$.message").value("Login Successful")); // Verifying the response message
    }
 
    @Test
    public void testLogin_Failure() throws Exception {
        // Given: Setting up test data and expected response for invalid credentials
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser"); // Simulating a valid username
        loginRequest.setPassword("wrongPassword"); // Simulating an invalid password
 
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setMessage("Invalid Credential"); // Expected message on login failure
 
        // When: Mocking the UserService's login method to return the failure response
        when(userService.login(any(LoginRequest.class))).thenReturn(loginResponse);
 
        // Then: Sending a POST request to /login and verifying the response
        mockMvc.perform(post("/login")
                        .contentType("application/json") // Specifying the content type as JSON
                        .content("{\"username\":\"testUser\", \"password\":\"wrongPassword\"}")) // JSON payload
                .andExpect(status().isOk()) // Expecting HTTP 200 OK status
                .andExpect(jsonPath("$.message").value("Invalid Credential")); // Verifying the response message
    }
}