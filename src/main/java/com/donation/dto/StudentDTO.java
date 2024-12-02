package com.donation.dto;

import java.util.List;

public record StudentDTO(
        Long id,
        String name,
        int age,
        String school,
        String bio,
        Double latitude,
        Double longitude,
        String studentImageBase64,
        List<HomelessStudentDetailsDTO> homelessDetails) {
}