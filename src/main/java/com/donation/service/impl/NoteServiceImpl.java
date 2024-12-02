package com.donation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.models.data.Note;
import com.donation.repository.NoteRepository;
import com.donation.service.NoteService;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note createQuickNote(Note note) {
        return noteRepository.save(note);
    }

    @Override
    public List<Note> getUserQuickNotes(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    @Override
    public Note updateQuickNote(Long noteId, Note noteDetails) {
        Note note = noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
        note.setContent(noteDetails.getContent());
        return noteRepository.save(note);
    }

    @Override
    public void deleteQuickNote(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    @Override
    public Note getNoteById(Long noteId) {
        return noteRepository.findById(noteId).orElseThrow(() -> new RuntimeException("Note not found"));
    }
}
