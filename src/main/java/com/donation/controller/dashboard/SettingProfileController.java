package com.donation.controller.dashboard;

import com.donation.models.data.User;
import com.donation.models.form.UserProfileForm;
import com.donation.repository.UserRepository;
import com.donation.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class SettingProfileController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public SettingProfileController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/dashboard/settings/setting-profile")
    public String getProfilePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        if (user == null) {
            return "redirect:/login";
        }

        // Fill the form with existing user data
        UserProfileForm userProfileForm = new UserProfileForm();
        userProfileForm.setName(user.getName());
        userProfileForm.setEmail(user.getEmail());
        userProfileForm.setPhoneNumber(user.getPhoneNumber());
        userProfileForm.setAddress(user.getAddress());
        userProfileForm.setLanguage(user.getLanguage());
        userProfileForm.setCurrency(user.getCurrency());
        userProfileForm.setEmailNotifications(user.isEmailNotifications());
        userProfileForm.setSmsNotifications(user.isSmsNotifications());

        model.addAttribute("userProfileForm", userProfileForm);

        return "dashboard/settings/setting-profile";
    }

    @PostMapping("/dashboard/settings/setting-profile")
    public String updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @ModelAttribute("userProfileForm") UserProfileForm userProfileForm,
            BindingResult result,
            Model model) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        User existingUser = userService.findByUsername(username);
        if (existingUser == null) {
            return "redirect:/login";
        }

        // Check for validation errors
        if (result.hasErrors()) {
            return "dashboard/settings/setting-profile";
        }

        // Handle profile image upload
        MultipartFile file = userProfileForm.getProfileImage();
        if (file != null && !file.isEmpty()) {
            try {
                // Validate file type (ensure it's an image)
                if (!file.getContentType().startsWith("image/")) {
                    result.rejectValue("profileImage", "error.userProfileForm", "Only image files are allowed.");
                    return "dashboard/settings/setting-profile";
                }

                // Validate file size (limit to 5MB)
                if (file.getSize() > 5 * 1024 * 1024) { // 5MB
                    result.rejectValue("profileImage", "error.userProfileForm", "File size exceeds the 5MB limit.");
                    return "dashboard/settings/setting-profile";
                }

                // Define the directory where the images will be stored
                String uploadDir = "uploads/";

                // Ensure the directory exists; create it if it does not
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    boolean dirCreated = uploadDirFile.mkdirs(); // Create the directory
                    if (!dirCreated) {
                        throw new IOException("Failed to create directory: " + uploadDir);
                    }
                }

                // Generate a unique file name for the image (e.g., user123_profile.jpg)
                String originalFilename = file.getOriginalFilename();
                String sanitizedFilename = Paths.get(originalFilename).getFileName().toString(); // Avoid directory
                                                                                                 // traversal
                String fileName = username + "_" + sanitizedFilename;

                // Create the path where the file will be saved
                Path filePath = Paths.get(uploadDir + fileName);

                // Write the file to the file system
                Files.write(filePath, file.getBytes());

                // Store the file name (not the full path) in the database
                existingUser.setProfileImagePath(fileName);
                existingUser.setProfileImage(file.getBytes()); // Optionally store image binary in DB

            } catch (IOException e) {
                e.printStackTrace();
                result.rejectValue("profileImage", "error.userProfileForm",
                        "Error uploading profile image: " + e.getMessage());
                return "dashboard/settings/setting-profile";
            }
        }

        // Save the updated user data
        existingUser.setEmail(userProfileForm.getEmail());
        existingUser.setPhoneNumber(userProfileForm.getPhoneNumber());
        existingUser.setAddress(userProfileForm.getAddress());
        existingUser.setName(userProfileForm.getName());
        existingUser.setEmailNotifications(userProfileForm.isEmailNotifications());
        existingUser.setSmsNotifications(userProfileForm.isSmsNotifications());
        existingUser.setLanguage(userProfileForm.getLanguage());
        existingUser.setCurrency(userProfileForm.getCurrency());

        try {
            userRepository.save(existingUser);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "Error updating profile: " + e.getMessage());
            return "dashboard/settings/setting-profile";
        }

        return "redirect:/dashboard/settings/setting-profile?success";
    }
}
