package com.mydashboard.serviceimpl;

import com.mydashboard.entity.Category;
import com.mydashboard.entity.Note;
import com.mydashboard.repo.CategoryRepository;
import com.mydashboard.repo.NoteRepository;
import com.mydashboard.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Note createNote(Note note, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        note.setCreatedAt(LocalDateTime.now());
        note.setCategory(category);

        return noteRepository.save(note);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found"));
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getNotesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return noteRepository.findByCategory(category);
    }

    @Override
    public Note updateNote(Long id, Note note, Long categoryId) {
        Note existing = getNoteById(id);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setTitle(note.getTitle());
        existing.setContent(note.getContent());
        existing.setCategory(category);

        return noteRepository.save(existing);
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}
