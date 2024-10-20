package com.donation.service;

import java.util.List;

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

    void registerUser(UserDTO userDTO, BCryptPasswordEncoder passwordEncoder);

<<<<<<< HEAD
=======
    void registerUser(UserDTO userDTO, BCryptPasswordEncoder passwordEncoder);

>>>>>>> c20a9643e4d9e22674313fe21adedf9df48d2ec9
}
