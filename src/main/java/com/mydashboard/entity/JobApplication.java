package com.mydashboard.entity;

import com.mydashboard.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Company Info
    private String companyName;
    private String companyWebsite;
    private String companyLocation;

    // 🔹 Job Info
    private String jobTitle;
    private String jobRole;
    private String jobDescriptionLink;
    private LocalDate dateApplied;

    // 🔹 Resume Info
    private Boolean resumeEditedForJob;
    private String resumeS3Url; // store uploaded resume file URL

    // 🔹 Reference Tracking
    @ElementCollection
    private List<String> referencePersons;

    @ElementCollection
    private List<String> referenceEmails;

    @ElementCollection
    private List<String> referenceReplies;

    // 🔹 Response from Company
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;
    private LocalDate responseDate;

    // 🔹 Notes
    @Column(length = 2000)
    private String notes;
}
