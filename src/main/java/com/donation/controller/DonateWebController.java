package com.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.donation.models.data.*;
import com.donation.repository.DonorRepository;
import com.donation.service.*;

import java.util.List;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class DonateWebController {
    private static final Logger logger = LoggerFactory.getLogger(DonateWebController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    private final DonorService donorService;
    private final DonationService donationService;

    @Autowired
    public DonateWebController(DonorService donorService, DonationService donationService) {
        this.donorService = donorService;
        this.donationService = donationService;
    }

    @GetMapping("/donate")
    public String showDonationForm(Model model) {
        List<Student> students = studentService.getAllStudents(); // Assuming a student service to get the list of
                                                                  // students
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods(); // Assuming a payment method
                                                                                          // service to get the list of
                                                                                          // payment methods

        model.addAttribute("students", students);
        model.addAttribute("paymentMethods", paymentMethods);

        return "donation/index";
    }

    @PostMapping("/donate")
    public String donate(@RequestParam Long studentId,
            @RequestParam Long paymentMethodId,
            @RequestParam Double amount,
            RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Donor donor = donorService.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Donor not found"));
        logger.debug("Donor found: {}", donor);

        donationService.donate(studentId, paymentMethodId, amount, donor.getId());
        redirectAttributes.addFlashAttribute("message", "Donation successful!");

        return "redirect:/donate";
    }

}
