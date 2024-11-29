/*** @author DLA3KOR */
package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.entity.SampleDetails;
import com.bosch.stocktoship.service.PartDetailsDAO;
import com.bosch.stocktoship.service.SampleDetailsDAO;

public class SampleUpdateTests {

    private PartDetails partDetails;
    private SampleDetails sampleDetails;
    private PartDetailsDAO partDetailsDAO;
    private SampleDetailsDAO sampleDetailsDAO;

    @BeforeEach
    public void setUp() throws ClassNotFoundException, SQLException {
        partDetails = new PartDetails(123, "Part A", "Description of Part A");
        sampleDetails = new SampleDetails(1, "Stage 1", 1.5, "Lubricated", "High Strength", "Die Type A",
                "Punch Type B", 100.0, 2.5, 0.75, 500.0, "Glossy", 0.3, "Aligned", "Check Passed", "Ductile",
                3.5, "MIG", partDetails);
        partDetailsDAO = new PartDetailsDAO();
        sampleDetailsDAO = new SampleDetailsDAO();
    }

    @Test
    public void testPartDetailsConstructorAndGetters() {
        PartDetails part = new PartDetails(456, "Part B", "Description of Part B");
        PartDetails part1 = new PartDetails("Part C", "Description of Part C");
        PartDetails part2 = new PartDetails(123);

        
        assertEquals(456, part.getPartNumber());
        assertEquals("Part B", part.getPartName());
        assertEquals("Description of Part B", part.getPartDescription());
    }

    @Test
    public void testPartDetailsSetters() {
        partDetails.setPartNumber(789);
        partDetails.setPartName("Updated Part");
        partDetails.setPartDescription("Updated Description");

        assertEquals(789, partDetails.getPartNumber());
        assertEquals("Updated Part", partDetails.getPartName());
        assertEquals("Updated Description", partDetails.getPartDescription());
    }

    @Test
    public void testSampleDetailsConstructorAndGetters() {
        assertEquals(1, sampleDetails.getSampleId());
        assertEquals("Stage 1", sampleDetails.getStageName());
        assertEquals(1.5, sampleDetails.getSheetMetalThickness());
        assertEquals("Lubricated", sampleDetails.getLubricationCondition());
        assertEquals("High Strength", sampleDetails.getSheetMetalStrength());
        assertEquals("Die Type A", sampleDetails.getToolGeometryDie());
        assertEquals("Punch Type B", sampleDetails.getToolGeometryPunch());
        assertEquals(100.0, sampleDetails.getPunchPressure());
        assertEquals(2.5, sampleDetails.getPunchSpeed());
        assertEquals(0.75, sampleDetails.getBendRadius());
        assertEquals(500.0, sampleDetails.getFormingForce());
        assertEquals("Glossy", sampleDetails.getPaintType());
        assertEquals(0.3, sampleDetails.getCoatingThickness());
        assertEquals("Aligned", sampleDetails.getAlignment());
        assertEquals("Check Passed", sampleDetails.getFunctionalChecks());
        assertEquals("Ductile", sampleDetails.getDuctility());
        assertEquals(3.5, sampleDetails.getWeldingSpeed());
        assertEquals("MIG", sampleDetails.getWeldingMethod());
        assertEquals(partDetails, sampleDetails.getPartDetails());
    }

