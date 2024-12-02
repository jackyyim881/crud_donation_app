package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/receipts")
public class WebReceiptsController {
    // Serve the index.html at /receipts/
    @GetMapping
    public String getReceiptsPage() {
        return "receipts/index.html";
    }

    // Serve the create.html at /receipts/create
    @GetMapping("/create")
    public String getCreateReceiptPage() {
        return "receipts/create.html";
    }

    @GetMapping("/edit")
    public String getEditPage() {
        return "receipts/edit.html";
    }

    @GetMapping("/edit.html")
    public String getEditPageAlt() {
        return "receipts/edit.html";
    }
}