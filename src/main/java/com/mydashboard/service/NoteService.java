package com.mydashboard.service;

import com.mydashboard.entity.Note;

import java.util.List;

public interface NoteService {
    Note createNote(Note note, Long categoryId);
    Note getNoteById(Long id);
    List<Note> getAllNotes();
    List<Note> getNotesByCategory(Long categoryId);
    Note updateNote(Long id, Note note, Long categoryId);
    void deleteNote(Long id);
}

