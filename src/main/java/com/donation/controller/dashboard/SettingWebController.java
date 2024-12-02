// src/main/java/com/donation/controller/dashboard/SettingWebController.java

package com.donation.controller.dashboard;

import com.donation.models.data.User;
import com.donation.service.UserService;
import com.donation.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;

@Controller
@RequestMapping("/dashboard/settings")
public class SettingWebController {

    private static final List<String> SUPPORTED_LANGUAGES = Arrays.asList("en", "es", "fr", "zh");
    private static final List<String> SUPPORTED_CURRENCIES = Arrays.asList("USD", "EUR", "CNY", "JPY");

    private final UserService userService;

    @Autowired
    public SettingWebController(UserService userService) {
        this.userService = userService;
    }

    // Display Settings Page
    @GetMapping
    public String getSettingsPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        try {
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);

            // Add supported languages and currencies
            model.addAttribute("supportedLanguages", SUPPORTED_LANGUAGES);
            model.addAttribute("supportedCurrencies", SUPPORTED_CURRENCIES);
        } catch (UserNotFoundException e) {
            // Handle exception, possibly redirect to login with an error
            return "redirect:/login";
        }

        return "dashboard/settings/index"; // Points to the Thymeleaf template
    }

    // Handle User Information Update
    @PostMapping("/update-user")
    public String updateUserInformation(@AuthenticationPrincipal UserDetails userDetails,
            @ModelAttribute("user") User updatedUser,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            // Update fields
            currentUser.setEmail(updatedUser.getEmail());
            currentUser.setPhoneNumber(updatedUser.getPhoneNumber());
            currentUser.setAddress(updatedUser.getAddress());
            currentUser.setName(updatedUser.getName());

            userService.updateUser(currentUser);
            redirectAttributes.addFlashAttribute("message", "user.info.update.success"); // Message key
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "user.info.update.error");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "user.info.update.error");
        }
        return "redirect:/dashboard/settings";
    }

    // Handle Notification Preferences Update
    @PostMapping("/update-notifications")
    public String updateNotificationPreferences(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(required = false) Boolean emailNotifications,
            @RequestParam(required = false) Boolean smsNotifications,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            currentUser.setEmailNotifications(emailNotifications != null ? emailNotifications : false);
            currentUser.setSmsNotifications(smsNotifications != null ? smsNotifications : false);

            userService.updateUser(currentUser);
            redirectAttributes.addFlashAttribute("message", "notification.preferences.update.success");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "notification.preferences.update.error");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "notification.preferences.update.error");
        }
        return "redirect:/dashboard/settings";
    }

    // Handle App Configuration Update
    @PostMapping("/update-config")
    public String updateAppConfig(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam String currency,
            @RequestParam String language,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        // Define supported languages and currencies
        List<String> supportedLanguages = SUPPORTED_LANGUAGES;
        List<String> supportedCurrencies = SUPPORTED_CURRENCIES;

        if (!supportedLanguages.contains(language)) {
            redirectAttributes.addFlashAttribute("error", "app.config.language.unsupported");
            return "redirect:/dashboard/settings";
        }

        if (!supportedCurrencies.contains(currency)) {
            redirectAttributes.addFlashAttribute("error", "app.config.currency.unsupported");
            return "redirect:/dashboard/settings";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            currentUser.setCurrency(currency);
            currentUser.setLanguage(language);

            userService.updateUser(currentUser);
            redirectAttributes.addFlashAttribute("message", "app.config.update.success");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "app.config.update.error");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "app.config.update.error");
        }
        return "redirect:/dashboard/settings";
    }

    // Handle Profile Image Upload
    @PostMapping("/upload-image")
    public String uploadProfileImage(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            User currentUser = userService.findByUsername(username);

            if (!image.isEmpty()) {
                currentUser.setProfileImage(image.getBytes());
                userService.updateUser(currentUser);
                redirectAttributes.addFlashAttribute("message", "profile.image.upload.success");
            } else {
                redirectAttributes.addFlashAttribute("error", "profile.image.upload.error.empty");
            }
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "profile.image.upload.error.user");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "profile.image.upload.error.io");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "profile.image.upload.error.unexpected");
        }
        return "redirect:/dashboard/settings";
    }

    // Handle Account Deletion
    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal UserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        try {
            String username = userDetails.getUsername();
            userService.deleteByUsername(username);
            redirectAttributes.addFlashAttribute("message", "account.deletion.success");
            return "redirect:/logout"; // Redirect to logout after deletion
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", "account.deletion.error.user");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "account.deletion.error.unexpected");
        }
        return "redirect:/dashboard/settings";
    }

}
