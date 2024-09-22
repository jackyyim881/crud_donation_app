package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DonationWebController {

    // This will load the Thymeleaf template (donation.html)
    @GetMapping("/donate")
    public String showDonationForm() {
        // Returns the Thymeleaf template named 'donation'
        return "donation";
    }
}
