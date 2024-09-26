package com.donation.service;

import com.donation.dto.DonationRequest;
import com.donation.models.data.Donation;
import com.donation.models.data.Donor;

import java.util.List;

public interface DonorService {
    List<Donor> getAllDonors();

    Donor getDonorById(Long id);

    Donor createDonor(Donor donor);

    Donor updateDonor(Long id, Donor donorDetails);

    void deleteDonor(Long id);

    Donation createDonation(DonationRequest donationRequest);

}