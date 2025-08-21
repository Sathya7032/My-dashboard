package com.mydashboard.serviceimpl;

import com.mydashboard.aws.S3FileUploadService;
import com.mydashboard.dto.JavaApplicationEditDto;
import com.mydashboard.dto.JobApplicationDto;
import com.mydashboard.entity.JobApplication;
import com.mydashboard.repo.JobApplicationRepository;
import com.mydashboard.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private S3FileUploadService s3FileUploadService;

    @Override
    public JobApplication saveJobApplication(JobApplicationDto dto) throws IOException {
        JobApplication jobApplication = new JobApplication();

        jobApplication.setCompanyName(dto.getCompanyName());
        jobApplication.setCompanyWebsite(dto.getCompanyWebsite());
        jobApplication.setCompanyLocation(dto.getCompanyLocation());
        jobApplication.setJobTitle(dto.getJobTitle());
        jobApplication.setJobRole(dto.getJobRole());
        jobApplication.setJobDescriptionLink(dto.getJobDescriptionLink());
        jobApplication.setDateApplied(dto.getDateApplied());
        jobApplication.setResumeEditedForJob(dto.getResumeEditedForJob());

        // File upload to S3
        if (dto.getResumeS3Url() != null && !dto.getResumeS3Url().isEmpty()) {
            String fileName = "resume_" + UUID.randomUUID() + "_" + dto.getResumeS3Url().getOriginalFilename();
            String s3Url = s3FileUploadService.uploadFile("resumes", dto.getResumeS3Url(), fileName);
            jobApplication.setResumeS3Url(s3Url);
        }

        return jobApplicationRepository.save(jobApplication);
    }


    @Override
    public List<JobApplication> getAllApplications() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public Optional<JobApplication> getApplicationById(Long id) {
        return jobApplicationRepository.findById(id);
    }

    @Override
    public JobApplication updateJobApplication(Long id, JavaApplicationEditDto javaApplicationEditDto) throws IOException {
        return jobApplicationRepository.findById(id).map(existing -> {
            existing.setCompanyName(javaApplicationEditDto.getCompanyName());
            existing.setCompanyWebsite(javaApplicationEditDto.getCompanyWebsite());
            existing.setCompanyLocation(javaApplicationEditDto.getCompanyLocation());
            existing.setJobTitle(javaApplicationEditDto.getJobTitle());
            existing.setJobRole(javaApplicationEditDto.getJobRole());
            existing.setJobDescriptionLink(javaApplicationEditDto.getJobDescriptionLink());
            existing.setDateApplied(javaApplicationEditDto.getDateApplied());
            existing.setResumeEditedForJob(javaApplicationEditDto.getResumeEditedForJob());
            existing.setReferencePersons(javaApplicationEditDto.getReferencePersons());
            existing.setReferenceEmails(javaApplicationEditDto.getReferenceEmails());
            existing.setReferenceReplies(javaApplicationEditDto.getReferenceReplies());
            existing.setApplicationStatus(javaApplicationEditDto.getApplicationStatus());
            existing.setResponseDate(javaApplicationEditDto.getResponseDate());
            existing.setNotes(javaApplicationEditDto.getNotes());

            // File upload to S3
            if (javaApplicationEditDto.getResumeS3Url() != null && !javaApplicationEditDto.getResumeS3Url().isEmpty()) {
                String fileName = "resume_" + UUID.randomUUID() + "_" +  javaApplicationEditDto.getResumeS3Url().getOriginalFilename();
                String s3Url = null;
                try {
                    s3Url = s3FileUploadService.uploadFile("resumes", javaApplicationEditDto.getResumeS3Url(), fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                existing.setResumeS3Url(s3Url);
            }

            return jobApplicationRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Job Application not found"));
    }

    @Override
    public void deleteJobApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }
}

