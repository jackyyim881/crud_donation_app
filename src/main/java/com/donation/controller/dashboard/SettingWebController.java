package com.donation.controller.dashboard;

import com.donation.models.data.User;
import com.donation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard/settings")
public class SettingWebController {

    @Autowired
    private UserService userService;

    // Display Settings Page
    @GetMapping
    public String getSettingsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "dashboard/settings/index"; // Points to a Thymeleaf settings template
    }

    // Handle User Information Update
    @PostMapping("/update-user")
    public String updateUserInformation(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("user") User updatedUser,
            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
            currentUser.setAddress(updatedUser.getAddress());
            currentUser.setName(updatedUser.getName());

            userService.updateUser(currentUser);
            model.addAttribute("message", "User information updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating user information: " + e.getMessage());
        }
        return "redirect:/dashboard/settings";
    }

    // Handle Notification Preferences Update
    @PostMapping("/update-notifications")
    public String updateNotificationPreferences(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false, defaultValue = "false") boolean emailNotifications,
            @RequestParam(required = false, defaultValue = "false") boolean smsNotifications,
            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            currentUser.setEmailNotifications(emailNotifications);
            currentUser.setSmsNotifications(smsNotifications);

            userService.updateUser(currentUser);
            model.addAttribute("message", "Notification preferences updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating notification preferences: " + e.getMessage());
        }
        return "redirect:/dashboard/settings";
    }

    // Handle App Configuration Update
    @PostMapping("/update-config")
    public String updateAppConfig(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String currency,
            @RequestParam String language,
            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            currentUser.setCurrency(currency);
            currentUser.setLanguage(language);

            userService.updateUser(currentUser);
            model.addAttribute("message", "App configuration updated successfully!");
        } catch (Exception e) {
            model.addAttribute("error", "Error updating app configuration: " + e.getMessage());
        }
        return "redirect:/dashboard/settings";
    }

    // Handle Profile Image Upload
    @PostMapping("/upload-image")
    public String uploadProfileImage(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("image") MultipartFile image,
            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            if (!image.isEmpty()) {
                currentUser.setProfileImage(image.getBytes());
                userService.updateUser(currentUser);
                model.addAttribute("message", "Profile image updated successfully!");
            } else {
                model.addAttribute("error", "Please select a valid image file.");
            }
        } catch (IOException e) {
            model.addAttribute("error", "Error uploading profile image: " + e.getMessage());
        }
        return "redirect:/dashboard/settings";
    }
}
