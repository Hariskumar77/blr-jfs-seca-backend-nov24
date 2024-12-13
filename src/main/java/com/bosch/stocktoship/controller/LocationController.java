package com.bosch.stocktoship.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.repository.LocationRepository;
import com.bosch.stocktoship.service.LocationService;
import com.bosch.stocktoship.service.ProductService;


// Author - Hariskumar S (ZEI1COB)
// Author - Gurpartap Singh (INU2COB)

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {
	@Autowired
	LocationRepository repository;

	@Autowired
	LocationService locationService;

	@Autowired
	ProductService productService;

	@PostMapping("/locationsAll")
	public ResponseEntity<Iterable<Location>> insertLocations(@RequestBody List<Location> locations) {
		try {
			Iterable<Location> savedLocations = repository.saveAll(locations);
			return ResponseEntity.ok(savedLocations); // Return HTTP 200 with the saved locations
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Handle and log exceptions as 
																						// needed
		}
	}

	@GetMapping("/locations")
	public Iterable<Location> getAllLocations() {
		return repository.findAll();
	}

	@PutMapping("/locationAssignment")
	public Iterable<Location> assignProductToLocation(@RequestBody LocationRequest locationRequest) {
		int productCode = locationRequest.getProductCode();
		Product product = productService.getProductById(productCode);
		System.out.println(productCode);
		System.out.println(product);
		List<Location> locations = new ArrayList<>();
		for (Integer locationId : locationRequest.getLocationIds()) {
			Location location = locationService.getLocationById(locationId);
			if (product == null) {
				System.out.println("code is here");
				location.setOccupied(true);
			} else {
				location.setProductCode(product);
				location.setOccupied(true);
			}
			locations.add(location);
		}
		for (Location location : locations) {
			System.out.println(location);
		}
		return repository.saveAll(locations);
	}

	@GetMapping("/locations/product/{productCode}")
	public List<Location> getLocationsByProductCode(@PathVariable int productCode) {
		System.out.println("Running");
		return locationService.getLocationsByProductCode(productCode);
	}

	@PutMapping("/locations/update")
    public void updateLocationProductCode(@RequestBody List<Integer> locationIds) {
        try {
        	System.out.println(locationIds);
        	for(Integer locationId:locationIds) {
            locationService.updateLocationProductCode(locationId);
        	}
           System.out.println("Product codes for the selected locations have been updated.");
        } catch (Exception e) {
            System.out.println("Error while updating product codes: " + e.getMessage());
        }
    }

}

class LocationRequest {
	private List<Integer> locationIds;
	private int productCode;

	// Getters and setters
	public List<Integer> getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(List<Integer> locationIds) {
		this.locationIds = locationIds;
	}

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
}
