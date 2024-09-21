package com.donation.controller.v1.api;

import com.donation.models.data.DonorFeedback;
import com.donation.service.DonorFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class DonorFeedbackController {

    @Autowired
    private DonorFeedbackService donorFeedbackService;

    // 1. Create a new feedback
    @PostMapping
    public ResponseEntity<DonorFeedback> createFeedback(@Valid @RequestBody DonorFeedback feedback) {
        DonorFeedback createdFeedback = donorFeedbackService.createFeedback(feedback);
        return ResponseEntity.status(201).body(createdFeedback); // HTTP 201 Created
    }

    // 2. Get all feedbacks
    @GetMapping
    public ResponseEntity<List<DonorFeedback>> getAllFeedbacks() {
        List<DonorFeedback> feedbacks = donorFeedbackService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks); // HTTP 200 OK
    }

    // 3. Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<DonorFeedback> getFeedbackById(@PathVariable Integer id) {
        DonorFeedback feedback = donorFeedbackService.getFeedbackById(id);
        return ResponseEntity.ok(feedback); // HTTP 200 OK
    }

    // 4. Update an existing feedback
    @PutMapping("/{id}")
    public ResponseEntity<DonorFeedback> updateFeedback(@PathVariable Integer id,
            @Valid @RequestBody DonorFeedback feedbackDetails) {
        DonorFeedback updatedFeedback = donorFeedbackService.updateFeedback(id, feedbackDetails);
        return ResponseEntity.ok(updatedFeedback); // HTTP 200 OK
    }

    // 5. Delete a feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        donorFeedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
