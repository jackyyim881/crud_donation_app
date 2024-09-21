package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.donation.models.data.DonorFeedback;

@Repository
public interface DonorFeedbackRepository extends JpaRepository<DonorFeedback, Integer> {
    // You can define custom query methods if necessary
}
