package com.bosch.stocktoship.service;


import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.repository.RobotRepository;


//Author - Hariskumar S (ZEI1COB)

 
@Service
public class RobotService {
 
    @Autowired
    private RobotRepository robotRepository;
    public Robot getRobotByCapacity(int capacity) {
        List<Robot> robots = robotRepository.findByCapacityGreaterThanEqual(capacity);
        if (robots != null && !robots.isEmpty()) {
            return robots.get(0);  // Return the first robot in the list
        } else {
            throw new RuntimeException("No robot found with the specified capacity.");
        }
    }
}