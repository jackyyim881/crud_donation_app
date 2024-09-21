package com.donation.service;

import com.donation.models.data.Campaign;

import java.util.List;

public interface CampaignService {
    Campaign createCampaign(Campaign campaign);

    List<Campaign> getAllCampaigns();

    Campaign getCampaignById(Long id);

    Campaign updateCampaign(Long id, Campaign campaignDetails);

    void deleteCampaign(Long id);
}