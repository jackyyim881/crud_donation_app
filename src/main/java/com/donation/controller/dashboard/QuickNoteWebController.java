package com.donation.controller.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.donation.models.data.Note;
import com.donation.models.data.User;
import com.donation.service.NoteService;
import com.donation.service.UserService;

@Controller
@RequestMapping("/dashboard")

public class QuickNoteWebController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    // Display the Quick Notes page
    @GetMapping("/quick-notes")
    public String getQuickNotesPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        List<Note> quickNotes = noteService.getUserQuickNotes(user.getId());
        model.addAttribute("quickNotes", quickNotes);
        model.addAttribute("userId", user.getId());

        return "quick-notes/index"; // Assuming there's a Thymeleaf template named "quick-notes.html"
    }

    // Display form to create a new quick note
    @GetMapping("/quick-notes/create")
    public String showCreateNoteForm(Model model) {
        model.addAttribute("note", new Note());
        return "quick-notes/create"; // Return the Thymeleaf template for creating a note
    }

    // Handle form submission to create a new quick note
    @PostMapping("/quick-notes/create")
    public String createQuickNote(@ModelAttribute("note") Note note, @AuthenticationPrincipal UserDetails userDetails,
            Model model) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);
        note.setUser(user); // Set the logged-in user

        noteService.createQuickNote(note);
        return "redirect:/dashboard/quick-notes"; // Redirect back to the list of notes
    }

    // Display form to update an existing quick note
    @GetMapping("/quick-notes/update/{noteId}")
    public String showUpdateNoteForm(@PathVariable Long noteId, Model model) {
        System.out.println("Attempting to fetch note with ID: " + noteId);
        Note existingNote = noteService.getNoteById(noteId);
        if (existingNote == null) {
            System.out.println("Note with ID " + noteId + " not found");
        } else {
            System.out.println("Found note: " + existingNote.getContent());
        }
        model.addAttribute("note", existingNote);
        return "quick-notes/update";
    }

    // Handle form submission to update an existing quick note
    @PostMapping("/quick-notes/update/{noteId}")
    public String updateQuickNote(@PathVariable Long noteId, @ModelAttribute("note") Note noteDetails) {
        noteService.updateQuickNote(noteId, noteDetails);
        return "redirect:/dashboard/quick-notes"; // Redirect back to the list of notes
    }

    // Handle deletion of a quick note
    @GetMapping("/quick-notes/delete/{noteId}")
    public String deleteQuickNote(@PathVariable Long noteId) {
        noteService.deleteQuickNote(noteId);
        return "redirect:/dashboard/quick-notes"; // Redirect back to the list of notes
    }
}