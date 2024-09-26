// src/main/java/com/donation/controllers/web/UserWebController.java

package com.donation.controller;

import com.donation.models.data.User;
import com.donation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserWebController {

    private final UserService userService;

    @Autowired
    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    // Display list of users
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/list";
    }

    // Show form to create a new user
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "users/create";
    }

    // Handle creation of new user
    @PostMapping("/create")
    public String createUser(@ModelAttribute User user,
            @RequestParam("profileImageFile") MultipartFile profileImageFile) throws IOException {
        if (!profileImageFile.isEmpty()) {
            user.setProfileImage(profileImageFile.getBytes());
        }
        userService.save(user);
        return "redirect:/users";
    }

    // Show form to edit an existing user
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            // Handle user not found
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "users/edit";
    }

    // Handle update of user
    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
            @ModelAttribute User user,
            @RequestParam("profileImageFile") MultipartFile profileImageFile) throws IOException {
        User existingUser = userService.findById(id);
        if (existingUser == null) {
            // Handle user not found
            return "redirect:/users";
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(user.getPassword());
        }
        if (!profileImageFile.isEmpty()) {
            existingUser.setProfileImage(profileImageFile.getBytes());
        }
        userService.save(existingUser);
        return "redirect:/users";
    }

    // Handle deletion of user
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
