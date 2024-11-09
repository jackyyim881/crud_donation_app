package com.donation.controller;

import com.donation.models.data.*;
import com.donation.service.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DonateWebController.class)
class DonateWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @MockBean
    private PaymentMethodService paymentMethodService;

    @MockBean
    private DonorService donorService;

    @MockBean
    private DonationService donationService;

    @MockBean
    private CampaignService campaignService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private UserService userService;

    private Authentication authentication;
    private SecurityContext securityContext;

    @BeforeEach
    void setup() {
        // Set up mock security context
        authentication = mock(Authentication.class);
        securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("username");
    }

    // Test for GET /donate
    @Test
    void testShowDonationForm() throws Exception {
        // Mock data to be returned by the services
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn("username");

        when(studentService.getAllStudents()).thenReturn(Collections.emptyList());
        when(paymentMethodService.getAllPaymentMethods()).thenReturn(Collections.emptyList());
        when(campaignService.getAllCampaigns()).thenReturn(Collections.emptyList());

        ResultActions result = mockMvc.perform(get("/donate"));

        result.andExpect(status().isOk())
                .andExpect(view().name("donation/index"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("paymentMethods"))
                .andExpect(model().attributeExists("campaigns"));
    }

    // Test for POST /donate
    @Test
    void testDonate_Success() throws Exception {
        // Mock data
        User user = new User();
        user.setUsername("username");

        Donor donor = new Donor();
        donor.setId(1L);

        Campaign campaign = new Campaign();
        campaign.setId(1L);
        campaign.setName("Test Campaign");

        // Mock service method responses
        when(authentication.getName()).thenReturn("username");
        when(userService.findByUsername("username")).thenReturn(user);
        when(donorService.findByUserUsername("username")).thenReturn(Optional.of(donor));
        when(campaignService.getCampaignById(1L)).thenReturn(campaign);

        // Perform POST /donate
        ResultActions result = mockMvc.perform(post("/donate")
                .param("studentId", "1")
                .param("paymentMethodId", "1")
                .param("amount", "100.0")
                .param("campaignId", "1"));

        // Verify interactions and expected response
        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/donate"))
                .andExpect(flash().attribute("message", "Donation successful!"));

        verify(donationService, times(1)).donate(1L, 1L, 100.0, 1L, 1L);
        verify(notificationService, times(1))
                .sendNotification(eq(1L), anyLong(), eq(1L), anyString());
    }

    // Test for POST /donate with Campaign Not Found
    @Test
    void testDonate_CampaignNotFound() throws Exception {
        User user = new User();
        user.setUsername("username");

        Donor donor = new Donor();
        donor.setId(1L);

        when(authentication.getName()).thenReturn("username");
        when(userService.findByUsername("username")).thenReturn(user);
        when(donorService.findByUserUsername("username")).thenReturn(Optional.of(donor));
        when(campaignService.getCampaignById(1L)).thenReturn(null);

        ResultActions result = mockMvc.perform(post("/donate")
                .param("studentId", "1")
                .param("paymentMethodId", "1")
                .param("amount", "100.0")
                .param("campaignId", "1"));

        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/donate"))
                .andExpect(flash().attribute("error", "Selected campaign not found."));

        verify(donationService, never()).donate(anyLong(), anyLong(), anyDouble(), anyLong(), anyLong());
    }

    // Test for POST /donate with Donor Not Found
    @Test
    void testDonate_DonorNotFound() throws Exception {
        User user = new User();
        user.setUsername("username");

        when(authentication.getName()).thenReturn("username");
        when(userService.findByUsername("username")).thenReturn(user);
        when(donorService.findByUserUsername("username")).thenReturn(Optional.empty());

        ResultActions result = mockMvc.perform(post("/donate")
                .param("studentId", "1")
                .param("paymentMethodId", "1")
                .param("amount", "100.0")
                .param("campaignId", "1"));

        result.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/donate"));

        verify(donationService, never()).donate(anyLong(), anyLong(), anyDouble(), anyLong(), anyLong());
    }
}
