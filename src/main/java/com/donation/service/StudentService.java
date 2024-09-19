package com.donation.service;

import org.springframework.stereotype.Service;

import com.donation.repository.StudentRepository;
import com.donation.models.data.Student;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
