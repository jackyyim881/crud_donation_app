package com.donation.service.mapper;

import com.donation.dto.EventDTO;
import com.donation.models.data.Event;
import com.donation.models.data.User;
import com.donation.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventMapperService {

    private final UserRepository userRepository;

    public EventMapperService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Convert Event entity to EventDTO
    public EventDTO toDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitle(event.getTitle());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setStart(event.getStart());
        eventDTO.setEnd(event.getEnd());
        eventDTO.setCategory(event.getCategory());
        eventDTO.setColor(event.getColor());

        if (event.getUser() != null) {
            eventDTO.setUserId(event.getUser().getId());
        }

        return eventDTO;
    }
    // Convert EventDTO to Event entity
    public Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
        event.setStart(eventDTO.getStart());
        event.setEnd(eventDTO.getEnd());
        event.setCategory(eventDTO.getCategory());
        event.setColor(eventDTO.getColor());

        if (eventDTO.getUserId() != null) {
            Optional<User> userOpt = userRepository.findById(eventDTO.getUserId());
            userOpt.ifPresent(event::setUser);
        }

        return event;
    }
}