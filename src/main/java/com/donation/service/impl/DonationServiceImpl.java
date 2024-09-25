package com.donation.service.impl;

import com.donation.dto.DonationRequest;
import com.donation.exception.ResourceNotFoundException;
import com.donation.models.data.Campaign;
import com.donation.models.data.Donation;
import com.donation.models.data.Donor;
import com.donation.models.data.PaymentMethod;
import com.donation.models.data.Student;
import com.donation.repository.CampaignRepository;
import com.donation.repository.DonationRepository;
import com.donation.repository.DonorRepository;
import com.donation.repository.PaymentMethodRepository;
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
    private PaymentMethodRepository paymentMethodRepository;

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Creates a new donation based on the DonationRequest DTO.
     */
    @Override
    public Donation createDonation(DonationRequest donationRequest) {
        // Fetch the donor by donorId
        Donor donor = donorRepository.findById(donationRequest.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Donor not found with id: " + donationRequest.getDonorId()));

        // Fetch the campaign by campaignId
        Campaign campaign = campaignRepository.findById(donationRequest.getCampaignId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Campaign not found with id: " + donationRequest.getCampaignId()));

        // Fetch the payment method by paymentMethodId
        PaymentMethod paymentMethod = paymentMethodRepository.findById(donationRequest.getPaymentMethodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment Method not found with id: " + donationRequest.getPaymentMethodId()));

        // Check if student exists (optional)
        Student student = null;
        if (donationRequest.getStudentId() != null) {
            student = studentRepository.findById(donationRequest.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Student not found with id: " + donationRequest.getStudentId()));
        }

        // Create a new donation
        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setNcampaign(campaign);
        donation.setPaymentMethod(paymentMethod);
        donation.setStudent(student); // Set student if present
        donation.setAmount(donationRequest.getAmount());
        donation.setDonationDate(donationRequest.getDonationDate()); // Use the date from the request

        // Save the donation
        return donationRepository.save(donation);
    }

    /**
     * Retrieves donations based on filter criteria.
     */
    @Override
    public List<Donation> getDonations(Double minAmount, Double maxAmount, LocalDate startDate, LocalDate endDate,
            Long campaignId) {
        // Implement filtering logic
        // For simplicity, using a custom query method in DonationRepository
        return donationRepository.findDonations(minAmount, maxAmount, startDate, endDate, campaignId, campaignId);
    }

    /**
     * Retrieves all donations.
     */
    @Override
    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    /**
     * Retrieves a donation by its ID.
     */
    @Override
    public Donation getDonationById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));
    }

    /**
     * Updates an existing donation based on the DonationRequest DTO.
     */
    @Override
    public Donation updateDonation(Long id, DonationRequest donationDetails) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));

        // Update amount
        donation.setAmount(donationDetails.getAmount());

        // Update donor
        Donor donor = donorRepository.findById(donationDetails.getDonorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Donor not found with id: " + donationDetails.getDonorId()));
        donation.setDonor(donor);

        // Update campaign
        Campaign campaign = campaignRepository.findById(donationDetails.getCampaignId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Campaign not found with id: " + donationDetails.getCampaignId()));
        donation.setNcampaign(campaign);

        // Update payment method
        PaymentMethod paymentMethod = paymentMethodRepository.findById(donationDetails.getPaymentMethodId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment Method not found with id: " + donationDetails.getPaymentMethodId()));
        donation.setPaymentMethod(paymentMethod);

        // Update student (optional)
        if (donationDetails.getStudentId() != null) {
            Student student = studentRepository.findById(donationDetails.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Student not found with id: " + donationDetails.getStudentId()));
            donation.setStudent(student);
        } else {
            donation.setStudent(null); // Remove association if student is not provided
        }

        // Update donation date
        donation.setDonationDate(donationDetails.getDonationDate());

        // Save the updated donation
        return donationRepository.save(donation);
    }

    /**
     * Deletes a donation by its ID.
     */
    @Override
    public void deleteDonation(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));
        donationRepository.delete(donation);
    }
}
