package com.donation.repository;

import com.donation.models.data.Donor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    @Query("SELECT DISTINCT d.donorType FROM Donor d")
    List<String> findDistinctDonorType();

    Donor getDonorById(Long id);

    Optional<Donor> findByUserUsername(String username);
}