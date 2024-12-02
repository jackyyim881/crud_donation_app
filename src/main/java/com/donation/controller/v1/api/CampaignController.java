package com.donation.controller.v1.api;

import com.donation.dto.CampaignDTO;
import com.donation.service.CampaignService;
import com.donation.service.mapper.CampaignMapperService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {

    private final CampaignService campaignService;
    private final CampaignMapperService campaignMapperService;

    // Constructor injection (recommended for better testability and immutability)
    public CampaignController(CampaignService campaignService, CampaignMapperService campaignMapperService) {
        this.campaignService = campaignService;
        this.campaignMapperService = campaignMapperService;
    }

    // 1. Create a new campaign
    @PostMapping
    public ResponseEntity<CampaignDTO> createCampaign(@Valid @RequestBody CampaignDTO campaignDTO) {
        var campaign = campaignMapperService.toEntity(campaignDTO);
        var createdCampaign = campaignService.createCampaign(campaign);
        var createdCampaignDTO = campaignMapperService.toDTO(createdCampaign);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCampaignDTO); // HTTP 201 Created
    }

    // 2. Get all campaigns
    @GetMapping
    public ResponseEntity<List<CampaignDTO>> getAllCampaigns() {
        List<CampaignDTO> campaigns = campaignService.getAllCampaigns().stream()
                .map(campaignMapperService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(campaigns); // HTTP 200 OK
    }

    // 3. Get campaign by ID
    @GetMapping("/{id}")
    public ResponseEntity<CampaignDTO> getCampaignById(@PathVariable Long id) {
        var campaign = campaignService.getCampaignById(id);
        var campaignDTO = campaignMapperService.toDTO(campaign);
        return ResponseEntity.ok(campaignDTO); // HTTP 200 OK
    }

    // 4. Update an existing campaign
    @PutMapping("/{id}")
    public ResponseEntity<CampaignDTO> updateCampaign(@PathVariable Long id,
            @Valid @RequestBody CampaignDTO campaignDTO) {
        var campaignDetails = campaignMapperService.toEntity(campaignDTO);
        var updatedCampaign = campaignService.updateCampaign(id, campaignDetails);
        var updatedCampaignDTO = campaignMapperService.toDTO(updatedCampaign);
        return ResponseEntity.ok(updatedCampaignDTO); // HTTP 200 OK
    }

    // 5. Delete a campaign
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCampaign(@PathVariable Long id) {
        campaignService.deleteCampaign(id);
        return ResponseEntity.noContent().build(); // HTTP 204 No Content
    }
}
