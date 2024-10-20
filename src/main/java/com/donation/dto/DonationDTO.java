package com.donation.dto;

import java.time.LocalDate;

public record DonationDTO(
        Long id, // Donation ID
        String donorName, // Donor's name
        String idNumber, // Donor's ID number (if applicable)
        LocalDate donationDate, // Date of donation
        String campaignName, // Campaign name
        String paymentMethodName // Payment method used
) {
}
