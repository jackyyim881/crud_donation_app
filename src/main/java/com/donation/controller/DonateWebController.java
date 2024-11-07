package com.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.donation.models.data.*;
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

    private final CampaignService campaignService;
    private final NotificationService notificationService;
    private final UserService userService;
    @Autowired
    public DonateWebController(DonorService donorService, DonationService donationService,
            NotificationService notificationService, UserService userService,
            CampaignService campaignService) {
        this.donorService = donorService;
        this.campaignService = campaignService;
        this.donationService = donationService;
        this.notificationService = notificationService;
        this.userService = userService;

    }

    @GetMapping("/donate")
    public String showDonationForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/auth/login"; // Redirect to login page if user is not authenticated
        }

        List<Student> students = studentService.getAllStudents();
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods();
        List<Campaign> campaigns = campaignService.getAllCampaigns();

        model.addAttribute("students", students);
        model.addAttribute("paymentMethods", paymentMethods);
        model.addAttribute("campaigns", campaigns);

        return "donation/index";
    }

    @PostMapping("/donate")
    public String donate(@RequestParam Long studentId,
            @RequestParam Long paymentMethodId,
            @RequestParam Double amount,
            @RequestParam Long campaignId,
            RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        if (user == null) {
            logger.error("User not found with username: {}", username);
            return "redirect:/donate";
        }
        Donor donor = donorService.findByUserUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Donor not found"));
        logger.debug("Donor found: {}", donor);

        Campaign campaign = campaignService.getCampaignById(campaignId);
        if (campaign == null) {
            logger.error("Campaign with ID {} not found", campaignId);
            redirectAttributes.addFlashAttribute("error", "Selected campaign not found.");
            return "redirect:/donate";
        }

        logger.debug("Donation request: studentId={}, paymentMethodId={}, amount={}, donorId={}, campaignId={}",
                studentId, paymentMethodId, amount, donor.getId(), campaign.getId());

        donationService.donate(studentId, paymentMethodId, amount, donor.getId(), campaign.getId());
        redirectAttributes.addFlashAttribute("message", "Donation successful!");

        // Send a notification to the user
        String message = String.format("Thank you for your donation of $%.2f to the %s campaign.", amount,
                campaign.getName());
        notificationService.sendNotification(donor.getId(), user.getId(), campaign.getId(), message);

        return "redirect:/donate";
    }

}
