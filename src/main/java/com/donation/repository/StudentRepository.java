package com.donation.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.donation.models.data.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
