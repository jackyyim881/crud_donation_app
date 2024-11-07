package com.donation.service;

import com.donation.models.data.Event;
import java.util.List;

public interface EventService {
    List<Event> getAllEventsByUser(Long userId);

    Event saveEvent(Event event);

    Event getEventById(Long eventId);

    void deleteEvent(Long eventId);
}
