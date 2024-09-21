package com.donation.controller.v1.api;

import com.donation.models.data.Campaign;
import com.donation.service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    // 1. Create a new campaign
    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@Valid @RequestBody Campaign campaign) {
        Campaign createdCampaign = campaignService.createCampaign(campaign);
        return ResponseEntity.status(201).body(createdCampaign); // HTTP 201 Created
    }

    // 2. Get all campaigns
    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        List<Campaign> campaigns = campaignService.getAllCampaigns();
        return ResponseEntity.ok(campaigns); // HTTP 200 OK
    }

    // 3. Get campaign by ID
    @GetMapping("/{id}")
    public ResponseEntity<Campaign> getCampaignById(@PathVariable Long id) {
        Campaign campaign = campaignService.getCampaignById(id);
        return ResponseEntity.ok(campaign); // HTTP 200 OK
    }

    // 4. Update an existing campaign
    @PutMapping("/{id}")
    public ResponseEntity<Campaign> updateCampaign(@PathVariable Long id,
            @Valid @RequestBody Campaign campaignDetails) {
        Campaign updatedCampaign = campaignService.updateCampaign(id, campaignDetails);
        return ResponseEntity.ok(updatedCampaign); // HTTP 200 OK
    }

    // 5. Delete a campaign
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
