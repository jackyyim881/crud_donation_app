package com.donation.service;

import com.donation.dto.DonationRequest;
import com.donation.models.data.Donation;

import java.time.LocalDate;
import java.util.List;

public interface DonationService {

    Donation createDonation(DonationRequest donationRequest);

    List<Donation> getAllDonations();

    Donation getDonationById(Long id);

    List<Donation> getDonations(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate,
            Long campaignId, Long donorId);

    Donation updateDonation(Long id, Donation donationDetails);

    void deleteDonation(Long id);
}