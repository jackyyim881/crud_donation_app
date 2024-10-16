package com.donation.dto;

import java.time.LocalDate;

public record DonationRequest(
        Long donorId,
        Long studentId, // Nullable (optional)
        Double amount,
        LocalDate donationDate,
        Long campaignId,
        Long paymentMethodId) {
}
