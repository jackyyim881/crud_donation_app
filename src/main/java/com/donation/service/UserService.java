package com.donation.service;

import java.util.List;

import com.donation.models.data.User;

public interface UserService {
    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();

    User findById(Long id);

}
