package com.bosch.stocktoship.service;

import com.bosch.stocktoship.entity.Feedback;

/**
 * @author BAP3COB
 * Service class for handling feedback-related operations.
 * This class includes methods for submitting feedback and generating a Bill of Materials (BOM).
 */
public class FeedbackService {
    
    public String submitFeedback(Feedback feedback) {
    	String result ="\n--- Feedback Submitted ---\n" + feedback;
		return result;
    }

    public String generateBOM() {
    	String result = "BOM generated!!!";
    	return result;
    }
}