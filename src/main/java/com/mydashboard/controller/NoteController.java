package com.mydashboard.controller;

import com.mydashboard.entity.Note;
import com.mydashboard.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/category/{categoryId}")
    public Note create(@RequestBody Note note, @PathVariable Long categoryId) {
        return noteService.createNote(note, categoryId);
    }

    @GetMapping("/{id}")
    public Note getById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @GetMapping
    public List<Note> getAll() {
        return noteService.getAllNotes();
    }

    @GetMapping("/category/{categoryId}")
    public List<Note> getByCategory(@PathVariable Long categoryId) {
        return noteService.getNotesByCategory(categoryId);
    }

    @PutMapping("/{id}/category/{categoryId}")
    public Note update(@PathVariable Long id, @RequestBody Note note, @PathVariable Long categoryId) {
        return noteService.updateNote(id, note, categoryId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        noteService.deleteNote(id);
    }
}
