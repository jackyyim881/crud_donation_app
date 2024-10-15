package com.donation.service;

import com.donation.dto.CampaignDTO;
import com.donation.models.data.Campaign;
import com.donation.models.data.Donation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CampaignService {
    Campaign createCampaign(Campaign campaign);

    List<Campaign> getAllCampaigns();

    Campaign getCampaignById(Long id);

    Campaign updateCampaign(Long id, Campaign campaignDetails);

    void deleteCampaign(Long id);

    List<CampaignDTO> getAllCampaignsWithCurrentAmount();

    Map<Long, BigDecimal> getTotalAmountPerCampaign();

    List<Donation> getDonationsByCampaignId(Long campaignId);
}