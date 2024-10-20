package com.donation.service.mapper;

import com.donation.dto.CampaignDTO;
import com.donation.models.data.Campaign;
import org.springframework.stereotype.Service;

@Service
public class CampaignMapperService {

    // Convert Campaign entity to CampaignDTO
    public CampaignDTO toDTO(Campaign campaign) {
        return new CampaignDTO(
                campaign.getId(),
                campaign.getName(),
                campaign.getDescription(),
                campaign.getGoalAmount(),
                campaign.getCurrentAmount(),
                campaign.getStartDate(),
                campaign.getEndDate());
    }

    // Convert CampaignDTO to Campaign entity
    public Campaign toEntity(CampaignDTO campaignDTO) {
        Campaign campaign = new Campaign();
        campaign.setId(campaignDTO.id());
        campaign.setName(campaignDTO.name());
        campaign.setDescription(campaignDTO.description());
        campaign.setGoalAmount(campaignDTO.goalAmount());
        campaign.setCurrentAmount(campaignDTO.currentAmount());
        campaign.setStartDate(campaignDTO.startDate());
        campaign.setEndDate(campaignDTO.endDate());
        return campaign;
    }
}
