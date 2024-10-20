package com.donation.service.mapper;

import com.donation.dto.UserRequest;
import com.donation.dto.UserResponse;
import com.donation.models.data.User;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
public class UserMapperService {

    // Convert User entity to UserResponse DTO
    public UserResponse toDTO(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                encodeImageToBase64(user.getProfileImage()));
    }

    // Convert UserRequest DTO to User entity
    public User toEntity(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setAddress(userRequest.address());
        user.setPassword(userRequest.password()); // You may want to encode this in your service layer
        user.setProfileImage(decodeBase64Image(userRequest.profileImageBase64()));
        return user;
    }

    // Update an existing User entity from UserRequest DTO
    public User updateEntity(User existingUser, UserRequest userRequest) {
        existingUser.setUsername(userRequest.username());
        existingUser.setEmail(userRequest.email());
        existingUser.setPhoneNumber(userRequest.phoneNumber());
        existingUser.setAddress(userRequest.address());
        if (userRequest.password() != null && !userRequest.password().isEmpty()) {
            existingUser.setPassword(userRequest.password());
        }
        existingUser.setProfileImage(decodeBase64Image(userRequest.profileImageBase64()));
        return existingUser;
    }

    // Utility to convert byte[] to Base64 encoded string
    private String encodeImageToBase64(byte[] image) {
        return (image != null) ? Base64.getEncoder().encodeToString(image) : null;
    }

    // Utility to decode Base64 encoded string back to byte[]
    private byte[] decodeBase64Image(String base64Image) {
        return (base64Image != null) ? Base64.getDecoder().decode(base64Image) : null;
    }
}
