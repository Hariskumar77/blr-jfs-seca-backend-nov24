package com.bosch.stocktoship.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bosch.stocktoship.entity.Location;


public interface LocationRepository extends CrudRepository<Location, Integer> {
	
    List<Location> findByProductProductCode(int productCode);
}
