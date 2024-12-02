package com.donation.controller.v1.api;

import com.donation.dto.EventDTO;
import com.donation.models.data.Event;
import com.donation.models.data.User;
import com.donation.service.EventService;
import com.donation.service.mapper.EventMapperService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.validation.Valid;

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
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Event event = eventMapper.toEntity(eventDTO);
        event.setUser(user);

        Event createdEvent = eventService.saveEvent(event);
        EventDTO createdEventDTO = eventMapper.toDTO(createdEvent);
        return new ResponseEntity<>(createdEventDTO, HttpStatus.CREATED);
    }

    // Get all events by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<EventDTO>> getAllEventsByUser(@PathVariable Long userId) {
        List<Event> events = eventService.getAllEventsByUser(userId);
        List<EventDTO> eventDTOs = events.stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(eventDTOs, HttpStatus.OK);
    }

    // Get event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EventDTO eventDTO = eventMapper.toDTO(event);
        return new ResponseEntity<>(eventDTO, HttpStatus.OK);
    }

    // Update an event by ID
    @PutMapping("/{eventId}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long eventId, @Valid @RequestBody EventDTO eventDTO) {
        Event existingEvent = eventService.getEventById(eventId);
        if (existingEvent == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal(); // Fetch the logged-in user

        // Update fields of the existing event with data from the DTO
        existingEvent.setTitle(eventDTO.getTitle());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setStart(eventDTO.getStart());
        existingEvent.setEnd(eventDTO.getEnd());
        existingEvent.setCategory(eventDTO.getCategory());
        existingEvent.setColor(eventDTO.getColor());
        existingEvent.setUser(user); // Set the user to ensure it's correct

        Event updatedEvent = eventService.saveEvent(existingEvent); // Use saveEvent to persist changes
        EventDTO updatedEventDTO = eventMapper.toDTO(updatedEvent);
        return new ResponseEntity<>(updatedEventDTO, HttpStatus.OK);
    }

    // Delete an event by ID
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        if (event == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
