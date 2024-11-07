package com.donation.controller.dashboard;

import com.donation.models.data.Event;
import com.donation.models.data.User;
import com.donation.service.EventService;
import com.donation.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/dashboard")

public class CalendarWebController {
    private final UserService userService;
    private final EventService eventService;
    private static final Logger logger = LoggerFactory.getLogger(CalendarWebController.class);
    @Autowired
    public CalendarWebController(UserService userService, EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    // Display Calendar with Events
    @GetMapping("/calendar")
    public String getCalendarPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Fetch the current user based on the authenticated principal
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);

        // Fetch events associated with the current user
        List<Event> events = eventService.getAllEventsByUser(currentUser.getId());
        model.addAttribute("events", events);

        // Log each event's details
        events.forEach(
                event -> logger.info("Event for user {}: {}", currentUser.getUsername(), event.getDescription()));

        logger.info("Showing events for user {}", currentUser.getUsername());
        return "dashboard/calendar/home";
    }
    // Show form to create a new event
    @GetMapping("/calendar/new")
    public String showCreateEventForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("event", new Event());
        return "dashboard/calendar/event_form";
    }

    // Handle form submission for creating a new event
    @PostMapping("/calendar/new")
    public String createEvent(@Valid @ModelAttribute("event") Event event,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            return "dashboard/calendar/event_form";
        }
        event.setUser(currentUser);
        eventService.saveEvent(event);
        return "redirect:/dashboard/calendar?success";
    }

    // Show form to edit an existing event
    @GetMapping("/calendar/edit/{id}")
    public String showEditEventForm(@PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Event event = eventService.getEventById(id);

        if (event == null || !event.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/dashboard/calendar?error";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("event", event);
        return "dashboard/calendar/event_form";
    }

    // Handle form submission for updating an existing event
    @PostMapping("/calendar/edit/{id}")
    public String updateEvent(@PathVariable("id") Long id,
            @Valid @ModelAttribute("event") Event event,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Event existingEvent = eventService.getEventById(id);

        if (existingEvent == null || !existingEvent.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/dashboard/calendar?error";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("currentUser", currentUser);
            return "dashboard/calendar/event_form";
        }

        // Update fields
        existingEvent.setTitle(event.getTitle());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setCategory(event.getCategory());
        existingEvent.setColor(event.getColor());

        eventService.saveEvent(existingEvent);
        return "redirect:/dashboard/calendar?updated";
    }

    // Handle deleting an event
    @PostMapping("/calendar/delete/{id}")
    public String deleteEvent(@PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        Event event = eventService.getEventById(id);

        if (event == null || !event.getUser().getId().equals(currentUser.getId())) {
            return "redirect:/dashboard/calendar?error";
        }

        eventService.deleteEvent(id);
        return "redirect:/dashboard/calendar?deleted";
    }
}
