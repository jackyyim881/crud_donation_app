package com.donation.controller.v1.api;

import com.donation.dto.DonorFeedbackRequest;
import com.donation.dto.DonorFeedbackResponse;
import com.donation.service.DonorFeedbackService;
import com.donation.service.mapper.DonorFeedbackMapperService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/feedbacks")
public class DonorFeedbackController {

    private final DonorFeedbackService donorFeedbackService;
    private final DonorFeedbackMapperService donorFeedbackMapperService;

    // Constructor injection
    public DonorFeedbackController(DonorFeedbackService donorFeedbackService,
            DonorFeedbackMapperService donorFeedbackMapperService) {
        this.donorFeedbackService = donorFeedbackService;
        this.donorFeedbackMapperService = donorFeedbackMapperService;
    }

    // 1. Create a new feedback
    @PostMapping
    public ResponseEntity<DonorFeedbackResponse> createFeedback(
            @Valid @RequestBody DonorFeedbackRequest feedbackRequest) {
        var feedback = donorFeedbackMapperService.toEntity(feedbackRequest);
        var createdFeedback = donorFeedbackService.createFeedback(feedback);
        var response = donorFeedbackMapperService.toDTO(createdFeedback);
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // HTTP 201 Created
    }

    // 2. Get all feedbacks
    @GetMapping
    public ResponseEntity<List<DonorFeedbackResponse>> getAllFeedbacks() {
        List<DonorFeedbackResponse> feedbacks = donorFeedbackService.getAllFeedbacks().stream()
                .map(donorFeedbackMapperService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedbacks); // HTTP 200 OK
    }

    // 3. Get feedback by ID
    @GetMapping("/{id}")
    public ResponseEntity<DonorFeedbackResponse> getFeedbackById(@PathVariable Integer id) {
        var feedback = donorFeedbackService.getFeedbackById(id);
        var response = donorFeedbackMapperService.toDTO(feedback);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    // 4. Update an existing feedback
    @PutMapping("/{id}")
    public ResponseEntity<DonorFeedbackResponse> updateFeedback(@PathVariable Integer id,
            @Valid @RequestBody DonorFeedbackRequest feedbackRequest) {
        var feedbackDetails = donorFeedbackMapperService.toEntity(feedbackRequest);
        var updatedFeedback = donorFeedbackService.updateFeedback(id, feedbackDetails);
        var response = donorFeedbackMapperService.toDTO(updatedFeedback);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    // 5. Delete a feedback
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Integer id) {
        donorFeedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }

    // 6. Get feedbacks by donor type
    @GetMapping("/donor-type/{donorType}")
    public ResponseEntity<List<DonorFeedbackResponse>> getFeedbacksByDonorType(@PathVariable String donorType) {
        List<DonorFeedbackResponse> feedbacks = donorFeedbackService.getFeedbacksByDonorType(donorType).stream()
                .map(donorFeedbackMapperService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(feedbacks); // HTTP 200 OK
    }
}
