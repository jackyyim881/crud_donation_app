package com.donation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.donation.models.data.Student;
import com.donation.repository.StudentRepository;
import com.donation.service.StudentService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    // Constructor Injection
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Fetch all students
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Fetch a student by ID
    @Override
    public Student getStudentById(int id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);
    }

    // Save or update a student
    @Override
    public void saveStudent(Student student) {
        studentRepository.save(student);
    }

    // Delete a student by ID
    @Override
    public void deleteStudentById(int id) {
        studentRepository.deleteById(id);
    }
}
