package com.mydashboard.controller;

import com.mydashboard.dto.JavaApplicationEditDto;
import com.mydashboard.dto.JobApplicationDto;
import com.mydashboard.entity.JobApplication;
import com.mydashboard.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/job-applications")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JobApplication> createJobApplication(@ModelAttribute JobApplicationDto jobApplicationDto) throws IOException {
        JobApplication saved = jobApplicationService.saveJobApplication(jobApplicationDto);
        return ResponseEntity.ok(saved);
    }


    // ✅ Get All Job Applications
    @GetMapping
    public ResponseEntity<List<JobApplication>> getAll() {
        return ResponseEntity.ok(jobApplicationService.getAllApplications());
    }

    // ✅ Get Job Application by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobApplication> getById(@PathVariable Long id) {
        return jobApplicationService.getApplicationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update Job Application (with new resume optional)
    @PutMapping("/{id}")
    public ResponseEntity<JobApplication> updateJobApplication(
            @PathVariable Long id,
            @ModelAttribute JavaApplicationEditDto javaApplicationEditDto
            ) throws IOException {
        JobApplication updated = jobApplicationService.updateJobApplication(id,javaApplicationEditDto);
        return ResponseEntity.ok(updated);
    }

    // ✅ Delete Job Application
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobApplication(@PathVariable Long id) {
        jobApplicationService.deleteJobApplication(id);
        return ResponseEntity.noContent().build();
    }
}
