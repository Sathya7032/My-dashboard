package com.mydashboard.service;

import com.mydashboard.dto.JavaApplicationEditDto;
import com.mydashboard.dto.JobApplicationDto;
import com.mydashboard.entity.JobApplication;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface JobApplicationService {
    JobApplication saveJobApplication(JobApplicationDto jobApplicationDto) throws IOException;
    List<JobApplication> getAllApplications();
    Optional<JobApplication> getApplicationById(Long id);
    JobApplication updateJobApplication(Long id, JavaApplicationEditDto javaApplicationEditDto) throws IOException;
    void deleteJobApplication(Long id);


}
