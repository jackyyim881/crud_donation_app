package com.donation.repository;

import com.donation.models.data.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Find events within a date range
    List<Event> findByStartBetween(LocalDateTime start, LocalDateTime end);

    // Optionally, find events by user
    List<Event> findByUserId(Long userId);
}
