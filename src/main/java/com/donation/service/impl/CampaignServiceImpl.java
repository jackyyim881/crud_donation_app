package com.donation.service.impl;

import com.donation.dto.CampaignDTO;
import com.donation.models.data.Campaign;
import com.donation.repository.CampaignRepository;
import com.donation.repository.DonationRepository;
import com.donation.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Override
    public Campaign createCampaign(Campaign campaign) {
        return campaignRepository.save(campaign);
    }

    @Override
    public List<Campaign> getAllCampaigns() {
        return campaignRepository.findAll();
    }

    @Override
    public Campaign getCampaignById(Long id) {
        return campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id: " + id));
    }

    @Override
    public Campaign updateCampaign(Long id, Campaign campaignDetails) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id: " + id));

        campaign.setName(campaignDetails.getName());
        campaign.setDescription(campaignDetails.getDescription());
        campaign.setGoalAmount(campaignDetails.getGoalAmount());
        campaign.setStartDate(campaignDetails.getStartDate());
        campaign.setEndDate(campaignDetails.getEndDate());

        return campaignRepository.save(campaign);
    }

    @Override
    public void deleteCampaign(Long id) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found with id: " + id));
        campaignRepository.delete(campaign);
    }

    @Override
    public List<CampaignDTO> getAllCampaignsWithCurrentAmount() {
        List<Campaign> campaigns = campaignRepository.findAll();
        Map<Long, BigDecimal> totals = getTotalAmountPerCampaign();

        List<CampaignDTO> campaignDTOs = new ArrayList<>();
        for (Campaign campaign : campaigns) {
            CampaignDTO dto = new CampaignDTO();
            dto.setId(campaign.getId());
            dto.setName(campaign.getName());
            dto.setDescription(campaign.getDescription());
            dto.setGoalAmount(campaign.getGoalAmount());
            dto.setStartDate(campaign.getStartDate());
            dto.setEndDate(campaign.getEndDate());
            dto.setCurrentAmount(totals.getOrDefault(campaign.getId(), BigDecimal.ZERO));
            campaignDTOs.add(dto);
        }
        return campaignDTOs;
    }

    @Override
    public Map<Long, BigDecimal> getTotalAmountPerCampaign() {
        List<Object[]> totals = donationRepository.getTotalAmountPerCampaign();
        Map<Long, BigDecimal> map = new HashMap<>();
        for (Object[] obj : totals) {
            map.put((Long) obj[0], (BigDecimal) obj[1]);
        }
        return map;
    }

}