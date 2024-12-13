package com.bosch.stocktoship.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bosch.stocktoship.entity.DeliveryDepartment;

 
 
@Repository
public interface DeliveryDepartmentRepository extends JpaRepository<DeliveryDepartment, Integer> {
 
}
