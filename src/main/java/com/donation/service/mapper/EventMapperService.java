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
        return new EventDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStart(),
                event.getEnd(),
                event.getCategory(),
                event.getColor(),
                event.getUser().getId());
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