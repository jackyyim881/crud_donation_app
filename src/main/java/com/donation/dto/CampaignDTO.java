package com.donation.dto;

import java.math.BigDecimal;
import java.util.Date;

public class CampaignDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal goalAmount;
    private Date startDate;
    private Date endDate;
    private BigDecimal currentAmount;

    // Constructors
    public CampaignDTO() {
    }

    public CampaignDTO(Long id, String name, String description, BigDecimal goalAmount, Date startDate, Date endDate,
            BigDecimal currentAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currentAmount = currentAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }
}
