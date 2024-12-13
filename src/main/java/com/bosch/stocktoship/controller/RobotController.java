package com.bosch.stocktoship.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.service.RobotService;

 
//Author - Hariskumar S (ZEI1COB)
 
 
@Controller
@RequestMapping("/api/robots")
@CrossOrigin(origins = "http://localhost:4200")
public class RobotController {
 
	 @Autowired
	  private RobotService robotService;
	    @GetMapping("/capacity")
	    public ResponseEntity<Robot> getRobotByCapacity(@RequestParam int capacity) {
	        try {
	            Robot robot = robotService.getRobotByCapacity(capacity);
	            return ResponseEntity.ok(robot);
	        } catch (RuntimeException e) {
	            return ResponseEntity.notFound().build();
	        }
	    }
	}