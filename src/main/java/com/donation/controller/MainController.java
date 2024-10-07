package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @RequestMapping("/")
    public String getHomePage() {
        return "index.html";
    }

    @GetMapping("/contact")
    public String getContactPage() {
        return "contact/index.html";
    }

    @GetMapping("/about")
    public String getAboutPage() {
        return "about/index.html";
    }

}
