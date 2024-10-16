package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "donations/edit";
    }

}
