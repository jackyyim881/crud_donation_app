package com.donation.controller.v1.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.donation.dto.StudentDTO;
import com.donation.models.data.Student;
import com.donation.service.StudentService;
import com.donation.service.mapper.StudentMapperService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    private final StudentService studentService;
    private final StudentMapperService studentMapperService;

    // Constructor injection for better testability and immutability
    public StudentController(StudentService studentService, StudentMapperService studentMapperService) {
        this.studentService = studentService;
        this.studentMapperService = studentMapperService;
    }

    /**
     * Get all students.
     *
     * @return List of students as DTOs.
     */
    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<StudentDTO> students = studentService.getAllStudents().stream()
                .map(studentMapperService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(students);
    }

    /**
     * Get a student by ID.
     *
     * @param id Student ID.
     * @return StudentDTO object.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        StudentDTO studentDTO = studentMapperService.toDTO(student);
        return ResponseEntity.ok(studentDTO);
    }

    /**
     * Create a new student.
     *
     * @param studentDTO DTO of the student to create.
     * @return Created student as DTO.
     */
    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        Student student = studentMapperService.toEntity(studentDTO); // Add a method to convert DTO to entity
        studentService.saveStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentMapperService.toDTO(student));
    }

    /**
     * Update an existing student.
     *
     * @param id         Student ID.
     * @param studentDTO Updated student DTO object.
     * @return Updated student as DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO) {
        Student existingStudent = studentService.getStudentById(id);
        if (existingStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Update fields using DTO data
        existingStudent.setName(studentDTO.name());
        existingStudent.setAge(studentDTO.age());
        existingStudent.setSchool(studentDTO.school());
        existingStudent.setBio(studentDTO.bio());
        existingStudent.setStudentImage(studentMapperService.toEntity(studentDTO).getStudentImage());

        studentService.saveStudent(existingStudent);
        return ResponseEntity.ok(studentMapperService.toDTO(existingStudent));
    }

    /**
     * Delete a student by ID.
     *
     * @param id Student ID.
     * @return HTTP status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
