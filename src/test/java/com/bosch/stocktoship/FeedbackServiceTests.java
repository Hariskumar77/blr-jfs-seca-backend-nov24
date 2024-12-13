package com.bosch.stocktoship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bosch.stocktoship.entity.Feedback;
import com.bosch.stocktoship.repository.FeedbackRepository;
import com.bosch.stocktoship.service.FeedbackService;

class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveFeedback() {
        Feedback feedback = new Feedback();
        feedback.setId(1);
        feedback.setQAFailed(true);
        feedback.setMetRequirements(false);
        feedback.setDefectType("Visual");
        feedback.setRemarks("Scratch found");
        feedback.setPartCode(12345);

        when(feedbackRepository.save(feedback)).thenReturn(feedback);

        Feedback savedFeedback = feedbackService.saveFeedback(feedback);

        assertEquals(feedback, savedFeedback);
        verify(feedbackRepository).save(feedback);
    }

    @Test
    void testGetAllFeedbacks() {
        Feedback feedback1 = new Feedback();
        feedback1.setId(1);
        feedback1.setQAFailed(false);
        feedback1.setMetRequirements(true);
        feedback1.setDefectType("None");
        feedback1.setRemarks("All good");
        feedback1.setPartCode(12345);

        Feedback feedback2 = new Feedback();
        feedback2.setId(2);
        feedback2.setQAFailed(true);
        feedback2.setMetRequirements(false);
        feedback2.setDefectType("Functional");
        feedback2.setRemarks("Does not fit");
        feedback2.setPartCode(67890);

        List<Feedback> feedbackList = Arrays.asList(feedback1, feedback2);

        when(feedbackRepository.findAll()).thenReturn(feedbackList);

        List<Feedback> allFeedbacks = feedbackService.getAllFeedbacks();

        assertEquals(2, allFeedbacks.size());
        assertEquals(feedbackList, allFeedbacks);
        verify(feedbackRepository).findAll();
    }
}
