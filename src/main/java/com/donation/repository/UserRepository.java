package com.donation.repository;

import com.donation.models.data.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    User save(User user);

    @Override
    Optional<User> findById(Long id);

    User getDonorById(Long id); // Incorrect Method

    void deleteById(Long id);
}
