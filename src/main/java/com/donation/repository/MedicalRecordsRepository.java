package com.donation.repository;

import com.donation.models.data.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordsRepository extends JpaRepository<MedicalRecords, Long> {
    List<MedicalRecords> findAllByStudentId(Long studentId);
}