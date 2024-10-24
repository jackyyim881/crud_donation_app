// src/main/java/com/donation/dto/EventDTO.java

package com.donation.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public class EventDTO {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title can be at most 100 characters")
    private String title;

    @Size(max = 1000, message = "Description can be at most 1000 characters")
    private String description;

    @NotNull(message = "Start time is mandatory")
    private LocalDateTime start;

    @NotNull(message = "End time is mandatory")
    private LocalDateTime end;

    @Size(max = 50, message = "Category can be at most 50 characters")
    private String category;

    @Pattern(regexp = "^#(?:[0-9a-fA-F]{3}){1,2}$", message = "Color must be a valid hex code")
    private String color;

    private Long userId; // ID of the user who created the event

    // Constructors
    public EventDTO() {
    }

    public EventDTO(Long id, String title, String description, LocalDateTime start, LocalDateTime end, String category,
            String color, Long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.start = start;
        this.end = end;
        this.category = category;
        this.color = color;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters

}
