
package com.bosch.stocktoship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
import com.bosch.stocktoship.entity.RAForm;
 
 
@Repository
public interface RequestAccessForm extends JpaRepository<RAForm , String> {
}