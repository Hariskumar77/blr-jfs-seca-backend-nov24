package com.bosch.stocktoship.controller;

import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.service.PartDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/partdetails")
@CrossOrigin(origins = "http://localhost:4200")
public class PartDetailsController {

    @Autowired
    private PartDetailsService partDetailsService;

    @GetMapping
    public List<PartDetails> getAllParts() {
        return partDetailsService.getAllParts();
    }

    @GetMapping("/{partId}")
    public ResponseEntity<PartDetails> getPartById(@PathVariable int partId) {
        Optional<PartDetails> partDetails = partDetailsService.getPartById(partId);
        return partDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/partcode/{partCode}")
    public ResponseEntity<Optional<PartDetails>> getPartByPartCode(@PathVariable int partCode) {
        Optional<PartDetails> partDetails = partDetailsService.getPartByPartCode(partCode);
        return partDetails != null ? ResponseEntity.ok(partDetails) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PartDetails> createPart(@RequestBody PartDetails partDetails) {
        PartDetails savedPart = partDetailsService.savePart(partDetails);
        return ResponseEntity.ok(savedPart);
    }

    @DeleteMapping("/{partId}")
    public ResponseEntity<Void> deletePart(@PathVariable int partId) {
        partDetailsService.deletePart(partId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/update/{partCode}")
    public ResponseEntity<PartDetails> updatePart(@PathVariable int partCode, @RequestBody PartDetails partDetails) {
        PartDetails updatedPart = partDetailsService.updatePart(partCode, partDetails);
        return ResponseEntity.ok(updatedPart);
    }
    
    @DeleteMapping("/deleteByPartCode/{partCode}")
    public ResponseEntity<Void> deletePartbyCode(@PathVariable int partCode) {
        partDetailsService.deletePartbyCode(partCode);
        return ResponseEntity.noContent().build();
    }
    
}
