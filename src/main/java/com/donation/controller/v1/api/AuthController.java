package com.donation.controller.v1.api;

import com.donation.models.data.User;
import com.donation.service.UserService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

@Controller
@RequestMapping("/")
public class AuthController {

    @Autowired
    private UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthController(UserService userService) {
        this.userService = userService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // @PostMapping("/register")
    // public String registerUser(@Validated @ModelAttribute("user") User user,
    // BindingResult result,
    // @RequestParam("profileImage") MultipartFile profileImage, Model model) {
    // if (result.hasErrors()) {
    // return "register";
    // }

    // // Check if email or username already exists
    // if (userService.findByEmail(user.getEmail()) != null) {
    // model.addAttribute("emailError", "Email already in use.");
    // return "register";
    // }
    // if (userService.findByUsername(user.getUsername()) != null) {
    // model.addAttribute("usernameError", "Username already taken.");
    // return "register";
    // }

    // // Encode the password
    // user.setPassword(passwordEncoder.encode(user.getPassword()));

    // // Handle profile image
    // if (!profileImage.isEmpty()) {
    // try {
    // user.setProfileImage(profileImage.getBytes());
    // } catch (IOException e) {
    // model.addAttribute("imageError", "Failed to upload image.");
    // return "register";
    // }
    // }

    // // Save the user
    // userService.save(user);

    // // Redirect to login page with success message
    // model.addAttribute("registerSuccess", "Registration successful! Please log
    // in.");
    // return "login";
    // }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
