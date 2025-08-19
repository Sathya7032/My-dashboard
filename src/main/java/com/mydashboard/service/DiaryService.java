package com.mydashboard.service;

import com.mydashboard.entity.DiaryEntry;
import java.util.List;

public interface DiaryService {
    DiaryEntry createEntry(DiaryEntry entry);
    DiaryEntry getEntryById(Long id);
    List<DiaryEntry> getAllEntries();
    DiaryEntry updateEntry(Long id, DiaryEntry entry);
    void deleteEntry(Long id);
}

