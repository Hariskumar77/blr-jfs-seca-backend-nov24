package com.bosch.stocktoship.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.stocktoship.entity.DeliveryDepartment;
import com.bosch.stocktoship.service.DeliveryDepartmentService;




 
 
// Author - Hariskumar S (ZEI1COB)

@RestController
@RequestMapping("/api/deliveryDepartments")
@CrossOrigin(origins = "http://localhost:4200")
public class DeliveryDepartmentController {
 
	   @Autowired
	    private DeliveryDepartmentService deliveryDepartmentService;
	   @GetMapping
	   public List<DeliveryDepartment> getAllDepartments(){
		   return deliveryDepartmentService.getAllDepartments();
	   }
}