package com.mydashboard.repo;

import com.mydashboard.entity.Category;
import com.mydashboard.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByCategory(Category category);
}
