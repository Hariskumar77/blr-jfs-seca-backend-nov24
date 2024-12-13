package com.bosch.stocktoship.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.entity.StageDetails;
import com.bosch.stocktoship.repository.PartDetailsRepository;
import com.bosch.stocktoship.repository.StageDetailsRepository;

@Service
public class StageDetailsService {

    @Autowired
    private StageDetailsRepository stageDetailsRepository;
    @Autowired
    private PartDetailsRepository partDetailsRepository;

    public List<StageDetails> getAllStages() {
        return stageDetailsRepository.findAll();
    }

    public Optional<StageDetails> getStageById(int stageId) {
        return stageDetailsRepository.findById(stageId);
    }

    public StageDetails saveStage(StageDetails stageDetails) {
        PartDetails partDetails = partDetailsRepository.findById(stageDetails.getPartDetails().getPartId())
                                                       .orElseThrow(() -> new RuntimeException("Part not found"));

        stageDetails.setPartDetails(partDetails);

        return stageDetailsRepository.save(stageDetails);
    }


    public void deleteStage(int stageId) {
        stageDetailsRepository.deleteById(stageId);
    }
}
