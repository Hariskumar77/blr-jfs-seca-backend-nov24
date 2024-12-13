package com.bosch.stocktoship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bosch.stocktoship.entity.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
}

