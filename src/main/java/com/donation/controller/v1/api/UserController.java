package com.donation.controller.v1.api;

import org.springframework.web.bind.annotation.RestController;

import com.donation.models.data.User;
import com.donation.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
// @RequestMapping("/api/v1/user")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // @GetMapping
    // public List<User> getAllUsers() {
    // return userService.getAllUsers();
    // }

    @PostMapping("/add")
    public String addUser(@RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email, Model model) {
        User user = new User();
        user.setUsername(firstName + " " + lastName);
        user.setEmail(email);

        userService.save(user);

        model.addAttribute("users", userService.getAllUsers());
        return "fragments/user-list :: userList"; // Return updated fragment
    }

    @GetMapping
    public String getUsers(Model model) {
        // Fetch all users
        List<User> users = userService.findAll();

        // Add users to the model so they are accessible in the template
        model.addAttribute("users", users);

        // Return the name of the Thymeleaf template (users.html)
        return "users";
    }

}
