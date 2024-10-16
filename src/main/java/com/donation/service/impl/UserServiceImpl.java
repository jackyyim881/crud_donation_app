package com.donation.service.impl;

import com.donation.dto.UserDTO;
import com.donation.exception.ResourceNotFoundException;
import com.donation.models.data.Role;
import com.donation.models.data.User;
import com.donation.repository.RoleRepository;
import com.donation.repository.UserRepository;
import com.donation.service.UserService;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    // Implement UserService interface methods

    @Override
    public void registerUser(UserDTO userDTO, BCryptPasswordEncoder passwordEncoder) {
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new IllegalArgumentException("Username is already taken");
        }

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered");
        }

        // Create a new User entity from the DTO
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Fetch the default role (ROLE_USER) from the database
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            throw new ResourceNotFoundException("Default role not found");
        }

        // Assign the default role to the user
        Set<Role> roles = new HashSet<>();
        roles.add(userRole); // Adding ROLE_USER as default
        user.setRoles(roles);

        // Save the user in the database
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null); // Correct variable name
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null); // Handle Optional
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getDonorById(Long id) {
        return userRepository.getDonorById(id);
    }

    @Override

    public List<User> findAll() { // 修改方法簽名
        return userRepository.findAll(); // 返回 List<User>
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            logger.error("Failed to find user: {}", username);
            throw new UsernameNotFoundException("User not found");
        }
        User user = optionalUser.get();

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

}
