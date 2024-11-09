// package com.donation.controller.v1.api;

// import com.donation.dto.CampaignDTO;
// import com.donation.models.data.Campaign;
// import com.donation.service.CampaignService;
// import com.donation.service.mapper.CampaignMapperService;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.BeforeEach;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.ResultActions;

// import static org.mockito.Mockito.*;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// import java.util.Arrays;
// import java.util.List;

// import com.fasterxml.jackson.databind.ObjectMapper;

// @WebMvcTest(CampaignController.class)
// class CampaignControllerTest {

// @Autowired
// private MockMvc mockMvc;

// @MockBean
// private CampaignService campaignService;

// @MockBean
// private CampaignMapperService campaignMapperService;

// @Autowired
// private ObjectMapper objectMapper;

// private CampaignDTO campaignDTO;
// private Campaign campaign;

// @BeforeEach
// void setUp() {
// campaignDTO = new Cam();
// campaignDTO.setId(1L);
// campaignDTO.setName("Campaign 1");
// campaignDTO.setDescription("Description of Campaign 1");

// campaign = new Campaign();
// campaign.setId(1L);
// campaign.setName("Campaign 1");
// campaign.setDescription("Description of Campaign 1");
// }

// // Test for POST /api/v1/campaigns (Create Campaign)
// @Test
// void testCreateCampaign() throws Exception {
// when(campaignMapperService.toEntity(any(CampaignDTO.class))).thenReturn(campaign);
// when(campaignService.createCampaign(any(Campaign.class))).thenReturn(campaign);
// when(campaignMapperService.toDTO(any(Campaign.class))).thenReturn(campaignDTO);

// ResultActions result = mockMvc.perform(post("/api/v1/campaigns")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(campaignDTO)));

// result.andExpect(status().isCreated())
// .andExpect(jsonPath("$.id").value(campaignDTO.id()))
// .andExpect(jsonPath("$.name").value(campaignDTO.name()))
// .andExpect(jsonPath("$.description").value(campaignDTO.description()));
// }

// // Test for GET /api/v1/campaigns (Get All Campaigns)
// @Test
// void testGetAllCampaigns() throws Exception {
// List<Campaign> campaigns = Arrays.asList(campaign);
// List<CampaignDTO> campaignDTOs = Arrays.asList(campaignDTO);

// when(campaignService.getAllCampaigns()).thenReturn(campaigns);
// when(campaignMapperService.toDTO(any(Campaign.class))).thenReturn(campaignDTO);

// ResultActions result = mockMvc.perform(get("/api/v1/campaigns")
// .contentType(MediaType.APPLICATION_JSON));

// result.andExpect(status().isOk())
// .andExpect(jsonPath("$[0].id").value(campaignDTO.id()))
// .andExpect(jsonPath("$[0].name").value(campaignDTO.name()))
// .andExpect(jsonPath("$[0].description").value(campaignDTO.description()));
// }

// // Test for GET /api/v1/campaigns/{id} (Get Campaign by ID)
// @Test
// void testGetCampaignById() throws Exception {
// when(campaignService.getCampaignById(1L)).thenReturn(campaign);
// when(campaignMapperService.toDTO(campaign)).thenReturn(campaignDTO);

// ResultActions result = mockMvc.perform(get("/api/v1/campaigns/{id}", 1L)
// .contentType(MediaType.APPLICATION_JSON));

// result.andExpect(status().isOk())
// .andExpect(jsonPath("$.id").value(campaignDTO.id()))
// .andExpect(jsonPath("$.name").value(campaignDTO.name()))
// .andExpect(jsonPath("$.description").value(campaignDTO.description()));
// }

// // Test for PUT /api/v1/campaigns/{id} (Update Campaign)
// @Test
// void testUpdateCampaign() throws Exception {
// when(campaignMapperService.toEntity(any(CampaignDTO.class))).thenReturn(campaign);
// when(campaignService.updateCampaign(eq(1L),
// any(Campaign.class))).thenReturn(campaign);
// when(campaignMapperService.toDTO(any(Campaign.class))).thenReturn(campaignDTO);

// ResultActions result = mockMvc.perform(put("/api/v1/campaigns/{id}", 1L)
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(campaignDTO)));

// result.andExpect(status().isOk())
// .andExpect(jsonPath("$.id").value(campaignDTO.id()))
// .andExpect(jsonPath("$.name").value(campaignDTO.name()))
// .andExpect(jsonPath("$.description").value(campaignDTO.description()));
// }

// // Test for DELETE /api/v1/campaigns/{id} (Delete Campaign)
// @Test
// void testDeleteCampaign() throws Exception {
// doNothing().when(campaignService).deleteCampaign(1L);

// ResultActions result = mockMvc.perform(delete("/api/v1/campaigns/{id}", 1L)
// .contentType(MediaType.APPLICATION_JSON));

// result.andExpect(status().isNoContent());
// verify(campaignService, times(1)).deleteCampaign(1L);
// }
// }
