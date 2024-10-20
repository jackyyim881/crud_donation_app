package com.donation.controller.v1.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donation.models.data.Donor;
import com.donation.models.data.User;
import com.donation.service.DonorService;
import com.donation.service.UserService;

@RestController
@RequestMapping("/api/v1/donors")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @Autowired
    private UserService userService;

    @GetMapping("/current")
    public Donor getDonorForCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        return donorService.getDonorById(user.getId());
    }
}
