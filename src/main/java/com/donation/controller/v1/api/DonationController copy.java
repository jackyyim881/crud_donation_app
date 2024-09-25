// package com.donation.controller.v1.api;

// import com.donation.dto.DonationRequest;
// import com.donation.models.data.Donation;
// import com.donation.repository.DonationRepository;
// import com.donation.service.DonationService;

// import jakarta.validation.Valid;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.format.annotation.DateTimeFormat;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.time.LocalDate;

// @RestController
// @RequestMapping("/api/donations")
// public class DonationController {

// @Autowired
// private DonationService donationService;
// @Autowired
// private DonationRepository donationRepository;

// // 1. Create a new donation
// // @PostMapping
// // public ResponseEntity<Donation> createDonation(@Valid @RequestBody
// Donation
// // donation) {
// // Donation createdDonation = donationService.createDonation(donation);
// // return ResponseEntity.status(201).body(createdDonation); // HTTP 201
// Created
// // }

// @PostMapping
// public ResponseEntity<Donation> createDonation(@Valid @RequestBody
// DonationRequest donationRequest) {
// Donation createdDonation = donationService.createDonation(donationRequest);
// return ResponseEntity.status(201).body(createdDonation); // HTTP 201 Created
// }

// @GetMapping
// public ResponseEntity<List<Donation>> getDonations(
// @RequestParam(required = false) Double minAmount,
// @RequestParam(required = false) Double maxAmount,
// @RequestParam(required = false) @DateTimeFormat(iso =
// DateTimeFormat.ISO.DATE) LocalDate startDate,
// @RequestParam(required = false) @DateTimeFormat(iso =
// DateTimeFormat.ISO.DATE) LocalDate endDate,
// @RequestParam(required = false) Long campaignId,
// @RequestParam(required = false) Long donorId) {
// List<Donation> donations;
// if (minAmount == null && maxAmount == null && startDate == null && endDate ==
// null && campaignId == null
// && donorId == null) {
// // If no filters are provided, get all donations
// donations = donationService.getAllDonations();
// } else {
// // If filters are provided, use them to get filtered donations
// donations = donationService.getDonations(minAmount, maxAmount, startDate,
// endDate, campaignId, donorId);
// }
// return ResponseEntity.ok(donations); // HTTP 200 OK
// }

// // 3. Get donation by ID
// @GetMapping("/{id}")
// public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
// Donation donation = donationService.getDonationById(id);
// return ResponseEntity.ok(donation); // HTTP 200 OK
// }

// // 4. Update a donation
// @PutMapping("/{id}")
// public ResponseEntity<Donation> updateDonation(@PathVariable Long id,
// @Valid @RequestBody Donation donationDetails) {
// Donation updatedDonation = donationService.updateDonation(id,
// donationDetails);
// return ResponseEntity.ok(updatedDonation); // HTTP 200 OK
// }

// // 5. Delete a donation
// @DeleteMapping("/{id}")
// public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
// donationService.deleteDonation(id);
// return ResponseEntity.noContent().build(); // HTTP 204 No Content
// }
// }
