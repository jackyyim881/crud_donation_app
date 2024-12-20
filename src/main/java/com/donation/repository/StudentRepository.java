package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.donation.models.data.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Student getStudentById(Long id);
}
