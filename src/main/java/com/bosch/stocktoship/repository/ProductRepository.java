package com.bosch.stocktoship.repository;

import org.springframework.data.repository.CrudRepository;

import com.bosch.stocktoship.entity.Product;


public interface ProductRepository extends CrudRepository<Product, Integer> {

}
