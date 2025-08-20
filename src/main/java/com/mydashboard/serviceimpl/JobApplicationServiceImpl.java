package com.mydashboard.serviceimpl;

import com.mydashboard.aws.S3FileUploadService;
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
    public JobApplication updateJobApplication(Long id, JobApplication updatedJobApplication, MultipartFile resume) throws IOException {
        return jobApplicationRepository.findById(id).map(existing -> {
            existing.setCompanyName(updatedJobApplication.getCompanyName());
            existing.setCompanyWebsite(updatedJobApplication.getCompanyWebsite());
            existing.setCompanyLocation(updatedJobApplication.getCompanyLocation());
            existing.setJobTitle(updatedJobApplication.getJobTitle());
            existing.setJobRole(updatedJobApplication.getJobRole());
            existing.setJobDescriptionLink(updatedJobApplication.getJobDescriptionLink());
            existing.setDateApplied(updatedJobApplication.getDateApplied());
            existing.setResumeEditedForJob(updatedJobApplication.getResumeEditedForJob());
            existing.setReferencePersons(updatedJobApplication.getReferencePersons());
            existing.setReferenceEmails(updatedJobApplication.getReferenceEmails());
            existing.setReferenceReplies(updatedJobApplication.getReferenceReplies());
            existing.setApplicationStatus(updatedJobApplication.getApplicationStatus());
            existing.setResponseDate(updatedJobApplication.getResponseDate());
            existing.setNotes(updatedJobApplication.getNotes());

            if (resume != null && !resume.isEmpty()) {
                try {
                    String fileName = UUID.randomUUID() + "_" + resume.getOriginalFilename();
                    String s3Url = s3FileUploadService.uploadFile("resumes", resume, fileName);
                    existing.setResumeS3Url(s3Url);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to upload resume", e);
                }
            }

            return jobApplicationRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Job Application not found"));
    }

    @Override
    public void deleteJobApplication(Long id) {
        jobApplicationRepository.deleteById(id);
    }
}

