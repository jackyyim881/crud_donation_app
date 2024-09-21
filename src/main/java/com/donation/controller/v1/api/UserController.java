package com.donation.controller.v1.api;

import com.donation.models.data.User;
import com.donation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Displays the registration form.
     * 
     * @param model The model to pass attributes to the view.
     * @return The registration template.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Handles the registration form submission.
     * 
     * @param user         The user entity bound from the form.
     * @param result       BindingResult to hold validation errors.
     * @param profileImage The uploaded profile image.
     * @param model        The model to pass attributes to the view.
     * @return Redirects to login on success or returns to registration on failure.
     */
    // @PostMapping("/register")
    // public String registerUser(@Validated @ModelAttribute("user") User user,
    // BindingResult result,
    // @RequestParam("profileImage") MultipartFile profileImage, Model model) {
    // if (result.hasErrors()) {
    // return "register";
    // }

    // // Check if email already exists
    // if (userService.findByEmail(user.getEmail()) != null) {
    // model.addAttribute("emailError", "Email already in use.");
    // return "register";
    // }

    // // Check if username already exists
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

    /**
     * Displays the login form.
     * 
     * @return The login template.
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Displays the dashboard after successful login.
     * 
     * @return The dashboard template.
     */
    @GetMapping("/dashboard")
    public String showDashboard() {
        return "dashboard";
    }

    /**
     * Displays all users.
     * 
     * @param model The model to pass attributes to the view.
     * @return The users list template.
     */
    @GetMapping("/all")
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "users"; // Thymeleaf 模板名稱
    }

    /**
     * Displays the form to add a new user.
     * 
     * @param model The model to pass attributes to the view.
     * @return The add user form template.
     */
    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "add-user-form"; // Thymeleaf template for adding a user
    }

    /**
     * Handles the add user form submission.
     * 
     * @param user         The user entity bound from the form.
     * @param result       BindingResult to hold validation errors.
     * @param profileImage The uploaded profile image.
     * @param model        The model to pass attributes to the view.
     * @return Redirects to users list on success or returns to add user form on
     *         failure.
     */
    // @PostMapping("/addUser")
    // public String addUser(@Validated @ModelAttribute("user") User user,
    // BindingResult result,
    // @RequestParam("profileImage") MultipartFile profileImage, Model model) {
    // if (result.hasErrors()) {
    // return "add-user-form";
    // }

    // // Check if email already exists
    // if (userService.findByEmail(user.getEmail()) != null) {
    // model.addAttribute("emailError", "Email already in use.");
    // return "add-user-form";
    // }

    // // Check if username already exists
    // if (userService.findByUsername(user.getUsername()) != null) {
    // model.addAttribute("usernameError", "Username already taken.");
    // return "add-user-form";
    // }

    // // Encode the password
    // user.setPassword(passwordEncoder.encode(user.getPassword()));

    // // Handle profile image
    // if (!profileImage.isEmpty()) {
    // try {
    // user.setProfileImage(profileImage.getBytes());
    // } catch (IOException e) {
    // model.addAttribute("imageError", "Failed to upload image.");
    // return "add-user-form";
    // }
    // }

    // // Save the user
    // userService.save(user);

    // // Redirect to the list of users with a success message
    // model.addAttribute("createSuccess", "User created successfully.");
    // return "redirect:/api/v1/user/all";
    // }

    /**
     * Displays the form to edit an existing user.
     * 
     * @param id    The ID of the user to edit.
     * @param model The model to pass attributes to the view.
     * @return The edit user form template or redirects to users list if user not
     *         found.
     */
    @GetMapping("/edit/{id}")
    public String showEditUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            // Handle user not found
            model.addAttribute("errorMessage", "User not found.");
            return "redirect:/api/v1/user/all";
        }
        model.addAttribute("user", user);
        return "edit-user-form";
    }

    /**
     * Handles the edit user form submission.
     * 
     * @param id           The ID of the user to edit.
     * @param user         The user entity bound from the form.
     * @param result       BindingResult to hold validation errors.
     * @param profileImage The uploaded profile image.
     * @param model        The model to pass attributes to the view.
     * @return Redirects to users list on success or returns to edit user form on
     *         failure.
     */
    // @PostMapping("/edit/{id}")
    // public String editUser(@PathVariable("id") Long id, @Validated
    // @ModelAttribute("user") User user,
    // BindingResult result, @RequestParam("profileImage") MultipartFile
    // profileImage, Model model) {
    // if (result.hasErrors()) {
    // return "edit-user-form";
    // }

    // User existingUser = userService.findById(id);
    // if (existingUser == null) {
    // // Handle user not found
    // model.addAttribute("errorMessage", "User not found.");
    // return "redirect:/api/v1/user/all";
    // }

    // // Check if email is being updated and if it's unique
    // if (!existingUser.getEmail().equals(user.getEmail()) &&
    // userService.findByEmail(user.getEmail()) != null) {
    // model.addAttribute("emailError", "Email already in use.");
    // return "edit-user-form";
    // }

    // // Check if username is being updated and if it's unique
    // if (!existingUser.getUsername().equals(user.getUsername())
    // && userService.findByUsername(user.getUsername()) != null) {
    // model.addAttribute("usernameError", "Username already taken.");
    // return "edit-user-form";
    // }

    // // Encode the password if it's being updated
    // if (user.getPassword() != null && !user.getPassword().isEmpty()) {
    // existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
    // }

    // // Update other fields
    // existingUser.setUsername(user.getUsername());
    // existingUser.setEmail(user.getEmail());
    // existingUser.setPhoneNumber(user.getPhoneNumber());
    // existingUser.setAddress(user.getAddress());

    // // Handle profile image
    // if (!profileImage.isEmpty()) {
    // try {
    // existingUser.setProfileImage(profileImage.getBytes());
    // } catch (IOException e) {
    // model.addAttribute("imageError", "Failed to upload image.");
    // return "edit-user-form";
    // }
    // }

    // // Save the updated user
    // userService.save(existingUser);

    // // Redirect to the list of users with a success message
    // model.addAttribute("updateSuccess", "User updated successfully.");
    // return "redirect:/api/v1/user/all";
    // }

    /**
     * Deletes a user by their ID.
     * 
     * @param id    The ID of the user to delete.
     * @param model The model to pass attributes to the view.
     * @return Redirects to users list with a success message.
     */
    // @GetMapping("/delete/{id}")
    // public String deleteUser(@PathVariable("id") Long id, Model model) {
    // User user = userService.findById(id);
    // if (user == null) {
    // model.addAttribute("errorMessage", "User not found.");
    // return "redirect:/api/v1/user/all";
    // }
    // userService.deleteById(id);
    // model.addAttribute("deleteSuccess", "User deleted successfully.");
    // return "redirect:/api/v1/user/all";
    // }
}
