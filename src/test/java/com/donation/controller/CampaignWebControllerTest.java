package com.donation.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.donation.models.data.Campaign;
import com.donation.models.data.CampaignAmountProjection;
import com.donation.service.CampaignService;
import com.donation.service.DonationService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CampaignWebController.class)
class CampaignWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CampaignService campaignService;

    @MockBean
    private DonationService donationService;

    // Test for getAllCampaigns method
    @Test
    void testGetAllCampaigns() throws Exception {
        when(campaignService.getAllCampaigns()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/campaigns"))
                .andExpect(status().isOk())
                .andExpect(view().name("campaigns/index"))
                .andExpect(model().attributeExists("campaigns"));
    }

    // Test for showCreateForm method
    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/campaigns/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("campaigns/create"))
                .andExpect(model().attributeExists("campaign"));
    }

    // Test for createCampaign method
    @Test
    void testCreateCampaign() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setName("New Campaign");

        mockMvc.perform(post("/campaigns/create")
                .flashAttr("campaign", campaign))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/campaigns"));

        verify(campaignService, times(1)).createCampaign(campaign);
    }

    // Test for showEditForm method
    @Test
    void testShowEditForm() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        when(campaignService.getCampaignById(1L)).thenReturn(campaign);

        mockMvc.perform(get("/campaigns/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("campaigns/edit"))
                .andExpect(model().attributeExists("campaign"));
    }

    // Test for updateCampaign method
    @Test
    void testUpdateCampaign() throws Exception {
        Campaign campaignDetails = new Campaign();
        campaignDetails.setName("Updated Campaign");

        mockMvc.perform(post("/campaigns/edit/1")
                .flashAttr("campaign", campaignDetails))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/campaigns"));

        verify(campaignService, times(1)).updateCampaign(1L, campaignDetails);
    }

    // Test for deleteCampaign method
    @Test
    void testDeleteCampaign() throws Exception {
        mockMvc.perform(get("/campaigns/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/campaigns"));

        verify(campaignService, times(1)).deleteCampaign(1L);
    }

    // Test for getCampaignById method
    @Test
    void testGetCampaignById() throws Exception {
        Campaign campaign = new Campaign();
        campaign.setId(1L);
        when(campaignService.getCampaignById(1L)).thenReturn(campaign);

        mockMvc.perform(get("/campaigns/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("campaigns/detail"))
                .andExpect(model().attributeExists("campaign"));
    }

    // Test for showCampaignAmounts method
    @Test
    void testShowCampaignAmounts() throws Exception {
        List<CampaignAmountProjection> campaignAmounts = Collections.emptyList();
        when(donationService.findCampaignIdAndAmount()).thenReturn(campaignAmounts);

        mockMvc.perform(get("/campaigns/campaign-amounts"))
                .andExpect(status().isOk())
                .andExpect(view().name("campaigns/campaign-amounts"))
                .andExpect(model().attributeExists("campaignAmounts"));
    }
}
