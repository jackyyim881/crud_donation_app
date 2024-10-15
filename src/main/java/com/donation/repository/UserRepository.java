package com.donation.repository;

import com.donation.models.data.User;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByUsername(String username);

    List<User> findAll();

    User save(User user);

    User findById(Long id);

    User deleteById(Long id);
}
