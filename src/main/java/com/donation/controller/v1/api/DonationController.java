package com.donation.controller.v1.api;

import com.donation.dto.DonationRequest;
import com.donation.dto.DonationResponse;
import com.donation.models.data.Donation;
import com.donation.service.DonationService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    /**
     * Creates a new donation.
     */
    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@Valid @RequestBody DonationRequest donationRequest) {
        Donation donation = donationService.createDonation(donationRequest);
        DonationResponse response = mapToResponse(donation);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Retrieves donations with optional filters.
     */
    @GetMapping
    public ResponseEntity<List<DonationResponse>> getDonations(
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long donorId) {
        List<Donation> donations;
        if (minAmount == null && maxAmount == null && startDate == null && endDate == null && donorId == null) {
            donations = donationService.getAllDonations();
        } else {
            donations = donationService.getDonations(minAmount, maxAmount, startDate, endDate, donorId);
        }
        List<DonationResponse> responses = donations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Retrieves a donation by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getDonationById(@PathVariable Long id) {
        Donation donation = donationService.getDonationById(id);
        DonationResponse response = mapToResponse(donation);
        return ResponseEntity.ok(response);
    }

    /**
     * Updates a donation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DonationResponse> updateDonation(@PathVariable Long id,
            @Valid @RequestBody DonationRequest donationRequest) {
        Donation updatedDonation = donationService.updateDonation(id, donationRequest);
        DonationResponse response = mapToResponse(updatedDonation);
        return ResponseEntity.ok(response);
    }

    /**
     * Deletes a donation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Utility method to map Donation to DonationResponse.
     */
    private DonationResponse mapToResponse(Donation donation) {
        DonationResponse response = new DonationResponse();
        response.setId(donation.getId());
        response.setAmount(donation.getAmount());
        response.setDonorId(donation.getDonor().getId());
        response.setDonorName(donation.getDonor().getUser().getUsername()); // Assuming Donor's User has getName()
        response.setDonationDate(donation.getDonationDate());
        response.setCampaignId(donation.getNcampaign().getId());
        response.setCampaignName(donation.getNcampaign().getName());

        if (donation.getNcampaign() != null) {
            response.setCampaignId(donation.getNcampaign().getId());
            response.setCampaignName(donation.getNcampaign().getName());
        }

        if (donation.getPaymentMethod() != null) {
            response.setPaymentMethodId(donation.getPaymentMethod().getId());
            response.setPaymentMethodName(donation.getPaymentMethod().getMethodName());
        }
        return response;
    }
}
