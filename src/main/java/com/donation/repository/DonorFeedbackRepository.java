package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.donation.models.data.DonorFeedback;

import java.util.List;

@Repository
public interface DonorFeedbackRepository extends JpaRepository<DonorFeedback, Integer> {

    // Custom query to find feedbacks by donor type (personal or organization)
    @Query("SELECT df FROM DonorFeedback df WHERE df.donor.donorType = :donorType")
    List<DonorFeedback> findByDonorType(@Param("donorType") String donorType);

    List<DonorFeedback> findByDonor_DonorType(String donorType);
}