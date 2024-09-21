package com.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.donation.models.data.Student;
import com.donation.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/web/students")
public class StudentWebController {

    private final StudentService studentService;

    // Directory to save uploaded images
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Autowired
    public StudentWebController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Display all students.
     */
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "list";
    }

    /**
     * Show the form to add a new student.
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "addform";
    }

    /**
     * Handle the submission of the add student form.
     */
    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("student") Student student,
            BindingResult result,
            @RequestParam("studentImageFile") MultipartFile studentImageFile,
            Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "addform";
        }

        // Handle image upload
        if (!studentImageFile.isEmpty()) {
            try {
                // Ensure upload directory exists
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename
                String filename = System.currentTimeMillis() + "_" + studentImageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);
                Files.copy(studentImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Set the image bytes in the student entity
                student.setStudentImage(Files.readAllBytes(filePath));
            } catch (IOException e) {
                model.addAttribute("imageError", "Failed to upload image.");
                return "addform";
            }
        }

        // Save the student
        studentService.saveStudent(student);

        // Redirect to the list
        return "redirect:/web/students";
    }

    /**
     * Show the form to edit an existing student.
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            // Handle student not found
            model.addAttribute("errorMessage", "Student not found.");
            return "redirect:/web/students";
        }
        model.addAttribute("student", student);
        return "editform";
    }

    /**
     * Handle the submission of the edit student form.
     */
    @PostMapping("/edit/{id}")
    public String editStudent(@PathVariable("id") int id,
            @Valid @ModelAttribute("student") Student student,
            BindingResult result,
            @RequestParam("studentImageFile") MultipartFile studentImageFile,
            Model model) {
        // Check for validation errors
        if (result.hasErrors()) {
            return "editform";
        }

        // Ensure the student exists
        Student existing = studentService.getStudentById(id);
        if (existing == null) {
            // Handle student not found
            model.addAttribute("errorMessage", "Student not found.");
            return "redirect:/web/students";
        }

        // Handle image upload
        if (!studentImageFile.isEmpty()) {
            try {
                // Ensure upload directory exists
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Generate unique filename
                String filename = System.currentTimeMillis() + "_" + studentImageFile.getOriginalFilename();
                Path filePath = uploadPath.resolve(filename);
                Files.copy(studentImageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Set the image bytes in the student entity
                student.setStudentImage(Files.readAllBytes(filePath));
            } catch (IOException e) {
                model.addAttribute("imageError", "Failed to upload image.");
                return "editform";
            }
        } else {
            // If no new image uploaded, retain existing image
            student.setStudentImage(existing.getStudentImage());
        }

        // Update student details
        existing.setName(student.getName());
        existing.setAge(student.getAge());
        existing.setSchool(student.getSchool());
        existing.setBio(student.getBio());
        existing.setStudentImage(student.getStudentImage());

        // Save the updated student
        studentService.saveStudent(existing);

        // Redirect to the list
        return "redirect:/web/students";
    }

    /**
     * Delete a student by ID.
     */
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") int id, Model model) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            // Handle student not found
            model.addAttribute("errorMessage", "Student not found.");
            return "redirect:/web/students";
        }

        studentService.deleteStudentById(id);
        return "redirect:/web/students";
    }
}
