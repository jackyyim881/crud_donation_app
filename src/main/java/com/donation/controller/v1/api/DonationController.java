package com.donation.controller.v1.api;

import com.donation.models.data.Donation;
import com.donation.service.DonationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    // 1. Create a new donation
    @PostMapping
    public ResponseEntity<Donation> createDonation(@Valid @RequestBody Donation donation) {
        Donation createdDonation = donationService.createDonation(donation);
        return ResponseEntity.status(201).body(createdDonation); // HTTP 201 Created
    }

    // 2. Get all donations
    @GetMapping
    public ResponseEntity<List<Donation>> getAllDonations() {
        List<Donation> donations = donationService.getAllDonations();
        return ResponseEntity.ok(donations); // HTTP 200 OK
    }

    // 3. Get donation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
        Donation donation = donationService.getDonationById(id);
        return ResponseEntity.ok(donation); // HTTP 200 OK
    }

    // 4. Update a donation
    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable Long id,
            @Valid @RequestBody Donation donationDetails) {
        Donation updatedDonation = donationService.updateDonation(id, donationDetails);
        return ResponseEntity.ok(updatedDonation); // HTTP 200 OK
    }

    // 5. Delete a donation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
