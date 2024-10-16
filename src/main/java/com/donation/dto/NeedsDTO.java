package com.donation.dto;

import java.math.BigDecimal;

public record NeedsDTO(
        Long id,
        String needDescription,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        Long studentId // Optionally include only the student ID to prevent exposing the entire Student
                       // object
) {
}
