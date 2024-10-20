package com.donation.dto;

import java.time.LocalDate;

public record ReceiptResponse(
        Integer id, // Unique receipt ID
        Long donationId, // ID of the related donation
        String receiptNumber, // Receipt number
        LocalDate issuedDate, // Date the receipt was issued
        String donorUsername, // Username of the donor
        LocalDate donationDate, // Date of the donation
        String campaignName, // Name of the campaign
        String paymentMethodName // Name of the payment method
) {
}
