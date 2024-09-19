// package com.donation.controller.v1.api;

// import com.donation.models.data.Donation;
// import com.donation.service.DonationService;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/api/v1/donation")
// public class DonationController {
// private final DonationService donationService;

// @Autowired
// public DonationController(DonationService donationService) {
// this.donationService = donationService;
// }

// @GetMapping
// public List<Donation> getAllDonations() {
// return donationService.getAllDonations();
// }

// }
