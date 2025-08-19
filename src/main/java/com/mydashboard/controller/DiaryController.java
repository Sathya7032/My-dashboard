package com.mydashboard.controller;

import com.mydashboard.entity.DiaryEntry;
import com.mydashboard.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public DiaryEntry create(@RequestBody DiaryEntry entry) {
        return diaryService.createEntry(entry);
    }

    @GetMapping("/{id}")
    public DiaryEntry getById(@PathVariable Long id) {
        return diaryService.getEntryById(id);
    }

    @GetMapping
    public List<DiaryEntry> getAll() {
        return diaryService.getAllEntries();
    }

    @PutMapping("/{id}")
    public DiaryEntry update(@PathVariable Long id, @RequestBody DiaryEntry entry) {
        return diaryService.updateEntry(id, entry);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        diaryService.deleteEntry(id);
    }
}
