package com.donation.models.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        event.setId(id);
        assertThat(event.getId()).isEqualTo(id);
    }

    @Test
    void testSetAndGetTitle() {
        String title = "Charity Run";
        event.setTitle(title);
        assertThat(event.getTitle()).isEqualTo(title);
    }

    @Test
    void testSetAndGetDescription() {
        String description = "A charity event to raise funds for education.";
        event.setDescription(description);
        assertThat(event.getDescription()).isEqualTo(description);
    }

    @Test
    void testSetAndGetStart() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 22, 9, 0);
        event.setStart(start);
        assertThat(event.getStart()).isEqualTo(start);
    }

    @Test
    void testSetAndGetEnd() {
        LocalDateTime end = LocalDateTime.of(2024, 11, 22, 17, 0);
        event.setEnd(end);
        assertThat(event.getEnd()).isEqualTo(end);
    }

    @Test
    void testSetAndGetCategory() {
        String category = "Sports";
        event.setCategory(category);
        assertThat(event.getCategory()).isEqualTo(category);
    }

    @Test
    void testSetAndGetColor() {
        String color = "#FF5733"; // A hex color
        event.setColor(color);
        assertThat(event.getColor()).isEqualTo(color);
    }

    @Test
    void testSetAndGetUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");

        event.setUser(user);
        assertThat(event.getUser()).isEqualTo(user);
        assertThat(event.getUser().getUsername()).isEqualTo("john_doe");
    }
}
