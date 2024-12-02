package com.donation.dto;

public record UserResponse(
        Long id,
        String username,
        String email,
        String phoneNumber,
        String address,
        String profileImageBase64) {
}
