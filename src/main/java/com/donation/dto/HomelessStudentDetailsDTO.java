package com.donation.dto;

public record HomelessStudentDetailsDTO(
        Long id, String currentShelter, String durationOfHomelessness,
        String emergencyContact, String specialNeeds) {
}
