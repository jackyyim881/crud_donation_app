package com.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.donation.models.data.Contact;
import com.donation.repository.ContactRepository;

@Controller
@RequestMapping("/contact")
public class ContactWebController {
    private final ContactRepository contactRepository;

    @Autowired
    public ContactWebController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    // Show contact form
    @GetMapping
    public String showContactForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "contact/contact"; // View located at src/main/resources/templates/contact.html
    }

    // Handle contact form submission
    @PostMapping
    public String submitContactForm(@ModelAttribute Contact contact, Model model) {
        contactRepository.save(contact);
        model.addAttribute("message", "Thank you for reaching out to us. We will get back to you soon.");
        return "contact/contact";
    }
}
