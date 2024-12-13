package com.bosch.stocktoship;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StocktoshipApplication {
	static Scanner scanner = new Scanner(System.in);

	// Main method for running the Spring Boot application
	public static void main(String[] args) throws Exception {
		// Run the Spring Boot application
		SpringApplication.run(StocktoshipApplication.class, args);

		
	}
}
