package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment-methods")
public class PaymentMethodWebController {

    // Serve index.html at /payment-methods or /payment-methods/index.html
    @GetMapping({ "", "/index.html" })
    public String getIndexPage() {
        return "payment-methods/index.html";
    }

    // Serve create.html at /payment-methods/create or /payment-methods/create.html
    @GetMapping("/create")
    public String getCreatePage() {
        return "payment-methods/create.html";
    }

    @GetMapping("/create.html")
    public String getCreatePageAlt() {
        return "payment-methods/create.html";
    }

    // Serve edit.html at /payment-methods/edit or /payment-methods/edit.html
    @GetMapping("/edit")
    public String getEditPage() {
        return "payment-methods/edit.html";
    }

    @GetMapping("/edit.html")
    public String getEditPageAlt() {
        return "payment-methods/edit.html";
    }
}
