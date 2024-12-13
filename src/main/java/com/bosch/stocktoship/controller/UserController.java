package com.bosch.stocktoship.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.stocktoship.entity.LoginRequest;
import com.bosch.stocktoship.entity.LoginResponse;
import com.bosch.stocktoship.entity.RAForm;
import com.bosch.stocktoship.entity.User;
import com.bosch.stocktoship.entity.UserDto;
import com.bosch.stocktoship.service.UserService;



@CrossOrigin("http://localhost:4200")
@RestController
public class UserController {
	@Autowired
    UserService userservice;

	 @PostMapping("/login")
	    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
		 LoginResponse loginresponse=userservice.login(loginRequest);
		 return loginresponse;
	       
	    }

	    // to save the response for RequestAccessForm

	    @PostMapping("/raf")
	    public RAForm reqAccessForm(@RequestBody RAForm request)
	    {
	    	RAForm raform=userservice.reqAccessForm(request);
	    	return raform;
	    }

	    // to add new user
	    @PostMapping("/user")
	    public User newUser(@RequestBody UserDto request)
	    {
	       User user=userservice.newUser(request);
	       return user;

	    }

	    @PostMapping("/user/{username}")
	    public User finduserByUsername(@PathVariable("username") String username)
	    {
	    	User user=userservice.finduserByUsername(username);
	    	return user;
	        
	    }

	    // to modify user password
	    @PutMapping("/changepassword/{username}")
	    public User modifyUser(@PathVariable("username") String username ,@RequestBody String newPassword)
	    {
	    	User user =userservice.modifyUser(username,newPassword);
	    	return user;
	    }

	    // to get a user info by username
	    @GetMapping("/user/{username}")
	public User getUserByName( @PathVariable("username") String username )
	    {
	    	User user=userservice.getUserByName(username);
	    	return user;
	       
	    }

	    @GetMapping("/users")
	    public List<User> getAllUsers()
	    {
	        List<User> user=userservice.getAllUsers();
	        return user;
	    }

	    @GetMapping("/usersraf")
	    public List<RAForm> getAllUsersraf()
	    {
	    	List<RAForm> raform=userservice.getAllUsersraf();
	    	return raform;
	       
	    }

	    @DeleteMapping("/deluser/{userid}")
	    public User deleteUser(@PathVariable("userid") String userId)
	    {
	    	User user=userservice.deleteUser(userId);
	    	return user;
	        
	    }

}
