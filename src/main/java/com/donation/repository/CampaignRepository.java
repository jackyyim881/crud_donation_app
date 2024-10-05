package com.donation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.donation.models.data.Campaign;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    // Custom queries can be added if needed

}