    @Test
    public void testSampleDetailsSetters() {
        sampleDetails.setSampleId(2);
        sampleDetails.setStageName("Stage 2");
        sampleDetails.setSheetMetalThickness(2.0);
        sampleDetails.setLubricationCondition("Non-Lubricated");
        sampleDetails.setSheetMetalStrength("Medium Strength");
        sampleDetails.setToolGeometryDie("Die Type B");
        sampleDetails.setToolGeometryPunch("Punch Type C");
        sampleDetails.setPunchPressure(150.0);
        sampleDetails.setPunchSpeed(3.0);
        sampleDetails.setBendRadius(1.0);
        sampleDetails.setFormingForce(600.0);
        sampleDetails.setPaintType("Matte");
        sampleDetails.setCoatingThickness(0.5);
        sampleDetails.setAlignment("Misaligned");
        sampleDetails.setFunctionalChecks("Check Failed");
        sampleDetails.setDuctility("Non-Ductile");
        sampleDetails.setWeldingSpeed(4.0);
        sampleDetails.setWeldingMethod("TIG");
        
        PartDetails newPartDetails = new PartDetails(456, "Part C", "Updated Description of Part C");
        sampleDetails.setPartDetails(newPartDetails);

        assertEquals(2, sampleDetails.getSampleId());
        assertEquals("Stage 2", sampleDetails.getStageName());
        assertEquals(2.0, sampleDetails.getSheetMetalThickness());
        assertEquals("Non-Lubricated", sampleDetails.getLubricationCondition());
        assertEquals("Medium Strength", sampleDetails.getSheetMetalStrength());
        assertEquals("Die Type B", sampleDetails.getToolGeometryDie());
        assertEquals("Punch Type C", sampleDetails.getToolGeometryPunch());
        assertEquals(150.0, sampleDetails.getPunchPressure());
        assertEquals(3.0, sampleDetails.getPunchSpeed());
        assertEquals(1.0, sampleDetails.getBendRadius());
        assertEquals(600.0, sampleDetails.getFormingForce());
        assertEquals("Matte", sampleDetails.getPaintType());
        assertEquals(0.5, sampleDetails.getCoatingThickness());
        assertEquals("Misaligned", sampleDetails.getAlignment());
        assertEquals("Check Failed", sampleDetails.getFunctionalChecks());
        assertEquals("Non-Ductile", sampleDetails.getDuctility());
        assertEquals(4.0, sampleDetails.getWeldingSpeed());
        assertEquals("TIG", sampleDetails.getWeldingMethod());
        assertEquals(newPartDetails, sampleDetails.getPartDetails());
    }
    
    
    @Test
    public void testSaveAndRetrievePartDetails() throws SQLException, ClassNotFoundException {
        PartDetails partDetails = new PartDetails(101, "Part A", "Description A");
        partDetailsDAO.savePartDetails(partDetails);

        PartDetails retrievedPartDetails = partDetailsDAO.getPartDetails(101);
        assertNotNull(retrievedPartDetails);
        assertEquals("Part A", retrievedPartDetails.getPartName());
        assertEquals("Description A", retrievedPartDetails.getPartDescription());

        // Clean up
        partDetailsDAO.deletePartDetails(101);
    }

    @Test
    public void testDeletePartDetails() throws SQLException, ClassNotFoundException {
        PartDetails partDetails = new PartDetails(102, "Part B", "Description B");
        partDetailsDAO.savePartDetails(partDetails);

        partDetailsDAO.deletePartDetails(102);
        assertNull(partDetailsDAO.getPartDetails(102));
    }

    @Test
    public void testSaveAndRetrieveSampleDetails() throws SQLException, ClassNotFoundException {
        PartDetails partDetails = new PartDetails(103, "Part C", "Description C");
        partDetailsDAO.savePartDetails(partDetails);

        assertNotNull(partDetailsDAO.getPartDetails(103), "PartDetails was not saved successfully.");
        SampleDetails sampleDetails = new SampleDetails();
        sampleDetails.setSampleId(5);
        sampleDetails.setStageName("Stage 1");
        sampleDetails.setPartDetails(partDetails);
        sampleDetails.setSheetMetalThickness(2.5);
        sampleDetails.setLubricationCondition("Good");
        sampleDetails.setSheetMetalStrength("High");
        sampleDetails.setToolGeometryDie("Die A");
        sampleDetails.setToolGeometryPunch("Punch A");
        sampleDetails.setPunchPressure(2.5);
        sampleDetails.setPunchSpeed(1.0);
        sampleDetails.setBendRadius(0.5);
        sampleDetails.setFormingForce(3.5);
        sampleDetails.setPaintType("Type A");
        sampleDetails.setCoatingThickness(0.2);
        sampleDetails.setAlignment("Aligned");
        sampleDetails.setFunctionalChecks("Checked");
        sampleDetails.setDuctility("Good");
        sampleDetails.setWeldingSpeed(1.5);
        sampleDetails.setWeldingMethod("Method A");
        sampleDetailsDAO.saveSampleDetails(List.of(sampleDetails));

        SampleDetails retrievedSampleDetails = sampleDetailsDAO.getSampleDetails(sampleDetails.getSampleId());
        assertNotNull(retrievedSampleDetails);
        assertEquals("Stage 1", retrievedSampleDetails.getStageName());
        assertEquals(2.5, retrievedSampleDetails.getSheetMetalThickness());

        // Clean up
        sampleDetailsDAO.deleteSampleDetails(sampleDetails.getSampleId());
        partDetailsDAO.deletePartDetails(103);
    }
    
