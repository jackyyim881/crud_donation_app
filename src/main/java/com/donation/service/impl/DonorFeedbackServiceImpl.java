package com.donation.service.impl;

import com.donation.models.data.DonorFeedback;
import com.donation.repository.DonorFeedbackRepository;
import com.donation.repository.DonorRepository;
import com.donation.service.DonorFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorFeedbackServiceImpl implements DonorFeedbackService {

    @Autowired
    private DonorFeedbackRepository donorFeedbackRepository;
    @Autowired
    private DonorRepository donorRepository;

    @Override
    public DonorFeedback createFeedback(DonorFeedback feedback) {
        return donorFeedbackRepository.save(feedback);
    }

    @Override
    public List<DonorFeedback> getAllFeedbacks() {
        return donorFeedbackRepository.findAll();
    }

    @Override
    public DonorFeedback getFeedbackById(Integer id) {
        return donorFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
    }

    @Override
    public DonorFeedback updateFeedback(Integer id, DonorFeedback feedbackDetails) {
        DonorFeedback feedback = donorFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));

        feedback.setMessage(feedbackDetails.getMessage());
        feedback.setSubmittedDate(feedbackDetails.getSubmittedDate());
        feedback.setDonor(feedbackDetails.getDonor());

        return donorFeedbackRepository.save(feedback);
    }

    @Override
    public void deleteFeedback(Integer id) {
        DonorFeedback feedback = donorFeedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback not found with id: " + id));
        donorFeedbackRepository.delete(feedback);
    }

    // 根据 donorType 获取反馈
    public List<DonorFeedback> getFeedbacksByDonorType(String donorType) {
        return donorFeedbackRepository.findByDonor_DonorType(donorType);
    }

    @Override
    public List<String> getAllDonorTypes() {
        return donorRepository.findDistinctDonorType();
    }

}