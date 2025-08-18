package com.mydashboard.serviceimpl;

import com.mydashboard.entity.EmailDetails;
import com.mydashboard.repo.EmailDetailsRepository;

import com.mydashboard.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;
    private final EmailDetailsRepository emailDetailsRepository;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendEmail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            javaMailSender.send(mailMessage);

            // Save email record to DB
            details.setGotReply(false);
            emailDetailsRepository.save(details);

            return "Mail Sent Successfully";
        } catch (Exception e) {
            return "Error in sending mail: " + e.getMessage();
        }
    }

    @Override
    public String sendEmailWithAttachments(EmailDetails details) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            helper.setText(details.getMsgBody());
            helper.setSubject(details.getSubject());

            // handle attachments, assuming CSV string of paths
            if (details.getAttachments() != null && !details.getAttachments().isEmpty()) {
                List<String> files = Arrays.asList(details.getAttachments().split(","));
                for (String path : files) {
                    FileSystemResource file = new FileSystemResource(new File(path.trim()));
                    helper.addAttachment(file.getFilename(), file);
                }
            }
            javaMailSender.send(mimeMessage);

            // Save to DB with reply false
            details.setGotReply(false);
            emailDetailsRepository.save(details);

            return "Mail Sent Successfully with attachments";
        } catch (MessagingException e) {
            return "Error in sending mail with attachments: " + e.getMessage();
        }
    }

    @Override
    public EmailDetails markReplyStatus(Long emailId, boolean gotReply) {
        Optional<EmailDetails> emailOpt = emailDetailsRepository.findById(emailId);
        if (emailOpt.isPresent()) {
            EmailDetails email = emailOpt.get();
            email.setGotReply(gotReply);
            return emailDetailsRepository.save(email);
        }
        return null;
    }

    @Override
    public List<EmailDetails> getAllEmails() {
        return emailDetailsRepository.findAll();
    }

    @Override
    public EmailDetails getEmailById(Long id) {
        return emailDetailsRepository.findById(id).orElse(null);
    }
}

