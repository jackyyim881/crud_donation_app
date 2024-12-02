package com.donation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.donation.models.data.Campaign;
import com.donation.models.data.Donation;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    // Custom query to get donations by campaign ID
    @Query("SELECT d FROM Donation d WHERE d.ncampaign.id = :campaignId")
    List<Donation> findDonationsByCampaignId(@Param("campaignId") Long campaignId);
}