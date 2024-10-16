package com.donation.controller.v1.api;

import com.donation.dto.DonationRequest;
import com.donation.dto.DonationResponse;
import com.donation.exception.ResourceNotFoundException;
import com.donation.service.DonationService;
import com.donation.service.mapper.DonationMapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/donations")
@Validated // Enables input validation
public class DonationController {

    private final DonationService donationService;
    private final DonationMapperService donationMapperService;

    @Autowired
    public DonationController(DonationService donationService, DonationMapperService donationMapperService) {
        this.donationService = donationService;
        this.donationMapperService = donationMapperService;
    }

    /**
     * Creates a new donation.
     * 
     * @param donationRequest DTO containing donation details.
     * @return ResponseEntity containing the created donation.
     */
    @PostMapping
    public ResponseEntity<DonationResponse> createDonation(@Valid @RequestBody DonationRequest donationRequest) {
        var donation = donationService.createDonation(donationRequest);
        var response = donationMapperService.toDTO(donation);
        return ResponseEntity.status(201).body(response); // HTTP 201 Created
    }

    /**
     * Retrieves all donations or donations filtered by optional criteria.
     * 
     * @param minAmount  Optional minimum donation amount.
     * @param maxAmount  Optional maximum donation amount.
     * @param startDate  Optional start date for donations.
     * @param endDate    Optional end date for donations.
     * @param campaignId Optional campaign ID to filter by campaign.
     * @return ResponseEntity containing a list of filtered or all donations.
     */
    @GetMapping
    public ResponseEntity<List<DonationResponse>> getDonations(
            @RequestParam(required = false) Double minAmount,
            @RequestParam(required = false) Double maxAmount,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Long campaignId) {

        var donations = donationService.getDonations(minAmount, maxAmount, startDate, endDate, campaignId);
        var responses = donations.stream()
                .map(donationMapperService::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses); // HTTP 200 OK
    }

    /**
     * Retrieves a donation by its ID.
     * 
     * @param id The ID of the donation.
     * @return ResponseEntity containing the donation if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> getDonationById(@PathVariable Long id) {
        var donation = donationService.getDonationById(id);
        var response = donationMapperService.toDTO(donation);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    /**
     * Updates an existing donation.
     * 
     * @param id              The ID of the donation to update.
     * @param donationRequest DTO containing updated donation details.
     * @return ResponseEntity containing the updated donation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DonationResponse> updateDonation(
            @PathVariable Long id,
            @Valid @RequestBody DonationRequest donationRequest) {

        var updatedDonation = donationService.updateDonation(id, donationRequest);
        var response = donationMapperService.toDTO(updatedDonation);
        return ResponseEntity.ok(response); // HTTP 200 OK
    }

    /**
     * Deletes a donation by its ID.
     * 
     * @param id The ID of the donation to delete.
     * @return ResponseEntity with HTTP 204 No Content status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
