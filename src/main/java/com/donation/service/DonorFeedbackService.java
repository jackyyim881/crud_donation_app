package com.donation.service;

import com.donation.models.data.DonorFeedback;

import java.util.List;

public interface DonorFeedbackService {
    DonorFeedback createFeedback(DonorFeedback feedback);

    List<DonorFeedback> getAllFeedbacks();

    DonorFeedback getFeedbackById(Integer id);

    DonorFeedback updateFeedback(Integer id, DonorFeedback feedbackDetails);

    void deleteFeedback(Integer id);

    List<DonorFeedback> getFeedbacksByDonorType(String donorType);

}