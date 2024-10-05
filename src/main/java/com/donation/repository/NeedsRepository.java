package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.models.data.Needs;
import java.util.List;

@Repository
public interface NeedsRepository extends JpaRepository<Needs, Long> {
    List<Needs> findAllByStudentId(Long studentId);
}