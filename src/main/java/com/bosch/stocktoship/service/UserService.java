package com.bosch.stocktoship.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.LoginRequest;
import com.bosch.stocktoship.entity.LoginResponse;
import com.bosch.stocktoship.entity.RAForm;
import com.bosch.stocktoship.entity.User;
import com.bosch.stocktoship.entity.UserDto;
import com.bosch.stocktoship.repository.RequestAccessForm;
import com.bosch.stocktoship.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    UserRepository userRepository;

    @Autowired
    RequestAccessForm requestAccessForm;
    
       public LoginResponse login(LoginRequest loginRequest) {
           User user = userRepository.findByUsername(loginRequest.getUsername());
           LoginResponse loginResponse = new LoginResponse();
           if(user == null)
           {
               loginResponse.setUser(user);
               loginResponse.setMessage("No user Found");
               return loginResponse;
           }

           if ( user.getPassword().equals(loginRequest.getPassword())) {
               loginResponse.setUser(user);
               loginResponse.setMessage("Login Successful");
               return loginResponse;
           } else {
               loginResponse.setUser(null);
               loginResponse.setMessage("Invalid Credential");
               return loginResponse;
           }
       }


       public RAForm reqAccessForm( RAForm request)
       {
           User user = userRepository.findByUsername(request.getName());
           if(user == null) {
               return null;

           }
           RAForm rAform = new RAForm(request.getRole() , request.getName() , request.getEmail(), request.getEmpId());
           requestAccessForm.save(rAform);
           return rAform;
       }

       public User newUser(UserDto request)
       {
           User newUser = new User(request.getUsername(), request.getPassword(), request.getName(), request.getEmail(), request.getUsertype());
           userRepository.save(newUser);

           return newUser;

       }

       public User finduserByUsername(String username)
       {
           User user = userRepository.findByUsername(username);
           return  user;
       }

       public User modifyUser( String username , String newPassword)
       {
           User user = userRepository.findByUsername(username);
           if(user == null)
               return null;

           user.setPassword(newPassword);
           userRepository.save(user);

           return user;
       }


   public User getUserByName( String username )
       {
          return  userRepository.findByUsername(username);
       }

       public List<User> getAllUsers()
       {
           return userRepository.findAll();
       }

       public List<RAForm> getAllUsersraf()
       {
           return requestAccessForm.findAll();
       }

     
       public User deleteUser(String userId)
       {
           User user = userRepository.findById(userId).get();
                   userRepository.deleteById(userId);
           return user;
       }


}
