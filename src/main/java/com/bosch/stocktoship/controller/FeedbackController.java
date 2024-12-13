package com.bosch.stocktoship.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bosch.stocktoship.entity.Feedback;
import com.bosch.stocktoship.service.FeedbackService;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*")
public class FeedbackController {

	@Autowired
	private FeedbackService feedbackService;

	@PostMapping
	public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback) {
		Feedback savedFeedback = feedbackService.saveFeedback(feedback);
		return ResponseEntity.ok(savedFeedback);
	}

	@GetMapping("/feedbacks")
	public List<Feedback> getAllParts() {
		return feedbackService.getAllFeedbacks();
	}
}
