package com.donation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.donation.models.data.Student;
import com.donation.service.StudentService;

@Controller
public class StudentProfileWebController {
    // This will load the Thymeleaf template (student_profile.html)
    @Autowired
    private final StudentService studentService;

    @Autowired
    public StudentProfileWebController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/student-profile")
    public String showStudentProfile(@RequestParam("id") Long id, Model model) {
        // Fetch the student profile by id and add it to the model
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return "error/404"; // Return a 404 error page if the student is not found
        }
        model.addAttribute("student", student);
        // Returns the Thymeleaf template named 'student_profile'
        return "student_profile/index";
    }
}
