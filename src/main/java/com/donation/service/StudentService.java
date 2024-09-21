package com.donation.service;

import com.donation.models.data.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();

    Student getStudentById(int id);

    void saveStudent(Student student);

    void deleteStudentById(int id);
}