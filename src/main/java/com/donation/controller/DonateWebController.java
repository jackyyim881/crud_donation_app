package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DonateWebController {
    @GetMapping("/donate")
    public String showDonationForm() {
        return "donation/index.html";
    }

}
