package com.donation.dto;

public class DonorFeedbackRequest {
    private Long donorId;
    private String message;

    // Getters and setters
    public Long getDonorId() {
        return donorId;
    }

    public void setDonorId(Long donorId) {
        this.donorId = donorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
