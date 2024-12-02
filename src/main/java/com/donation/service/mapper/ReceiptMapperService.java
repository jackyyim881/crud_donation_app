package com.donation.service.mapper;

import org.springframework.stereotype.Service;

import com.donation.dto.ReceiptRequest;
import com.donation.dto.ReceiptResponse;
import com.donation.models.data.Donation;
import com.donation.models.data.Receipt;
import com.donation.repository.DonationRepository;

@Service
public class ReceiptMapperService {
    private final DonationRepository donationRepository;

    // Constructor injection for repository to fetch related entities like Donation
    public ReceiptMapperService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    // Converts Receipt entity to ReceiptResponse DTO
    public ReceiptResponse toDTO(Receipt receipt) {
        return new ReceiptResponse(
                receipt.getId(),
                receipt.getDonation().getId(), // Fetch the donation ID from the entity
                receipt.getReceiptNumber(),
                receipt.getIssuedDate(),
                receipt.getDonation().getDonor().getUser().getUsername(), // Fetch the donor username from the entity
                receipt.getDonation().getDonationDate(), // Fetch the donation date from the entity
                receipt.getDonation().getNcampaign().getName(), // Fetch the campaign name from the entity
                receipt.getDonation().getPaymentMethod().getMethodName() // Fetch the payment method name from the
                                                                         // entity
        );
    }

    // Converts ReceiptRequest DTO to Receipt entity
    public Receipt toEntity(ReceiptRequest receiptRequest) {
        // Fetch the related Donation entity from the repository
        Donation donation = donationRepository.findById(receiptRequest.donationId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Donation not found with ID: " + receiptRequest.donationId()));

        Receipt receipt = new Receipt();
        receipt.setDonation(donation);
        receipt.setReceiptNumber(receiptRequest.receiptNumber());
        receipt.setIssuedDate(receiptRequest.issuedDate());

        return receipt;
    }
}
