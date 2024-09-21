package com.donation.repository;

import com.donation.models.data.User;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Finds a user by their email.
     * 
     * @param email The email to search for.
     * @return The User entity if found; otherwise, null.
     */
    User findByEmail(String email);

    /**
     * Finds a user by their username.
     * 
     * @param username The username to search for.
     * @return The User entity if found; otherwise, null.
     */
    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    User findById(Long id);

    User deleteById(Long id);
}
