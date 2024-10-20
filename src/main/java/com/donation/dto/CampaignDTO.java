package com.donation.dto;

import java.math.BigDecimal;
import java.util.Date;

public record CampaignDTO(
        Long id,
        String name,
        String description,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        Date startDate,
        Date endDate) {

    public CampaignDTO withCurrentAmount(BigDecimal newCurrentAmount) {
        return new CampaignDTO(this.id, this.name, this.description, this.goalAmount, newCurrentAmount, this.startDate,
                this.endDate);
    }
}
