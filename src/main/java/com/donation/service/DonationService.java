package com.donation.service;

import com.donation.models.data.Donation;

import java.util.List;

public interface DonationService {
    Donation createDonation(Donation donation);

    List<Donation> getAllDonations();

    Donation getDonationById(Long id);

    Donation updateDonation(Long id, Donation donationDetails);

    void deleteDonation(Long id);
}