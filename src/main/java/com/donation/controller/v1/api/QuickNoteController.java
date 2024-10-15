package com.donation.controller.v1.api;

import com.donation.models.data.Note;
import com.donation.service.NoteService;
import com.donation.service.UserService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import com.donation.models.data.User;

@RestController
@RequestMapping("/api/v1/quick-notes")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class QuickNoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    // Create a new quick note
    @PostMapping("/create")
    public Note createQuickNote(@RequestBody Note note) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findByUsername(username);
        note.setUser(user); // Set the logged-in user
        return noteService.createQuickNote(note);
    }

    // Get all quick notes for a user
    @GetMapping("/user/{userId}")
    public List<Note> getUserQuickNotes(@PathVariable Long userId) {
        return noteService.getUserQuickNotes(userId);
    }

    // Update a quick note
    @PutMapping("/update/{noteId}")
    public Note updateQuickNote(@PathVariable Long noteId, @RequestBody Note noteDetails) {
        return noteService.updateQuickNote(noteId, noteDetails);
    }

    // Delete a quick note
    @DeleteMapping("/delete/{noteId}")
    public void deleteQuickNote(@PathVariable Long noteId) {
        noteService.deleteQuickNote(noteId);
    }
}
