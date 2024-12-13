package com.bosch.stocktoship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.service.ProductService;


//Author - Gurpartap Singh (INU2COB)


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/product/{id}")
	public Product getUniqueProductById(@PathVariable int id) {
		return productService.getProductById(id);
	}
	
}
