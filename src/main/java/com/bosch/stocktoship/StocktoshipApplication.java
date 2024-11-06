package com.bosch.stocktoship;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bosch.stocktoship.service.*;


@SpringBootApplication
public class StocktoshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(StocktoshipApplication.class, args);
		
		ApplicationData  app = new ApplicationData(); // Create an instance of the Application class
	        app.start();
		
	}

}
