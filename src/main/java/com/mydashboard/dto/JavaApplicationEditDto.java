package com.mydashboard.dto;

import com.mydashboard.enums.ApplicationStatus;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Data
public class JavaApplicationEditDto {

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
    private MultipartFile resumeS3Url; // handle file upload

    // 🔹 Reference Tracking
    private List<String> referencePersons;
    private List<String> referenceEmails;
    private List<String> referenceReplies;

    // 🔹 Response from Company
    private ApplicationStatus applicationStatus;
    private LocalDate responseDate;

    // 🔹 Notes
    private String notes;
}
