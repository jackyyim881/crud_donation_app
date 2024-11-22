package com.donation.models.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CampaignTest {

    private Campaign campaign;

    @BeforeEach
    void setUp() {
        campaign = new Campaign();
    }

    @Test
    void testDefaultValues() {
        assertThat(campaign.getCurrentAmount()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(campaign.getDonations()).isEmpty();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        campaign.setId(id);
        assertThat(campaign.getId()).isEqualTo(id);
    }

    @Test
    void testSetAndGetName() {
        String name = "Save the Forests";
        campaign.setName(name);
        assertThat(campaign.getName()).isEqualTo(name);
    }

    @Test
    void testSetAndGetDescription() {
        String description = "A campaign to raise funds for forest conservation.";
        campaign.setDescription(description);
        assertThat(campaign.getDescription()).isEqualTo(description);
    }

    @Test
    void testSetAndGetGoalAmount() {
        BigDecimal goalAmount = new BigDecimal("100000.00");
        campaign.setGoalAmount(goalAmount);
        assertThat(campaign.getGoalAmount()).isEqualByComparingTo(goalAmount);
    }

    @Test
    void testSetAndGetStartDate() {
        Date startDate = new Date();
        campaign.setStartDate(startDate);
        assertThat(campaign.getStartDate()).isEqualTo(startDate);
    }

    @Test
    void testSetAndGetEndDate() {
        Date endDate = new Date();
        campaign.setEndDate(endDate);
        assertThat(campaign.getEndDate()).isEqualTo(endDate);
    }

    @Test
    void testSetAndGetCurrentAmount() {
        BigDecimal currentAmount = new BigDecimal("50000.00");
        campaign.setCurrentAmount(currentAmount);
        assertThat(campaign.getCurrentAmount()).isEqualByComparingTo(currentAmount);
    }

    @Test
    void testDonationsAssociation() {
        Donation donation1 = new Donation();
        donation1.setId(1L);

        Donation donation2 = new Donation();
        donation2.setId(2L);

        List<Donation> donations = new ArrayList<>();
        donations.add(donation1);
        donations.add(donation2);

        campaign.setDonations(donations);

        assertThat(campaign.getDonations()).hasSize(2);
        assertThat(campaign.getDonations()).contains(donation1, donation2);
    }
}
