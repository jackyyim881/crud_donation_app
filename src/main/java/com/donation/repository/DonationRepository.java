package com.donation.repository;

import com.donation.models.data.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT d FROM Donation d WHERE " +
            "(:minAmount IS NULL OR d.amount >= :minAmount) AND " +
            "(:maxAmount IS NULL OR d.amount <= :maxAmount) AND " +
            "(:startDate IS NULL OR d.donationDate >= :startDate) AND " +
            "(:endDate IS NULL OR d.donationDate <= :endDate) AND " +
            "(:campaignId IS NULL OR d.ncampaign.id = :campaignId) AND " +
            "(:donorId IS NULL OR d.donor.id = :donorId)")
    List<Donation> findDonations(
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("campaignId") Long campaignId,
            @Param("donorId") Long donorId);
}
