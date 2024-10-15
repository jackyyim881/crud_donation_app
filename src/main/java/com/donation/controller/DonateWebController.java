package com.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.donation.models.data.Donor;
import com.donation.models.data.PaymentMethod;
import com.donation.models.data.Student;
import com.donation.service.DonationService;
import com.donation.service.DonorService;
import com.donation.service.PaymentMethodService;
import com.donation.service.StudentService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DonateWebController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private PaymentMethodService paymentMethodService;

    @Autowired
    private DonationService donationService;

    @Autowired
    private DonorService donorService;

    @Autowired
    public DonateWebController(DonationService donationService, DonorService donorService) {
        this.donationService = donationService;
        this.donorService = donorService;
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

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Donor donor = donorService.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Donor not found"));

            donationService.donate(studentId, paymentMethodId, amount, donor.getId());
            redirectAttributes.addFlashAttribute("message", "Donation successful!");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Donation failed: " +
                    e.getMessage());
        }

        return "redirect:/donate";
    }

}
