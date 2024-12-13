package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bosch.stocktoship.entity.DeliveryDepartment;
import com.bosch.stocktoship.entity.Location;
import com.bosch.stocktoship.entity.Product;
import com.bosch.stocktoship.entity.Robot;
import com.bosch.stocktoship.repository.DeliveryDepartmentRepository;
import com.bosch.stocktoship.repository.LocationRepository;
import com.bosch.stocktoship.repository.ProductRepository;
import com.bosch.stocktoship.repository.RobotRepository;
import com.bosch.stocktoship.service.DeliveryDepartmentService;
import com.bosch.stocktoship.service.LocationService;
import com.bosch.stocktoship.service.ProductService;
import com.bosch.stocktoship.service.RobotService;

@ExtendWith(MockitoExtension.class)
class UseCase2Testing {

//	AUTHOR - MUJAHID DAFEDAR (DAFB1KOR)
	
    // Mock repositories
    @Mock
    private DeliveryDepartmentRepository deliveryDepartmentRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private RobotRepository robotRepository;

    // Inject mocks into services
    @InjectMocks
    private DeliveryDepartmentService deliveryDepartmentService;

    @InjectMocks
    private LocationService locationService;

    @InjectMocks
    private RobotService robotService;

    // Tests for DeliveryDepartmentService
    @Test
    void testGetAllDepartments() {
        List<DeliveryDepartment> departments = Arrays.asList(
            new DeliveryDepartment(1, "Logistics", "John Doe"),
            new DeliveryDepartment(2, "Operations", "Jane Smith")
        );

        when(deliveryDepartmentRepository.findAll()).thenReturn(departments);

        List<DeliveryDepartment> result = deliveryDepartmentService.getAllDepartments();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Logistics", result.get(0).getDepartmentName());
        verify(deliveryDepartmentRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDepartments_EmptyList() {
        when(deliveryDepartmentRepository.findAll()).thenReturn(Arrays.asList());

        List<DeliveryDepartment> result = deliveryDepartmentService.getAllDepartments();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(deliveryDepartmentRepository, times(1)).findAll();
    }

    // Tests for LocationService
    @Test
    void testGetLocationsByProductCode() {
        int productCode = 101;
        Product mockProduct = new Product(productCode, "Test Product", 10, 20, 30, 40, 50);
        List<Location> mockLocations = Arrays.asList(
            new Location(1, 1, 1, mockProduct, 100, 50),
            new Location(2, 1, 2, mockProduct, 200, 150)
        );

        when(locationRepository.findByProductProductCode(productCode)).thenReturn(mockLocations);

        List<Location> result = locationService.getLocationsByProductCode(productCode);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(50, result.get(0).getQuantity());
        verify(locationRepository, times(1)).findByProductProductCode(productCode);
    }


    @Test
    void testUpdateLocationProductCode() {
        int locationId = 1;
        Product mockProduct = new Product(101, "Test Product", 10, 20, 30, 40, 50);
        Location mockLocation = new Location(locationId, 1, 1, mockProduct, 100, 50);

        when(locationRepository.findById(locationId)).thenReturn(Optional.of(mockLocation));
        when(locationRepository.save(mockLocation)).thenReturn(mockLocation);

        locationService.updateLocationProductCode(locationId);

        assertNull(mockLocation.getProductCode());
        verify(locationRepository, times(1)).findById(locationId);
        verify(locationRepository, times(1)).save(mockLocation);
    }

    @Test
    void testUpdateLocationProductCode_NotFound() {
        int locationId = 999;

        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> locationService.updateLocationProductCode(locationId)
        );

        assertEquals("Location not found with ID: 999", exception.getMessage());
        verify(locationRepository, times(1)).findById(locationId);
        verify(locationRepository, never()).save(any());
    }

    // Tests for RobotService
    @Test
    void testGetRobotByCapacity() {
        List<Robot> mockRobots = Arrays.asList(new Robot(1, 50, "Robot A"));
        when(robotRepository.findByCapacityGreaterThanEqual(40)).thenReturn(mockRobots);

        Robot result = robotService.getRobotByCapacity(40);

        assertNotNull(result);
        assertEquals("Robot A", result.getName());
        verify(robotRepository, times(1)).findByCapacityGreaterThanEqual(40);
    }

    @Test
    void testGetRobotByCapacityThrowsException() {
        when(robotRepository.findByCapacityGreaterThanEqual(100)).thenReturn(Arrays.asList());

        Exception exception = assertThrows(RuntimeException.class, 
            () -> robotService.getRobotByCapacity(100)
        );

        assertEquals("No robot found with the specified capacity.", exception.getMessage());
    }
    
// AUTHOR - CHARUL SAINI (SIC2COB)
    
    @InjectMocks
    private ProductService productService;
 
    @Mock
    private ProductRepository productRepository;
 
    private Product sampleProduct;
 
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Sample product setup
        sampleProduct = new Product(1, "Sample Product", 10, 20, 30, 40, 6000);
    }
 
    @Test
    void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);
 
        Product savedProduct = productService.save(sampleProduct);
 
        assertNotNull(savedProduct);
        assertEquals(1, savedProduct.getProductCode());
        assertEquals("Sample Product", savedProduct.getName());
        verify(productRepository, times(1)).save(sampleProduct);
    }
 
    @Test
    void testGetProductById_Success() {
        when(productRepository.findById(1)).thenReturn(Optional.of(sampleProduct));
 
        Product fetchedProduct = productService.getProductById(1);
 
        assertNotNull(fetchedProduct);
        assertEquals("Sample Product", fetchedProduct.getName());
        assertEquals(6000, fetchedProduct.getVolume());
        verify(productRepository, times(1)).findById(1);
    }
 
    @Test
    void testGetProductById_NotFound() {
        when(productRepository.findById(2)).thenReturn(Optional.empty());
 
        Product fetchedProduct = productService.getProductById(2);
 
        assertNull(fetchedProduct);
        verify(productRepository, times(1)).findById(2);
    }

    
}
