package com.bosch.stocktoship.controller;

import com.bosch.stocktoship.entity.StageDetails;
import com.bosch.stocktoship.service.StageDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stagedetails")
@CrossOrigin(origins = "http://localhost:4200")
public class StageDetailsController {

    @Autowired
    private StageDetailsService stageDetailsService;

    @GetMapping
    public List<StageDetails> getAllStages() {
        return stageDetailsService.getAllStages();
    }

    @GetMapping("/{stageId}")
    public ResponseEntity<StageDetails> getStageById(@PathVariable int stageId) {
        Optional<StageDetails> stageDetails = stageDetailsService.getStageById(stageId);
        return stageDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StageDetails> createStage(@RequestBody StageDetails stageDetails) {
        StageDetails savedStage = stageDetailsService.saveStage(stageDetails);
        return ResponseEntity.ok(savedStage);
    }

    @DeleteMapping("/{stageId}")
    public ResponseEntity<Void> deleteStage(@PathVariable int stageId) {
        stageDetailsService.deleteStage(stageId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/update")
    public ResponseEntity<StageDetails> updateStage(@RequestBody StageDetails stageDetails){
    	StageDetails updateStage = stageDetailsService.saveStage(stageDetails);
    	return ResponseEntity.ok(updateStage);
    }
}
