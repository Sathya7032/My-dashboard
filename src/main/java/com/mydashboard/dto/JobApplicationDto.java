package com.mydashboard.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class JobApplicationDto {

    private String companyName;
    private String companyWebsite;
    private String companyLocation;

    // 🔹 Job Info
    private String jobTitle;
    private String jobRole;
    private String jobDescriptionLink;
    private LocalDate dateApplied;
    private Boolean resumeEditedForJob;
    private MultipartFile resumeS3Url;
}
