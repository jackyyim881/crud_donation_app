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

    private final CampaignService campaignService;

    @Autowired
    public DonateWebController(DonorService donorService, DonationService donationService,
            CampaignService campaignService) {
        this.donorService = donorService;
        this.campaignService = campaignService;
        this.donationService = donationService;
    }

    @GetMapping("/donate")
    public String showDonationForm(Model model) {
        List<Student> students = studentService.getAllStudents(); // Assuming a student service to get the list of
                                                                  // students
        List<PaymentMethod> paymentMethods = paymentMethodService.getAllPaymentMethods(); // Assuming a payment method
                                                                                          // service to get the list of
        List<Campaign> campaigns = campaignService.getAllCampaigns(); // Assuming a campaign service to get the list of
                                                                      // campaigns

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

        return "redirect:/donate";
    }

}
