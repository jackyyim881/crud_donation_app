package com.donation.dto;

import java.time.LocalDate;

public record DonationResponse(
                Long id,
                Long donorId,
                String donorName,
                Long studentId, // Nullable (optional)
                String studentName, // Nullable (optional)
                Double amount,
                LocalDate donationDate,
                Long campaignId,
                String campaignName,
                Long paymentMethodId,
                String paymentMethodName) {
}
