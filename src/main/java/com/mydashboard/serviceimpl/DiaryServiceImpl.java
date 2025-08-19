package com.mydashboard.serviceimpl;

import com.mydashboard.entity.DiaryEntry;
import com.mydashboard.repo.DiaryRepository;
import com.mydashboard.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    public DiaryEntry createEntry(DiaryEntry entry) {
        entry.setEntryDate(LocalDate.now());
        return diaryRepository.save(entry);
    }

    @Override
    public DiaryEntry getEntryById(Long id) {
        return diaryRepository.findById(id).orElseThrow(() -> new RuntimeException("Diary entry not found"));
    }

    @Override
    public List<DiaryEntry> getAllEntries() {
        return diaryRepository.findAll();
    }

    @Override
    public DiaryEntry updateEntry(Long id, DiaryEntry entry) {
        DiaryEntry existing = getEntryById(id);
        existing.setContent(entry.getContent());
        return diaryRepository.save(existing);
    }

    @Override
    public void deleteEntry(Long id) {
        diaryRepository.deleteById(id);
    }
}
