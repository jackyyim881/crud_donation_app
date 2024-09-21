package com.donation.service.impl;

import com.donation.models.data.Donation;
import com.donation.repository.DonationRepository;
import com.donation.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    @Override
    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
    }

    @Override
    public Donation updateDonation(Long id, Donation donationDetails) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));

        // Update fields
        donation.setAmount(donationDetails.getAmount());
        donation.setDonor(donationDetails.getDonor());
        donation.setStudent(donationDetails.getStudent());
        donation.setNcampaign(donationDetails.getNcampaign());
        donation.setPaymentMethod(donationDetails.getPaymentMethod());
        donation.setDonationDate(donationDetails.getDonationDate());

        return donationRepository.save(donation);
    }

    @Override
    public void deleteDonation(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        donationRepository.delete(donation);
    }
}