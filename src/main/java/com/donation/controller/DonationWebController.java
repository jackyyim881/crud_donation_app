package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/donations")
public class DonationWebController {

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

    // Serve create.html at /donations/create or /donations/create.html
    @GetMapping("/create")
    public String getCreatePage() {
        return "donations/create.html";
    }

    @GetMapping("/create.html")
    public String getCreatePageAlt() {
        return "donations/create.html";
    }

    // Serve edit.html at /donations/edit or /donations/edit.html
    @GetMapping("/edit")
    public String getEditPage() {
        return "donations/edit.html";
    }

    @GetMapping("/edit.html")
    public String getEditPageAlt() {
        return "donations/edit.html";
    }
}
