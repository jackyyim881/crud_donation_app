package com.donation.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.donation.models.data.HomelessStudentDetails;

@Repository
public interface HomelessStudentDetailsRepository extends JpaRepository<HomelessStudentDetails, Long> {
    Optional<HomelessStudentDetails> findByStudentId(Long studentId);
}