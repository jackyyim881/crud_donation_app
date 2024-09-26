// src/main/java/com/donation/controller/DonorFeedbackWebController.java

package com.donation.controller;

import com.donation.models.data.DonorFeedback;
import com.donation.service.DonorFeedbackService;
import com.donation.exception.DonorFeedbackNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.donation.service.DonorService;

@Controller
@RequestMapping("/feedbacks")
public class DonorFeedbackWebController {

    private final DonorFeedbackService donorFeedbackService;
    private final DonorService donorService; // Assuming you need to fetch donors for the form
    private static final Logger logger = LoggerFactory.getLogger(DonorFeedbackWebController.class);

    @Autowired
    public DonorFeedbackWebController(DonorFeedbackService donorFeedbackService, DonorService donorService) {
        this.donorFeedbackService = donorFeedbackService;
        this.donorService = donorService;
    }

    @GetMapping
    public String listFeedbacks(Model model) {
        List<DonorFeedback> feedbacks = donorFeedbackService.getAllFeedbacks();
        model.addAttribute("feedbacks", feedbacks);
        return "feedbacks/list";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        DonorFeedback feedback = new DonorFeedback();
        model.addAttribute("feedback", feedback);
        model.addAttribute("donors", donorService.getAllDonors()); // Populate donors for selection
        logger.info("Loaded create form with donors: {}", donorService.getAllDonors());

        return "feedbacks/create";
    }

    @PostMapping("/add")
    public String createFeedback(@Valid @ModelAttribute("feedback") DonorFeedback feedback,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("donors", donorService.getAllDonors());
            logger.error("Validation errors: {}", result.getAllErrors());

            return "feedbacks/create";
        }
        donorFeedbackService.createFeedback(feedback);
        logger.info("Created feedback: {}", feedback);

        return "redirect:/feedbacks";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        try {
            DonorFeedback feedback = donorFeedbackService.getFeedbackById(id);
            model.addAttribute("feedback", feedback);
            model.addAttribute("donors", donorService.getAllDonors());
            return "feedbacks/edit";
        } catch (DonorFeedbackNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "redirect:/feedbacks";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateFeedback(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("feedback") DonorFeedback feedbackDetails,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("donors", donorService.getAllDonors());
            return "feedbacks/edit";
        }
        try {
            donorFeedbackService.updateFeedback(id, feedbackDetails);
            return "redirect:/feedbacks";
        } catch (DonorFeedbackNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "redirect:/feedbacks";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable("id") Integer id, Model model) {
        try {
            donorFeedbackService.deleteFeedback(id);
            return "redirect:/feedbacks";
        } catch (DonorFeedbackNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "redirect:/feedbacks";
        }
    }

    @GetMapping("/donor-type")
    public String getFeedbacksByDonorType(@RequestParam("type") String donorType, Model model) {
        List<DonorFeedback> feedbacks = donorFeedbackService.getFeedbacksByDonorType(donorType);
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("donorType", donorType);
        return "feedbacks/list";
    }

    @GetMapping("/{id}")
    public String viewFeedback(@PathVariable("id") Integer id, Model model) {
        try {
            DonorFeedback feedback = donorFeedbackService.getFeedbackById(id);
            model.addAttribute("feedback", feedback);
            return "feedbacks/view";
        } catch (DonorFeedbackNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "redirect:/feedbacks";
        }
    }
}