    @Test
    public void testGetSampleDetails() throws SQLException, ClassNotFoundException {
        // Set up part details and save them
    	partDetailsDAO.deletePartDetails(107);
        PartDetails partDetails = new PartDetails(107, "Part E", "Description E");
        partDetailsDAO.savePartDetails(partDetails);

        // Set up sample details
        SampleDetails sampleDetails = new SampleDetails();
        sampleDetails.setSampleId(8);
        sampleDetails.setStageName("Stage A");
        sampleDetails.setPartDetails(partDetails);
        sampleDetails.setSheetMetalThickness(1.2);
        sampleDetails.setLubricationCondition("Excellent");
        sampleDetails.setSheetMetalStrength("High");
        sampleDetails.setToolGeometryDie("Die A");
        sampleDetails.setToolGeometryPunch("Punch A");
        sampleDetails.setPunchPressure(2.5);
        sampleDetails.setPunchSpeed(1.0);
        sampleDetails.setBendRadius(0.5);
        sampleDetails.setFormingForce(3.5);
        sampleDetails.setPaintType("Type A");
        sampleDetails.setCoatingThickness(0.2);
        sampleDetails.setAlignment("Aligned");
        sampleDetails.setFunctionalChecks("Checked");
        sampleDetails.setDuctility("Good");
        sampleDetails.setWeldingSpeed(1.5);
        sampleDetails.setWeldingMethod("Method A");

        // Save the sample details to the database
        sampleDetailsDAO.saveSampleDetails(List.of(sampleDetails));

        // Retrieve the saved sample details using its sampleId
        int sampleId = sampleDetails.getSampleId(); // Assuming saveSampleDetails sets the sampleId
        SampleDetails retrievedSampleDetails = sampleDetailsDAO.getSampleDetails(sampleId);

        // Assertions to check if the retrieved data matches the saved data
        assertNotNull(retrievedSampleDetails, "SampleDetails was not retrieved successfully.");
        assertEquals(sampleDetails.getStageName(), retrievedSampleDetails.getStageName());
        assertEquals(sampleDetails.getPartDetails().getPartNumber(), retrievedSampleDetails.getPartDetails().getPartNumber());
        assertEquals(sampleDetails.getSheetMetalThickness(), retrievedSampleDetails.getSheetMetalThickness());
        assertEquals(sampleDetails.getLubricationCondition(), retrievedSampleDetails.getLubricationCondition());
        assertEquals(sampleDetails.getSheetMetalStrength(), retrievedSampleDetails.getSheetMetalStrength());
        assertEquals(sampleDetails.getToolGeometryDie(), retrievedSampleDetails.getToolGeometryDie());
        assertEquals(sampleDetails.getToolGeometryPunch(), retrievedSampleDetails.getToolGeometryPunch());
        assertEquals(sampleDetails.getPunchPressure(), retrievedSampleDetails.getPunchPressure());
        assertEquals(sampleDetails.getPunchSpeed(), retrievedSampleDetails.getPunchSpeed());
        assertEquals(sampleDetails.getBendRadius(), retrievedSampleDetails.getBendRadius());
        assertEquals(sampleDetails.getFormingForce(), retrievedSampleDetails.getFormingForce());
        assertEquals(sampleDetails.getPaintType(), retrievedSampleDetails.getPaintType());
        assertEquals(sampleDetails.getCoatingThickness(), retrievedSampleDetails.getCoatingThickness());
        assertEquals(sampleDetails.getAlignment(), retrievedSampleDetails.getAlignment());
        assertEquals(sampleDetails.getFunctionalChecks(), retrievedSampleDetails.getFunctionalChecks());
        assertEquals(sampleDetails.getDuctility(), retrievedSampleDetails.getDuctility());
        assertEquals(sampleDetails.getWeldingSpeed(), retrievedSampleDetails.getWeldingSpeed());
        assertEquals(sampleDetails.getWeldingMethod(), retrievedSampleDetails.getWeldingMethod());

        // Clean up
        sampleDetailsDAO.deleteSampleDetails(sampleId);
        partDetailsDAO.deletePartDetails(partDetails.getPartNumber());
    }

