package com.bosch.stocktoship.repository;

import com.bosch.stocktoship.entity.StageDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageDetailsRepository extends JpaRepository<StageDetails, Integer> {
}
