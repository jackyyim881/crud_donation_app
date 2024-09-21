package com.donation.controller.v1.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donation.models.data.Student;
import com.donation.service.StudentService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Get all students.
     *
     * @return List of students.
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * Get a student by ID.
     *
     * @param id Student ID.
     * @return Student object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") int id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    /**
     * Create a new student.
     *
     * @param student Student object.
     * @return Created student object.
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        studentService.saveStudent(student);
        return new ResponseEntity<>(student, HttpStatus.CREATED);
    }

    /**
     * Update an existing student.
     *
     * @param id      Student ID.
     * @param student Updated student object.
     * @return Updated student object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") int id,
            @Valid @RequestBody Student student) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update fields
        existingStudent.setName(student.getName());
        existingStudent.setAge(student.getAge());
        existingStudent.setSchool(student.getSchool());
        existingStudent.setBio(student.getBio());
        existingStudent.setStudentImage(student.getStudentImage());

        studentService.saveStudent(existingStudent);
        return new ResponseEntity<>(existingStudent, HttpStatus.OK);
    }

    /**
     * Delete a student by ID.
     *
     * @param id Student ID.
     * @return HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        studentService.deleteStudentById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