    @Test
    public void testUpdateSampleDetails() throws SQLException, ClassNotFoundException {
    	
    	partDetailsDAO.deletePartDetails(104);
        PartDetails partDetails = new PartDetails(104, "Part D", "Description D");
        partDetailsDAO.savePartDetails(partDetails);

        PartDetails savedPartDetails = partDetailsDAO.getPartDetails(104);
        assertNotNull(savedPartDetails, "PartDetails was not saved successfully.");
        SampleDetails sampleDetails = new SampleDetails();
        sampleDetails.setSampleId(9);
        sampleDetails.setStageName("Stage 2");
        sampleDetails.setPartDetails(partDetails);
        sampleDetails.setSheetMetalThickness(1.5);
        sampleDetails.setLubricationCondition("Fair");
        sampleDetails.setSheetMetalStrength("High");
        sampleDetails.setToolGeometryDie("Die A");
        sampleDetails.setToolGeometryPunch("Punch A");
        sampleDetails.setPunchPressure(2.5);
        sampleDetails.setPunchSpeed(1.0);
        sampleDetails.setBendRadius(0.5);
        sampleDetails.setFormingForce(3.5);
        sampleDetails.setPaintType("Type A");
        sampleDetails.setCoatingThickness(0.2);
        sampleDetails.setAlignment("Aligned");
        sampleDetails.setFunctionalChecks("Checked");
        sampleDetails.setDuctility("Good");
        sampleDetails.setWeldingSpeed(1.5);
        sampleDetails.setWeldingMethod("Method A");
        sampleDetailsDAO.saveSampleDetails(List.of(sampleDetails));

        sampleDetails.setStageName("Updated Stage");
        sampleDetails.setSheetMetalThickness(3.0);
        sampleDetailsDAO.updateSampleDetails(sampleDetails);

        SampleDetails updatedSampleDetails = sampleDetailsDAO.getSampleDetails(sampleDetails.getSampleId());
        assertNotNull(updatedSampleDetails);
        assertEquals("Updated Stage", updatedSampleDetails.getStageName());
        assertEquals(3.0, updatedSampleDetails.getSheetMetalThickness());

        // Clean up
        sampleDetailsDAO.deleteSampleDetails(sampleDetails.getSampleId());
        partDetailsDAO.deletePartDetails(104);
    }

    @Test
    public void testGetAllSampleDetails() throws SQLException, ClassNotFoundException {
        // Clean up any existing test data before running the test
        sampleDetailsDAO.deleteSampleDetails(12);
        sampleDetailsDAO.deleteSampleDetails(13);
        partDetailsDAO.deletePartDetails(105);

        // Create and save PartDetails
        PartDetails partDetails = new PartDetails(105, "Part E", "Description E");
        partDetailsDAO.savePartDetails(partDetails);

        // Check if partDetails was saved
        assertNotNull(partDetailsDAO.getPartDetails(105), "PartDetails was not saved successfully.");

        // Initialize and set all fields in sampleDetails1
        SampleDetails sampleDetails1 = new SampleDetails();
        sampleDetails1.setSampleId(12);
        sampleDetails1.setStageName("Stage A");
        sampleDetails1.setPartDetails(partDetails);
        sampleDetails1.setSheetMetalThickness(1.2);
        sampleDetails1.setLubricationCondition("Excellent");
        sampleDetails1.setSheetMetalStrength("High");
        sampleDetails1.setToolGeometryDie("Die A");
        sampleDetails1.setToolGeometryPunch("Punch A");
        sampleDetails1.setPunchPressure(2.5);
        sampleDetails1.setPunchSpeed(1.0);
        sampleDetails1.setBendRadius(0.5);
        sampleDetails1.setFormingForce(3.5);
        sampleDetails1.setPaintType("Type A");
        sampleDetails1.setCoatingThickness(0.2);
        sampleDetails1.setAlignment("Aligned");
        sampleDetails1.setFunctionalChecks("Checked");
        sampleDetails1.setDuctility("Good");
        sampleDetails1.setWeldingSpeed(1.5);
        sampleDetails1.setWeldingMethod("Method A");

        // Initialize and set all fields in sampleDetails2
        SampleDetails sampleDetails2 = new SampleDetails();
        sampleDetails2.setSampleId(13);
        sampleDetails2.setStageName("Stage B");
        sampleDetails2.setPartDetails(partDetails);
        sampleDetails2.setSheetMetalThickness(1.8);
        sampleDetails2.setLubricationCondition("Poor");
        sampleDetails2.setSheetMetalStrength("Medium");
        sampleDetails2.setToolGeometryDie("Die B");
        sampleDetails2.setToolGeometryPunch("Punch B");
        sampleDetails2.setPunchPressure(2.0);
        sampleDetails2.setPunchSpeed(1.2);
        sampleDetails2.setBendRadius(0.6);
        sampleDetails2.setFormingForce(3.0);
        sampleDetails2.setPaintType("Type B");
        sampleDetails2.setCoatingThickness(0.3);
        sampleDetails2.setAlignment("Misaligned");
        sampleDetails2.setFunctionalChecks("Not Checked");
        sampleDetails2.setDuctility("Poor");
        sampleDetails2.setWeldingSpeed(1.0);
        sampleDetails2.setWeldingMethod("Method B");

        // Save both sample details and retrieve the inserted row counts
        int savedCount[] = sampleDetailsDAO.saveSampleDetails(List.of(sampleDetails1, sampleDetails2));
        int total = 0;
        for(int i: savedCount) {
        	total += i;
        }
        assertEquals(2, total, "Sample details were not saved successfully.");

        // Retrieve and assert sample details
        List<SampleDetails> sampleDetailsList = sampleDetailsDAO.getAllSampleDetails();
        assertTrue(sampleDetailsList.size() >= 2, "Sample details retrieval failed.");

        // Clean up after test
        sampleDetailsDAO.deleteSampleDetails(sampleDetails1.getSampleId());
        sampleDetailsDAO.deleteSampleDetails(sampleDetails2.getSampleId());
        partDetailsDAO.deletePartDetails(105);
    }


