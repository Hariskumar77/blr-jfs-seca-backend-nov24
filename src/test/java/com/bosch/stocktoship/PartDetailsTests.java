package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.entity.StageDetails;
import com.bosch.stocktoship.repository.PartDetailsRepository;
import com.bosch.stocktoship.repository.StageDetailsRepository;
import com.bosch.stocktoship.service.PartDetailsService;

class PartDetailsServiceTest {

    @Mock
    private PartDetailsRepository partDetailsRepository;

    @Mock
    private StageDetailsRepository stageDetailsRepository;

    @InjectMocks
    private PartDetailsService partDetailsService;

    private PartDetails partDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        partDetails = new PartDetails();
        partDetails.setPartId(1);
        partDetails.setPartCode(1001);
        partDetails.setPartDescription("Test Part");

        StageDetails stageDetails = new StageDetails();
        stageDetails.setStageId(1);
        stageDetails.setStageName("Stamping");
        stageDetails.setPartDetails(partDetails);

        partDetails.setStageDetails(Arrays.asList(stageDetails));
    }

    @Test
    void testGetAllParts() {
        when(partDetailsRepository.findAll()).thenReturn(Arrays.asList(partDetails));

        List<PartDetails> parts = partDetailsService.getAllParts();

        assertNotNull(parts);
        assertEquals(1, parts.size());
        verify(partDetailsRepository, times(1)).findAll();
    }

    @Test
    void testGetPartById() {
        when(partDetailsRepository.findById(1)).thenReturn(Optional.of(partDetails));

        Optional<PartDetails> result = partDetailsService.getPartById(1);

        assertTrue(result.isPresent());
        assertEquals("Test Part", result.get().getPartDescription());
        verify(partDetailsRepository, times(1)).findById(1);
    }

    @Test
    void testSavePart() {
        when(partDetailsRepository.save(any(PartDetails.class))).thenReturn(partDetails);

        PartDetails savedPart = partDetailsService.savePart(partDetails);

        assertNotNull(savedPart);
        assertEquals("Test Part", savedPart.getPartDescription());
        verify(partDetailsRepository, times(1)).save(partDetails);
    }

    @Test
    void testDeletePartByCode() {
        doNothing().when(partDetailsRepository).deleteByPartCode(1001);

        partDetailsService.deletePartbyCode(1001);

        verify(partDetailsRepository, times(1)).deleteByPartCode(1001);
    }
}
