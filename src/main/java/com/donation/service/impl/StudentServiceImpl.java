package com.donation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.donation.models.data.Student;
import com.donation.repository.StudentRepository;
import com.donation.service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    // Fetch all students
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Fetch a student by ID
    @Override
    public Student getStudentById(Long id) {
        // Fetch the student by ID, or throw an exception if not found
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    // Save or update a student
    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    // Delete a student by ID
    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }
}
