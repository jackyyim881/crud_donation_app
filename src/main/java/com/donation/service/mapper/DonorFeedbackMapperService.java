package com.donation.service.mapper;

import com.donation.dto.DonorFeedbackRequest;
import com.donation.dto.DonorFeedbackResponse;
import com.donation.models.data.Donor;
import com.donation.models.data.DonorFeedback;
import com.donation.repository.DonorRepository;
import org.springframework.stereotype.Service;

@Service
public class DonorFeedbackMapperService {

    private final DonorRepository donorRepository;

    public DonorFeedbackMapperService(DonorRepository donorRepository) {
        this.donorRepository = donorRepository;
    }

    // Converts DonorFeedback entity to DonorFeedbackResponse DTO
    public DonorFeedbackResponse toDTO(DonorFeedback feedback) {
        DonorFeedbackResponse response = new DonorFeedbackResponse();
        response.setId(feedback.getId());
        response.setMessage(feedback.getMessage());
        response.setSubmittedDate(feedback.getSubmittedDate());

        if (feedback.getDonor() != null) {
            response.setDonorId(feedback.getDonor().getId());
            response.setDonorName(feedback.getDonor().getUser().getUsername()); // Assuming Donor has a User entity
        }

        return response;
    }

    // Converts DonorFeedbackRequest DTO to DonorFeedback entity
    public DonorFeedback toEntity(DonorFeedbackRequest feedbackRequest) {
        DonorFeedback feedback = new DonorFeedback();
        feedback.setMessage(feedbackRequest.getMessage());

        Donor donor = donorRepository.findById(feedbackRequest.getDonorId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Donor not found with ID: " + feedbackRequest.getDonorId()));
        feedback.setDonor(donor);

        return feedback;
    }
}
