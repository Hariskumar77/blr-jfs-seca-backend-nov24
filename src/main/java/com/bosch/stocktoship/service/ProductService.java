package com.bosch.stocktoship.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.repository.ProductRepository;



//Author - Gurpartap Singh (INU2COB)


@Service
public class ProductService {
	@Autowired
	ProductRepository productRepository;

	public Product save(Product product) {
		return productRepository.save(product);
	}
	
	public Product getProductById(int id) {
		try {
			return productRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return null;
		}
	}

}
