package com.donation.service.impl;

import com.donation.dto.DonationRequest;
import com.donation.exception.ResourceNotFoundException;
import com.donation.models.data.*;
import com.donation.repository.*;
import com.donation.service.DonorService;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {

    private final DonorRepository donorRepository;
    private final UserRepository userRepository;
    private final CampaignRepository campaignRepository;
    private final StudentRepository studentRepository;
    private final DonationRepository donationRepository;

    // Constructor injection for dependencies
    public DonorServiceImpl(DonorRepository donorRepository,
            UserRepository userRepository,
            CampaignRepository campaignRepository,
            StudentRepository studentRepository,
            DonationRepository donationRepository) {
        this.donorRepository = donorRepository;
        this.userRepository = userRepository;
        this.campaignRepository = campaignRepository;
        this.studentRepository = studentRepository;
        this.donationRepository = donationRepository;
    }

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @Override
    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + id));
    }

    @Override
    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    @Override
    public Donor updateDonor(Long id, Donor donorDetails) {
        Donor donor = getDonorById(id); // Fetch the existing donor

        User user = donor.getUser(); // Retrieve the associated User object

        // Update the User fields using donorDetails
        user.setEmail(donorDetails.getUser().getEmail());
        user.setPhoneNumber(donorDetails.getUser().getPhoneNumber());
        user.setAddress(donorDetails.getUser().getAddress());

        // Update the donor's associated user if needed
        donor.setUser(donorDetails.getUser());

        // Save the updated User and Donor
        userRepository.save(user);
        return donorRepository.save(donor);
    }

    @Override
    public Donation createDonation(DonationRequest donationRequest) {
        Donor donor = findDonorById(donationRequest.donorId());
        Campaign campaign = findCampaignById(donationRequest.campaignId());

        Student student = donationRequest.studentId() != null ? findStudentById(donationRequest.studentId()) : null;

        Donation donation = new Donation();
        donation.setDonor(donor);
        donation.setNcampaign(campaign);
        donation.setStudent(student);
        donation.setAmount(donationRequest.amount());
        donation.setDonationDate(LocalDate.now());

        return donationRepository.save(donation);
    }

    @Override
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    @Override
    public Optional<Donor> findByUserUsername(String username) {
        return donorRepository.findByUserUsername(username);
    }

    @Override
    public Donor save(Donor donor) {
        return donorRepository.save(donor);
    }

    // Helper methods for fetching related entities

    private Donor findDonorById(Long donorId) {
        return donorRepository.findById(donorId)
                .orElseThrow(() -> new ResourceNotFoundException("Donor not found with id: " + donorId));
    }

    private Campaign findCampaignById(Long campaignId) {
        return campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ResourceNotFoundException("Campaign not found with id: " + campaignId));
    }

    private Student findStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
    }
}
