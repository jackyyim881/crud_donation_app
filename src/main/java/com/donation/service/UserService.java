package com.donation.service;

import java.util.List;
import java.util.Optional;

import com.donation.models.data.User;

public interface UserService {
    User findByEmail(String email);

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    void delete(Long id);

    User getDonorById(Long id);
}
