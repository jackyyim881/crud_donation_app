// // src/main/java/com/donation/controller/DonorWebController.java

// package com.donation.controller;

// import com.donation.models.data.Donor;
// import com.donation.models.data.User;
// import com.donation.service.DonorService;
// import com.donation.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;
// import jakarta.validation.Valid;
// import org.springframework.web.multipart.MultipartFile;
// import java.io.IOException;
// import java.nio.file.*;
// import java.util.Optional;
// import java.util.UUID;

// @Controller
// @RequestMapping("/donors")
// public class DonorWebController {

// private final DonorService donorService;
// private final UserService userService;

// // Directory to save uploaded images (optional if Donor has images)
// // private static final String UPLOAD_DIR =
// // "src/main/resources/static/images/donors/";

// @Autowired
// public DonorWebController(DonorService donorService, UserService userService)
// {
// this.donorService = donorService;
// this.userService = userService;
// }

// /**
// * Display all donors.
// */
// @GetMapping
// public String listDonors(Model model) {
// model.addAttribute("donors", donorService.getAllDonors());
// return "donor/list";
// }

// /**
// * Show the form to add a new donor.
// */
// @GetMapping("/add")
// public String showAddForm(Model model) {
// model.addAttribute("donor", new Donor());
// // If you need to select a User from existing Users
// model.addAttribute("users", userService.findAll());
// return "donor/add";
// }

// /**
// * Handle the submission of the add donor form.
// */
// @PostMapping("/add")
// public String addDonor(@Valid @ModelAttribute("donor") Donor donor,
// BindingResult result,
// @RequestParam("userId") Long userId,
// Model model) {
// // Check for validation errors
// if (result.hasErrors()) {
// model.addAttribute("users", userService.findAll());
// return "donor/add";
// }

// // Associate Donor with User
// User user = userService.getDonorById(userId);

// donor.setUser(user);

// // Save Donor
// donorService.saveDonor(donor);

// // Redirect to the list
// return "redirect:/donors";
// }

// /**
// * Show the form to edit an existing donor.
// */
// @GetMapping("/edit/{id}")
// public String showEditForm(@PathVariable("id") Long id, Model model) {
// Optional<Donor> donorOptional = donorService.getDonorById(id);
// if (!donorOptional.isPresent()) {
// model.addAttribute("errorMessage", "Donor not found.");
// return "redirect:/donors";
// }
// model.addAttribute("donor", donorOptional.get());
// model.addAttribute("users", userService.findAll());
// return "donor/edit";
// }

// /**
// * Handle the submission of the edit donor form.
// */
// @PostMapping("/edit/{id}")
// public String editDonor(@PathVariable("id") Long id,
// @Valid @ModelAttribute("donor") Donor donor,
// BindingResult result,
// @RequestParam("userId") Long userId,
// Model model) {
// // Check for validation errors
// if (result.hasErrors()) {
// model.addAttribute("users", userService.findAll());
// return "donor/edit";
// }

// // Ensure the donor exists
// Optional<Donor> existingDonorOptional = donorService.getDonorById(id);
// if (!existingDonorOptional.isPresent()) {
// model.addAttribute("errorMessage", "Donor not found.");
// return "redirect:/donors";
// }

// Donor existingDonor = existingDonorOptional.get();

// // Associate Donor with User
// User user = userService.getDonorById(userId);

// existingDonor.setUser(user);
// existingDonor.setDonorType(donor.getDonorType());

// // Save the updated Donor
// donorService.saveDonor(existingDonor);

// // Redirect to the list
// return "redirect:/donors";
// }

// /**
// * Delete a donor by ID.
// */
// // @GetMapping("/delete/{id}")
// // public String deleteDonor(@PathVariable("id") Long id, Model model) {
// // Optional<Donor> donorOptional = donorService.getDonorById(id);
// // if (!donorOptional.isPresent()) {
// // model.addAttribute("errorMessage", "Donor not found.");
// // return "redirect:/donors";
// // }

// // donorService.deleteDonorById(id);
// // return "redirect:/donors";
// // }
// }
