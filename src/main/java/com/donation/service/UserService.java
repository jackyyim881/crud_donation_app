package com.donation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.donation.dto.UserDTO;
import com.donation.models.data.User;

public interface UserService {
    User findByEmail(String email);

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void delete(Long id);

    User getDonorById(Long id);

    void registerUser(UserDTO userDTO, BCryptPasswordEncoder passwordEncoder);

}
