package com.donation.service;

import com.donation.dto.DonationRequest;
import com.donation.dto.DonationResponse;
import com.donation.models.data.Donation;
import com.donation.models.data.Receipt;

import java.time.LocalDate;
import java.util.List;

public interface DonationService {
    Donation createDonation(DonationRequest donationRequest);

    List<Donation> getDonations(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate,
            Long campaignId);

    List<Donation> getAllDonations();

    Donation getDonationById(Long id);

    Donation updateDonation(Long id, DonationRequest donationRequest);

    void deleteDonation(Long id);

    void donate(Long studentId, Long paymentMethodId, Double amount, Long donorId);

    void saveDonation(Donation donation);
}