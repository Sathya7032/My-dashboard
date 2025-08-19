package com.mydashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "category_id") // foreign key
    private Category category;
}

