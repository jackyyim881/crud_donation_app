package com.donation.controller.dashboard;

import com.donation.models.data.User;
import com.donation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String getHome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "redirect:/auth/login";
        }

        String username = getUsername(authentication);
        model.addAttribute("username", username);

        // Fetch user details and add profile image path to model
        User user = userService.findByUsername(username);
        String profileImagePath = (user != null && user.getProfileImagePath() != null)
                ? "/uploads/" + user.getProfileImagePath()
                : "/images/default-profile.jpg"; // Default image
        model.addAttribute("profileImagePath", profileImagePath);

        return "dashboard/home";
    }

    private String getUsername(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return authentication.getPrincipal().toString();
    }
}
