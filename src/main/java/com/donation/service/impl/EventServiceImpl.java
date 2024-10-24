// src/main/java/com/donation/services/impl/EventServiceImpl.java

package com.donation.service.impl;

import com.donation.models.data.Event;
import com.donation.repository.EventRepository;
import com.donation.service.EventService;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    // Constructor Injection
    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Create a new event
    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    // Retrieve all events
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Retrieve events within a date range
    @Override
    public List<Event> getEventsInRange(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findByStartBetween(start, end);
    }

    // Retrieve events by user
    @Override
    public List<Event> getEventsByUser(Long userId) {
        return eventRepository.findByUserId(userId);
    }

    // Update an existing event
    @Override
    public Event updateEvent(Long eventId, Event updatedEvent) {
        return eventRepository.findById(eventId)
                .map(event -> {
                    event.setTitle(updatedEvent.getTitle());
                    event.setDescription(updatedEvent.getDescription());
                    event.setStart(updatedEvent.getStart());
                    event.setEnd(updatedEvent.getEnd());
                    event.setCategory(updatedEvent.getCategory());
                    event.setColor(updatedEvent.getColor());
                    // Optionally, update user if needed
                    return eventRepository.save(event);
                })
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id " + eventId));
    }

    // Delete an event
    @Override
    public void deleteEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EntityNotFoundException("Event not found with id " + eventId);
        }
        eventRepository.deleteById(eventId);
    }
}
