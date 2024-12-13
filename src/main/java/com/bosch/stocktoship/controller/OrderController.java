package com.bosch.stocktoship.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.stocktoship.entity.Order;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.repository.OrderRepository;
import com.bosch.stocktoship.service.ProductService;


//Author - Gurpartap Singh (INU2COB)

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class OrderController {
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/orders")
	public Order insertOrder(@RequestBody Order order) {
		Product productFound = productService.getProductById(order.getProductCode());
		if (productFound == null) {
			productService.save(new Product(order.getProductCode(), order.getName(), order.getLength(), order.getBreadth(), order.getHeight(), order.getWeight(), order.getVolume()));
		}
		return repository.save(order);
	}
	
	@GetMapping("/orders")
	public Iterable<Order> getAllOrders(){
		return repository.findAll();
	}
	
	
	
	
}
