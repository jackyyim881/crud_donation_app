package com.donation.service.impl;

import com.donation.dto.CampaignDTO;
import com.donation.exception.EntityNotFoundException; // Assume you have a custom exception

import com.donation.models.data.Campaign;
import com.donation.models.data.Donation;
import com.donation.repository.CampaignRepository;
import com.donation.repository.DonationRepository;
import com.donation.service.CampaignService;
import com.donation.service.mapper.CampaignMapperService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CampaignServiceImpl implements CampaignService {

    private final CampaignRepository campaignRepository;
    private final DonationRepository donationRepository;
    private final CampaignMapperService campaignMapperService; // Mapper Service for CampaignDTO

    // Constructor injection (best practice for testability and immutability)
    public CampaignServiceImpl(CampaignRepository campaignRepository,
            DonationRepository donationRepository,
            CampaignMapperService campaignMapperService) {
        this.campaignRepository = campaignRepository;
        this.donationRepository = donationRepository;
        this.campaignMapperService = campaignMapperService;
    }

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
                .orElseThrow(() -> new EntityNotFoundException("Campaign not found with id: " + id));
    }

    @Override
    public Campaign updateCampaign(Long id, Campaign campaignDetails) {
        Campaign campaign = getCampaignById(id); // Use existing method for reusability

        // Update fields
        campaign.setName(campaignDetails.getName());
        campaign.setDescription(campaignDetails.getDescription());
        campaign.setGoalAmount(campaignDetails.getGoalAmount());
        campaign.setStartDate(campaignDetails.getStartDate());
        campaign.setEndDate(campaignDetails.getEndDate());

        return campaignRepository.save(campaign);
    }

    @Override
    public void deleteCampaign(Long id) {
        Campaign campaign = getCampaignById(id); // Use existing method for reusability
        campaignRepository.delete(campaign);
    }

    @Override
    public List<CampaignDTO> getAllCampaignsWithCurrentAmount() {
        List<Campaign> campaigns = campaignRepository.findAll();
        Map<Long, BigDecimal> totals = getTotalAmountPerCampaign();

        // Use the CampaignMapperService to map Campaign to CampaignDTO
        List<CampaignDTO> campaignDTOs = new ArrayList<>();
        for (Campaign campaign : campaigns) {
            CampaignDTO dto = campaignMapperService.toDTO(campaign);
            // Set currentAmount from totals map
            dto = dto.withCurrentAmount(totals.getOrDefault(campaign.getId(), BigDecimal.ZERO));
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

    @Override
    public List<Donation> getDonationsByCampaignId(Long campaignId) {
        return campaignRepository.findDonationsByCampaignId(campaignId);
    }
}
