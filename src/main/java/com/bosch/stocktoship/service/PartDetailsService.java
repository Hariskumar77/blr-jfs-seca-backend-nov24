package com.bosch.stocktoship.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.entity.StageDetails;
import com.bosch.stocktoship.repository.PartDetailsRepository;
import com.bosch.stocktoship.repository.StageDetailsRepository;

import jakarta.transaction.Transactional;

@Service
public class PartDetailsService {

	@Autowired
	private PartDetailsRepository partDetailsRepository;

	@Autowired
	private StageDetailsRepository stageDetailsRepository;

	public List<PartDetails> getAllParts() {
		return partDetailsRepository.findAll();
	}

	public Optional<PartDetails> getPartById(int partId) {
		return partDetailsRepository.findById(partId);
	}

	public Optional<PartDetails> getPartByPartCode(int partCode) {
		return partDetailsRepository.findByPartCode(partCode);
	}

	public PartDetails savePart(PartDetails partDetails) {
		for (StageDetails stageDetail : partDetails.getStageDetails()) {
			stageDetail.setPartDetails(partDetails);
		}
		return partDetailsRepository.save(partDetails);
	}

	public PartDetails updatePart(int id, PartDetails partDetails) {
		Optional<PartDetails> existingPart = partDetailsRepository.findByPartCode(id);

		if (existingPart.isPresent()) {
			PartDetails existing = existingPart.get();

			existing.setPartCode(partDetails.getPartCode());
			existing.setPartDescription(partDetails.getPartDescription());

			List<StageDetails> updatedStageDetails = partDetails.getStageDetails();
			for (StageDetails stageDetail : updatedStageDetails) {
				Optional<StageDetails> existingStageDetail = stageDetailsRepository.findById(stageDetail.getStageId());
				if (existingStageDetail.isPresent()) {
					StageDetails existingDetail = existingStageDetail.get();
					existingDetail.setStageName(stageDetail.getStageName());
					existingDetail.setSheetMetalThickness(stageDetail.getSheetMetalThickness());
					existingDetail.setLubricationCondition(stageDetail.getLubricationCondition());
					existingDetail.setSheetMetalStrength(stageDetail.getSheetMetalStrength());
					existingDetail.setToolGeometryDie(stageDetail.getToolGeometryDie());
					existingDetail.setToolGeometryPunch(stageDetail.getToolGeometryPunch());
					existingDetail.setPunchPressure(stageDetail.getPunchPressure());
					existingDetail.setPunchSpeed(stageDetail.getPunchSpeed());
					existingDetail.setBendRadius(stageDetail.getBendRadius());
					existingDetail.setFormingForce(stageDetail.getFormingForce());
					existingDetail.setPaintType(stageDetail.getPaintType());
					existingDetail.setCoatingThickness(stageDetail.getCoatingThickness());
					existingDetail.setAlignment(stageDetail.getAlignment());
					existingDetail.setFunctionalCheck(stageDetail.getFunctionalCheck());
					existingDetail.setDuctilityOfWeldedJoints(stageDetail.getDuctilityOfWeldedJoints());
					existingDetail.setWeldingSpeed(stageDetail.getWeldingSpeed());
					existingDetail.setWeldingMethod(stageDetail.getWeldingMethod());

					existingDetail.setPartDetails(existing);
					stageDetailsRepository.saveAndFlush(existingDetail);
				}
			}

			return partDetailsRepository.saveAndFlush(existing);
		}

		return null;
	}

	public void deletePart(int partId) {
		partDetailsRepository.deleteById(partId);
	}

	@Transactional
	public void deletePartbyCode(int partCode) {
		System.out.println("Deleting part with partCode: " + partCode);
		partDetailsRepository.deleteByPartCode(partCode);
		
	}
}
