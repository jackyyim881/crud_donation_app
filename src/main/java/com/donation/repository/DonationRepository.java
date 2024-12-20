package com.donation.repository;

import com.donation.models.data.CampaignAmountProjection;
import com.donation.models.data.CampaignTotalAmountProjection;
import com.donation.models.data.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
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

        List<Donation> findByAmountBetweenAndDonationDateBetweenAndNcampaignIdAndDonorId(
                        Double minAmount,
                        Double maxAmount,
                        LocalDate startDate,
                        LocalDate endDate,
                        Long campaignId,
                        Long donorId);

        @Query("SELECT d.ncampaign.id, SUM(d.amount) FROM Donation d GROUP BY d.ncampaign.id")
        List<Object[]> getTotalAmountPerCampaign();

        List<Donation> findByNcampaignId(Long campaignId);

        @Query("SELECT d.ncampaign.id AS campaignId, d.amount AS amount FROM Donation d")
        List<CampaignAmountProjection> findCampaignIdAndAmount();

        // Or use a native SQL query
        @Query(value = "SELECT campaign_id AS campaignId, amount AS amount FROM donation", nativeQuery = true)
        List<CampaignAmountProjection> findCampaignIdAndAmountNative();

        @Query("SELECT d.ncampaign.id AS campaignId, SUM(d.amount) AS totalAmount " +
                        "FROM Donation d GROUP BY d.ncampaign.id")
        List<CampaignTotalAmountProjection> findTotalAmountByCampaign();
}
