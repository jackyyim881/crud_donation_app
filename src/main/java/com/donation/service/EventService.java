// src/main/java/com/donation/services/EventService.java

package com.donation.service;

import com.donation.models.data.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    // Create a new event
    Event createEvent(Event event);

    // Retrieve all events
    List<Event> getAllEvents();

    // Retrieve events within a date range
    List<Event> getEventsInRange(LocalDateTime start, LocalDateTime end);

    // Retrieve events by user
    List<Event> getEventsByUser(Long userId);

    // Update an existing event
    Event updateEvent(Long eventId, Event updatedEvent);

    // Delete an event
    void deleteEvent(Long eventId);
}
