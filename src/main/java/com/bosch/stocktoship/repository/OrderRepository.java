package com.bosch.stocktoship.repository;


import org.springframework.data.repository.CrudRepository;

import com.bosch.stocktoship.entity.Order;


public interface OrderRepository extends CrudRepository<Order, Integer> {
	
}
