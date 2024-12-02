package com.donation.repository;

import com.donation.models.data.ShelterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShelterHistoryRepository extends JpaRepository<ShelterHistory, Long> {
    List<ShelterHistory> findAllByStudentId(Long studentId);
}
