package com.bosch.stocktoship.repository;


import org.springframework.stereotype.Repository;

import com.bosch.stocktoship.entity.Robot;

import java.util.List;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
 
@Repository
public interface RobotRepository extends JpaRepository<Robot, Integer> {
    List<Robot> findByCapacityGreaterThanEqual(int capacity);
 
	
}