    @Test
    public void testSaveSampleDetails() throws SQLException, ClassNotFoundException {
        // Set up part details and save them
    	partDetailsDAO.deletePartDetails(111);
        PartDetails partDetails = new PartDetails(111, "Part E", "Description E");
        partDetailsDAO.savePartDetails(partDetails);

        SampleDetails sampleDetails1 = new SampleDetails();
        sampleDetails1.setSampleId(10);
        sampleDetails1.setStageName("Stage A");
        sampleDetails1.setPartDetails(partDetails);
        sampleDetails1.setSheetMetalThickness(1.2);
        sampleDetails1.setLubricationCondition("Excellent");
        sampleDetails1.setSheetMetalStrength("High");
        sampleDetails1.setToolGeometryDie("Die A");
        sampleDetails1.setToolGeometryPunch("Punch A");
        sampleDetails1.setPunchPressure(2.5);
        sampleDetails1.setPunchSpeed(1.0);
        sampleDetails1.setBendRadius(0.5);
        sampleDetails1.setFormingForce(3.5);
        sampleDetails1.setPaintType("Type A");
        sampleDetails1.setCoatingThickness(0.2);
        sampleDetails1.setAlignment("Aligned");
        sampleDetails1.setFunctionalChecks("Checked");
        sampleDetails1.setDuctility("Good");
        sampleDetails1.setWeldingSpeed(1.5);
        sampleDetails1.setWeldingMethod("Method A");

        SampleDetails sampleDetails2 = new SampleDetails();
        sampleDetails2.setSampleId(11);
        sampleDetails2.setStageName("Stage B");
        sampleDetails2.setPartDetails(partDetails);
        sampleDetails2.setSheetMetalThickness(1.8);
        sampleDetails2.setLubricationCondition("Poor");
        sampleDetails2.setSheetMetalStrength("Medium");
        sampleDetails2.setToolGeometryDie("Die B");
        sampleDetails2.setToolGeometryPunch("Punch B");
        sampleDetails2.setPunchPressure(2.0);
        sampleDetails2.setPunchSpeed(1.2);
        sampleDetails2.setBendRadius(0.6);
        sampleDetails2.setFormingForce(3.0);
        sampleDetails2.setPaintType("Type B");
        sampleDetails2.setCoatingThickness(0.3);
        sampleDetails2.setAlignment("Misaligned");
        sampleDetails2.setFunctionalChecks("Not Checked");
        sampleDetails2.setDuctility("Poor");
        sampleDetails2.setWeldingSpeed(1.0);
        sampleDetails2.setWeldingMethod("Method B");

        // Save sample details in a batch and verify the result
        int[] result = sampleDetailsDAO.saveSampleDetails(List.of(sampleDetails1, sampleDetails2));
        for (int rowsAffected : result) {
            assertTrue(rowsAffected > 0, "Each sample detail should be inserted successfully.");
        }

        // Retrieve the inserted sample details by unique fields to get their sampleIds
        List<SampleDetails> sampleDetailsList = sampleDetailsDAO.getAllSampleDetails();

        // Clean up using the retrieved sampleIds
        sampleDetailsDAO.deleteSampleDetails(sampleDetails1.getSampleId());
        sampleDetailsDAO.deleteSampleDetails(sampleDetails2.getSampleId());
        partDetailsDAO.deletePartDetails(111);
    }



}


