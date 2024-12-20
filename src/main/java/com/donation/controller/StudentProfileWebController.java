package com.donation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.donation.models.data.*;
import com.donation.service.StudentService;
import java.util.List;

@Controller
public class StudentProfileWebController {

    private final StudentService studentService;

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
        HomelessStudentDetails homelessStudentDetails = studentService.getHomelessStudentDetailsByStudentId(id);
        List<Needs> needs = studentService.getNeedsByStudentId(id);
        List<ProgressUpdate> progressUpdates = studentService.getProgressUpdatesByStudentId(id);
        List<ShelterHistory> shelterHistory = studentService.getShelterHistoryByStudentId(id);
        List<MedicalRecords> medicalRecords = studentService.getMedicalRecordsByStudentId(id);
        model.addAttribute("student", student);
        model.addAttribute("homelessStudentDetails", homelessStudentDetails);
        model.addAttribute("needs", needs);
        model.addAttribute("progressUpdates", progressUpdates);
        model.addAttribute("shelterHistory", shelterHistory);
        model.addAttribute("medicalRecords", medicalRecords);
        // Returns the Thymeleaf template named 'student_profile'
        return "student_profile/index";
    }
}
