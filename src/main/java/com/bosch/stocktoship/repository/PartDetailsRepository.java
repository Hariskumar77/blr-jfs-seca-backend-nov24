package com.bosch.stocktoship.repository;

import com.bosch.stocktoship.entity.PartDetails;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartDetailsRepository extends JpaRepository<PartDetails, Integer> {
	Optional<PartDetails> findByPartCode(int partCode);
	void deleteByPartCode(int partCode);
}

