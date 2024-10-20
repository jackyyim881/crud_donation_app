package com.donation.controller.dashboard;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardWebController {
    @GetMapping("/home")
    public String getHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/auth/login";
        }

        String username = getUsername(authentication);
        model.addAttribute("username", username);
        System.out.println("Navigating to dashboard home page with username: " + username); // Debug logging
        return "dashboard/home";
    }

    private String getUsername(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userDetails.getUsername();
        } else if (authentication != null) {
            return authentication.getPrincipal().toString();
        }
        return "Guest";
    }
}
