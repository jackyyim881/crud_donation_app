package com.donation.dto;

public record UserRequest(
        String username,
        String email,
        String phoneNumber,
        String address,
        String password,
        String profileImageBase64 // Profile image as Base64 encoded string
) {
}
