package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author MKU1HYD
 */

import com.bosch.stocktoship.entity.GenerateBOM;



public class GenerateBOMTest {

    private GenerateBOM generateBOM;

    @BeforeEach
    public void setUp() {
        // Initialize a GenerateBOM object with test values
        generateBOM = new GenerateBOM("ABC1234567", 50, 1234, 5678);
        generateBOM.setDimensions("10x20x30");
        generateBOM.setUnitsOfMeasure("cm");
    }

    @Test
    public void testConstructorAndGetters() {
        // Test initial values from constructor
        assertEquals("ABC1234567", generateBOM.getUniqueCode());
        assertEquals(50, generateBOM.getQuantity());
        assertEquals(1234, generateBOM.getSupplierCode());
        assertEquals(5678, generateBOM.getSubAssembly());
    }

    @Test
    public void testDimensionsGetterSetter() {
        // Test getDimensions and setDimensions
        assertEquals("10x20x30", generateBOM.getDimensions());
        
        // Change the dimensions and check again
        generateBOM.setDimensions("15x25x35");
        assertEquals("15x25x35", generateBOM.getDimensions());
    }

    @Test
    public void testUnitsOfMeasureGetterSetter() {
        // Test getUnitsOfMeasure and setUnitsOfMeasure
        assertEquals("cm", generateBOM.getUnitsOfMeasure());
        
        // Change the units of measure and check again
        generateBOM.setUnitsOfMeasure("mm");
        assertEquals("mm", generateBOM.getUnitsOfMeasure());
    }

    @Test
    public void testUniqueCodeGetterSetter() {
        // Test getUniqueCode and setUniqueCode
        assertEquals("ABC1234567", generateBOM.getUniqueCode());

        // Change the unique code and check again
        generateBOM.setUniqueCode("XYZ9876543");
        assertEquals("XYZ9876543", generateBOM.getUniqueCode());
    }

    @Test
    public void testQuantityGetterSetter() {
        // Test getQuantity and setQuantity
        assertEquals(50, generateBOM.getQuantity());

        // Change the quantity and check again
        generateBOM.setQuantity(75);
        assertEquals(75, generateBOM.getQuantity());
    }

    @Test
    public void testSupplierCodeGetterSetter() {
        // Test getSupplierCode and setSupplierCode
        assertEquals(1234, generateBOM.getSupplierCode());

        // Change the supplier code and check again
        generateBOM.setSupplierCode(4321);
        assertEquals(4321, generateBOM.getSupplierCode());
    }

    @Test
    public void testSubAssemblyGetterSetter() {
        // Test getSubAssembly and setSubAssembly
        assertEquals(5678, generateBOM.getSubAssembly());

        // Change the sub-assembly code and check again
        generateBOM.setSubAssembly(8765);
        assertEquals(8765, generateBOM.getSubAssembly());
    }
}
