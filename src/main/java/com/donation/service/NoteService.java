package com.donation.service;

import com.donation.models.data.Note;

import java.util.List;

public interface NoteService {
    Note createQuickNote(Note note);

    List<Note> getUserQuickNotes(Long integer);

    Note updateQuickNote(Long noteId, Note noteDetails);

    void deleteQuickNote(Long noteId);

    Note getNoteById(Long noteId);
}