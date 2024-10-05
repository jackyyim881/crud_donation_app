package com.donation.dto;

public class HomelessStudentDetailsDTO {
    private Long id;
    private String currentShelter;
    private String durationOfHomelessness;
    private String emergencyContact;
    private String specialNeeds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrentShelter() {
        return currentShelter;
    }

    public void setCurrentShelter(String currentShelter) {
        this.currentShelter = currentShelter;
    }

    public String getDurationOfHomelessness() {
        return durationOfHomelessness;
    }

    public void setDurationOfHomelessness(String durationOfHomelessness) {
        this.durationOfHomelessness = durationOfHomelessness;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public String getSpecialNeeds() {
        return specialNeeds;
    }

    public void setSpecialNeeds(String specialNeeds) {
        this.specialNeeds = specialNeeds;
    }

}
