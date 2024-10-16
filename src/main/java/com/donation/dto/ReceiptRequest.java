package com.donation.dto;

import java.time.LocalDate;

public record ReceiptRequest(
        Long donationId, // ID of the donation
        String receiptNumber,
        LocalDate issuedDate) {
}
