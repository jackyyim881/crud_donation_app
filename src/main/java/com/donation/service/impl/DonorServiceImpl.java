package com.donation.service.impl;

import com.donation.models.data.Donor;
import com.donation.models.data.User;
import com.donation.repository.DonorRepository;
import com.donation.repository.UserRepository;
import com.donation.service.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorServiceImpl implements DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private UserRepository userRepository;

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
    public void deleteDonor(Long id) {
        donorRepository.deleteById(id);
    }
}