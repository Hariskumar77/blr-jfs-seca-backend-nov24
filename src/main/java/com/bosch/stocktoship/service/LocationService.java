package com.bosch.stocktoship.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.repository.LocationRepository;


//Author - Hariskumar S (ZEI1COB)
//Author - Gurpartap Singh (INU2COB)


@Service
public class LocationService { 
	@Autowired
	LocationRepository locationRepository;
	
	public Location getLocationById(int id) {
		try {
			return locationRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public void updateLocationProductCode(int locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found with ID: " + locationId));

        location.setProductCode(null); // Set the product code to null
        location.setOccupied(false);
        locationRepository.save(location); // Save the updated location
    }

    public List<Location> getLocationsByProductCode(int productCode) {
        return locationRepository.findByProductProductCode(productCode);
    }

}
