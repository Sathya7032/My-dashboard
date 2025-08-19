package com.mydashboard.repo;

import com.mydashboard.entity.DiaryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntry, Long> {
}
