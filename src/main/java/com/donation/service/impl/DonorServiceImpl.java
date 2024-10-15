package com.donation.service.impl;

import com.donation.dto.DonationRequest;
import com.donation.models.data.Campaign;
import com.donation.models.data.Donation;
import com.donation.models.data.Donor;
import com.donation.models.data.Student;
import com.donation.models.data.User;
import com.donation.repository.CampaignRepository;
import com.donation.repository.DonationRepository;
import com.donation.repository.DonorRepository;
import com.donation.repository.StudentRepository;
import com.donation.repository.UserRepository;
import com.donation.service.DonorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class DonorServiceImpl implements DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    @Override
    public Donor getDonorById(Long id) {
        return donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));
    }

    @Override
    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    @Override
    public Donor updateDonor(Long id, Donor donorDetails) {
        // Fetch the existing donor by ID
        Donor donor = getDonorById(id);

        // Retrieve the associated User object
        User user = donor.getUser();

        // Update the User fields using donorDetails
        user.setEmail(donorDetails.getUser().getEmail()); // Updating email via User
        user.setPhoneNumber(donorDetails.getUser().getPhoneNumber()); // Updating phone number via User
        user.setAddress(donorDetails.getUser().getAddress()); // Updating address via User

        // Optionally update the donor's user association if needed (e.g., if changing
        // users)
        donor.setUser(donorDetails.getUser());

        // Save the updated User and Donor
        userRepository.save(user); // Save changes to the User
        return donorRepository.save(donor); // Save changes to the Donor
    }

    @Override
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
        donation.setDonationDate(LocalDate.now()); // Automatically set to current date
        // Save and return the donation
        return donationRepository.save(donation);
    }

    @Override
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }

    @Override
    public Optional<Donor> findByUsername(String username) {
        return donorRepository.findByUserUsername(username);
    }

    @Override
    public Optional<Donor> findByUser(User user) {
        return donorRepository.findByUser(user);
    }

}