package com.donation.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.donation.models.data.User;

@Qualifier("userRepository")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}