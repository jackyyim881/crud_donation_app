package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.donation.dto.DonationRequest;
import com.donation.models.data.Donation;
import com.donation.service.DonationService;

@Controller
@RequestMapping("/donations")
public class DonationWebController {

    private final DonationService donationService;

    public DonationWebController(DonationService donationService) {
        this.donationService = donationService;
    }

    // This will load the Thymeleaf template (donation.html)
    @GetMapping("/donate")
    public String showDonationForm() {
        // Returns the Thymeleaf template named 'donation'
        return "donation";
    }

    @GetMapping({ "", "/index.html" })
    public String getIndexPage() {
        return "donations/index.html";
    }

    @GetMapping("/create")
    public String getCreatePage() {
        return "donations/create";
    }

    @PostMapping("/create")
    public Donation createDonation(@RequestBody DonationRequest donationRequest) {
        return donationService.createDonation(donationRequest);
    }

    @GetMapping("/edit")
    public String getEditPage() {
        return "donations/edit.html";
    }

    @GetMapping("/edit.html")
    public String getEditPageAlt() {
        return "donations/edit.html";
    }
}
