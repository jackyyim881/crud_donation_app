package com.donation.repository;

import com.donation.models.data.Donor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long> {

    @Query("SELECT DISTINCT d.donorType FROM Donor d")
    List<String> findDistinctDonorType();
}