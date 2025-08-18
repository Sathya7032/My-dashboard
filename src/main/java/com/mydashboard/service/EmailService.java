package com.mydashboard.service;

import com.mydashboard.entity.EmailDetails;

import java.util.List;

public interface EmailService {

    String sendEmail(EmailDetails details);

    String sendEmailWithAttachments(EmailDetails details);

    EmailDetails markReplyStatus(Long emailId, boolean gotReply);

    List<EmailDetails> getAllEmails();

    EmailDetails getEmailById(Long id);
}

