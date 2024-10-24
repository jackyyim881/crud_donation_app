package com.donation.controller.v1.api;

import com.donation.dto.EventDTO;
import com.donation.models.data.Event;
import com.donation.models.data.User;
import com.donation.service.EventService;
import com.donation.service.mapper.EventMapperService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;
    private final EventMapperService eventMapper;

    // Constructor Injection
    public EventController(EventService eventService, EventMapperService eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    // Create a new event
    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Fetch the logged-in user

        Event event = eventMapper.toEntity(eventDTO);
        event.setUser(user); // Set the user from the session instead of from the frontend

        Event createdEvent = eventService.createEvent(event);
        EventDTO createdEventDTO = eventMapper.toDTO(createdEvent);
        return new ResponseEntity<>(createdEventDTO, HttpStatus.CREATED);
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        List<EventDTO> eventDTOs = events.stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    // Get events within a date range
    @GetMapping("/range")
    public ResponseEntity<List<EventDTO>> getEventsInRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        List<Event> events = eventService.getEventsInRange(start, end);
        List<EventDTO> eventDTOs = events.stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    // Get events by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventDTO>> getEventsByUser(@PathVariable Long userId) {
        List<Event> events = eventService.getEventsByUser(userId);
        List<EventDTO> eventDTOs = events.stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    // Update an event
    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        Event event = eventMapper.toEntity(eventDTO);
        Event updatedEvent = eventService.updateEvent(id, event);
        EventDTO updatedEventDTO = eventMapper.toDTO(updatedEvent);
        return new ResponseEntity<>(updatedEventDTO, HttpStatus.OK);
    }

    // Delete an event
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}