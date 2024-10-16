package com.donation.service;

import com.donation.dto.DonationRequest;
import com.donation.models.data.Donation;

import java.time.LocalDate;
import java.util.List;

public interface DonationService {

    Donation createDonation(DonationRequest donationRequest);

    List<Donation> getAllDonations();

    List<Donation> getDonations(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate,
            Long campaignId);

    Donation getDonationById(Long id);

    // Update an existing donation
    Donation updateDonation(Long id, DonationRequest donationDetails);

    // Delete a donation by its ID
    void deleteDonation(Long id);

    // Simulate a donation
    void donate(Long studentId, Long paymentMethodId, Double amount, Long donorId);

}
