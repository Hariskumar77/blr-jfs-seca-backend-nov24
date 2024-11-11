package com.bosch.stocktoship;
/**
* @author MJT3KOR*/
 
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*Quality Control Stages: Verify the stages of the manufacturing process and ensure that the current stage can be set properly.
Data Collection: Simulate collecting data for a part and stage, and ensure that it�s correctly stored.
Data Editing: Ensure that existing data can be edited based on valid inputs, and handle invalid inputs.
Data Saving and Submission: Ensure that data can be saved and submitted without errors.
ParameterData Class: Validate the functionality of getters and setters for the ParameterData class attributes.*/
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.bosch.stocktoship.entity.ParameterData;
import com.bosch.stocktoship.service.QualityControl;

public class QualityControlTest {
    private QualityControl qualityControl;
    private ParameterData parameterData;

    @BeforeEach
    public void setUp() {
        qualityControl = new QualityControl();
        parameterData = new ParameterData("Part001");
    }

    @Test
    public void testGetStages() {
        List<String> expectedStages = new ArrayList<>();
        expectedStages.add("Stamping");
        expectedStages.add("Blanking");
        expectedStages.add("Welding");
        expectedStages.add("Painting");
        expectedStages.add("Assembling");

        assertEquals(expectedStages, qualityControl.getStages());
    }

    @Test
    public void testSetCurrentStage() {
        String stage = "Stamping";
        qualityControl.setCurrentStage(stage);
        assertEquals(stage, qualityControl.getStages().get(0));
    }

    @Test
    public void testSetCurrentStageForAllStages() {
        for (String stage : qualityControl.getStages()) {
            qualityControl.setCurrentStage(stage);
            assertEquals(stage, qualityControl.getStages().get(qualityControl.getStages().indexOf(stage)));
        }
    }

    @Test
    public void testCollectData() {
        String simulatedInput = "0.5\nGood\n200\nDie1\nPunch1\n100\n10\n";
        Scanner scanner = new Scanner(simulatedInput);
        
        qualityControl.collectData(1, "Part001", scanner);
        ParameterData data = qualityControl.stageData.get("Stamping").get(0);

        assertEquals("Part001", data.getPartCode());
        assertEquals(0.5, data.getSheetMetalThickness(), 0.01);
        assertEquals("Good", data.getLubricationCondition());
    }

    @Test
    public void testEditData() {
        testCollectData(); // Ensure there's data to edit

        String simulatedInput = "Part001\n1\n1\n1.0\n"; // Choose to edit thickness
        Scanner scanner = new Scanner(simulatedInput);
        qualityControl.editData(scanner);

        ParameterData data = qualityControl.stageData.get("Stamping").get(0);
        assertEquals(1.0, data.getSheetMetalThickness(), 0.01);
    }

    @Test
    public void testSaveData() {
        qualityControl.setCurrentStage("Stamping");
        qualityControl.saveData();
    }

    @Test
    public void testSubmitData() {
        testCollectData();
        int stagechoice = 3;
        String partcode = "Part001";
        qualityControl.submitData(stagechoice, partcode);
    }

    @Test
    public void testParameterDataGettersAndSetters() {
        parameterData.setSheetMetalThickness(0.5);
        assertEquals(0.5, parameterData.getSheetMetalThickness(), 0.01);

        parameterData.setLubricationCondition("Good");
        assertEquals("Good", parameterData.getLubricationCondition());

        parameterData.setSheetMetalStrength(300);
        assertEquals(300, parameterData.getSheetMetalStrength(), 0.01);

        parameterData.setToolGeometryDie("Die2");
        assertEquals("Die2", parameterData.getToolGeometryDie());

        parameterData.setToolGeometryPunch("Punch2");
        assertEquals("Punch2", parameterData.getToolGeometryPunch());

        parameterData.setPunchPressure(150);
        assertEquals(150, parameterData.getPunchPressure(), 0.01);

        parameterData.setPunchSpeed(20);
        assertEquals(20, parameterData.getPunchSpeed(), 0.01);
    }

    @Test
    public void testEditDataWithInvalidSampleNumber() {
        testCollectData(); 
        String simulatedInput = "Part001\n1\n100\n"; 
        Scanner scanner = new Scanner(simulatedInput);
        qualityControl.editData(scanner);
    }

    @Test
    public void testEditDataWithInvalidPartNumber() {
        testCollectData(); 
        String simulatedInput = "InvalidPart\n"; 
        Scanner scanner = new Scanner(simulatedInput);
        qualityControl.editData(scanner);
    }
}
