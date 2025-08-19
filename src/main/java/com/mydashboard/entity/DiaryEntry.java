package com.mydashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiaryEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate entryDate;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;
}
