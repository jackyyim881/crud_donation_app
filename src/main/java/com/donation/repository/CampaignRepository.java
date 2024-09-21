package com.donation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donation.models.data.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    // Custom queries can be added if needed
}