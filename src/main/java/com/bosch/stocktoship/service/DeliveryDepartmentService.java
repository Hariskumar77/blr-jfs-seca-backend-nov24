package com.bosch.stocktoship.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.DeliveryDepartment;
import com.bosch.stocktoship.repository.DeliveryDepartmentRepository;

 
//Author - Hariskumar S (ZEI1COB)

 
@Service
public class DeliveryDepartmentService {
 
    @Autowired
    private DeliveryDepartmentRepository deliveryDepartmentRepository;
    public List<DeliveryDepartment>getAllDepartments(){
        return deliveryDepartmentRepository.findAll();
 
	}
 
}