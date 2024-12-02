package com.donation.controller.dashboard;

import com.donation.models.data.Donation;
import com.donation.repository.DonationRepository;
import com.donation.specifications.DonationSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class SearchWebController {

    @Autowired
    private DonationRepository donationRepository;

    @GetMapping("/search")
    public String showSearchForm(Model model) {
        // Add any attributes needed for the search form, e.g., dropdown options
        return "search/index";
    }

    @PostMapping("/search")
    public String searchDonations(
            @RequestParam(value = "donorName", required = false) String donorName,
            @RequestParam(value = "studentName", required = false) String studentName,
            @RequestParam(value = "minAmount", required = false) Double minAmount,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            Model model) {
        Specification<Donation> spec = Specification.where(null);

        if (donorName != null && !donorName.isEmpty()) {
            spec = spec.and(DonationSpecification.hasDonorName(donorName));
        }

        if (studentName != null && !studentName.isEmpty()) {
            spec = spec.and(DonationSpecification.hasStudentName(studentName));
        }

        if (minAmount != null) {
            spec = spec.and(DonationSpecification.hasAmountGreaterThan(minAmount));
        }

        LocalDate startDate = (startDateStr != null && !startDateStr.isEmpty()) ? LocalDate.parse(startDateStr) : null;
        LocalDate endDate = (endDateStr != null && !endDateStr.isEmpty()) ? LocalDate.parse(endDateStr) : null;
        spec = spec.and(DonationSpecification.hasDonationDateBetween(startDate, endDate));

        List<Donation> results = donationRepository.findAll();
        model.addAttribute("donations", results);
        return "search/results";
    }
}
