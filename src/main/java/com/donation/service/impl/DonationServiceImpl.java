package com.donation.service.impl;

import com.donation.dto.DonationRequest;
import com.donation.models.data.Campaign;
import com.donation.models.data.Donation;
import com.donation.models.data.Donor;
import com.donation.models.data.Student;
import com.donation.repository.CampaignRepository;
import com.donation.repository.DonationRepository;
import com.donation.repository.DonorRepository;
import com.donation.repository.StudentRepository;
import com.donation.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Donation createDonation(DonationRequest donationRequest) {
        // Fetch the donor by donorId
        Donor donor = donorRepository.findById(donationRequest.getDonorId())
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + donationRequest.getDonorId()));

        // Fetch the campaign by campaignId
        Campaign campaign = campaignRepository.findById(donationRequest.getCampaignId())
                .orElseThrow(
                        () -> new RuntimeException("Campaign not found with id: " + donationRequest.getCampaignId()));

        // Check if student exists (optional)
        Student student = null;
        if (donationRequest.getStudentId() != null) {
            student = studentRepository.findById(donationRequest.getStudentId())
                    .orElseThrow(
                            () -> new RuntimeException("Student not found with id: " + donationRequest.getStudentId()));
        }

        // Create a new donation
        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setNcampaign(campaign);
        donation.setStudent(student); // Set student if present
        donation.setAmount(donationRequest.getAmount());
        donation.setDonationDate(LocalDate.now()); // Set current date

        // Save the donation
        return donationRepository.save(donation);
    }

    @Override
    public List<Donation> getDonations(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate,
            Long campaignId, Long donorId) {
        return donationRepository.findDonations(minAmount, maxAmount, startDate, endDate, campaignId, donorId);
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