package com.bosch.stocktoship;
/**
 * @author MKU1HYD
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.ItemCodeGeneration;

public class ItemCodeGenerationTest {

    private ItemCodeGeneration item;

    @BeforeEach
    public void setUp() {
        // Initialize a new ItemCodeGeneration object before each test
        item = new ItemCodeGeneration("1234567890", "Electronics", "10x20x30 cm", 
                                      "Test Item", "Test Manufacturer", "Test Country");
    }

    @Test
    public void testConstructorAndGetters() {
        // Verify that the constructor correctly sets all fields
        assertEquals("1234567890", item.getItemUniqueCode());
        assertEquals("Electronics", item.getCategory());
        assertEquals("10x20x30 cm", item.getDimensions());
        assertEquals("Test Item", item.getItemDescription());
        assertEquals("Test Manufacturer", item.getManufacturer());
        assertEquals("Test Country", item.getCountryOfOrigin());
    }

    @Test
    public void testSetters() {
        // Use setters to modify values
        item.setItemUniqueCode("0987654321");
        item.setCategory("Furniture");
        item.setDimensions("15x25x35 inches");
        item.setItemDescription("New Item Description");
        item.setManufacturer("New Manufacturer");
        item.setCountryOfOrigin("New Country");

        // Verify that getters retrieve updated values
        assertEquals("0987654321", item.getItemUniqueCode());
        assertEquals("Furniture", item.getCategory());
        assertEquals("15x25x35 inches", item.getDimensions());
        assertEquals("New Item Description", item.getItemDescription());
        assertEquals("New Manufacturer", item.getManufacturer());
        assertEquals("New Country", item.getCountryOfOrigin());
    }

    @Test
    public void testDefaultConstructor() {
        // Create an object with the default constructor
        ItemCodeGeneration defaultItem = new ItemCodeGeneration();

        // Verify that all fields are null or default values
        assertEquals(null, defaultItem.getItemUniqueCode());
        assertEquals(null, defaultItem.getCategory());
        assertEquals(null, defaultItem.getDimensions());
        assertEquals(null, defaultItem.getItemDescription());
        assertEquals(null, defaultItem.getManufacturer());
        assertEquals(null, defaultItem.getCountryOfOrigin());
    }
}
