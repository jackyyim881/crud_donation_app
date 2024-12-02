package com.donation.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.donation.models.data.Donor;
import com.donation.models.data.Role;
import com.donation.models.data.User;
import com.donation.repository.DonorRepository;
import com.donation.repository.RoleRepository;
import com.donation.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthWebController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DonorRepository donorRepository;

    @Autowired
    public AuthWebController(UserService userService, RoleRepository roleRepository,
            BCryptPasswordEncoder passwordEncoder, DonorRepository donorRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.donorRepository = donorRepository;
    }

    // Show registration form (GET /auth/register)
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User()); // New empty User object to bind the form
        return "auth/register/register";
    }

    // Handle form submission for registration (POST /auth/register)
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Check if username or email already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username is already taken");
            return "auth/register/register";
        }

        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email is already registered");
            return "auth/register/register";
        }

        try {
            // Encrypt password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Fetch the default role from the database
            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                model.addAttribute("error", "Default role not found. Please contact support.");
                return "auth/register/register";
            }

            Set<Role> roles = new HashSet<>();
            roles.add(userRole);
            user.setRoles(roles);

            // Save user
            User savedUser = userService.save(user);

            // Create and save donor
            Donor donor = new Donor();
            donor.setUser(savedUser);
            donor.setDonorType("personal"); // Default to 'personal', can be updated later
            donorRepository.save(donor);

        } catch (Exception e) {
            model.addAttribute("error", "Failed to register user. Please try again.");
            return "auth/register/register";
        }

        // Redirect to login after successful registration
        return "redirect:/auth/login";
    }

    // Show login form (GET /auth/login)
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login/login";
    }
}
