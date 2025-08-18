package com.mydashboard.controller;


import com.mydashboard.entity.EmailDetails;
import com.mydashboard.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailDetails details) {
        return emailService.sendEmail(details);
    }

    @PostMapping("/sendWithAttachments")
    public String sendEmailWithAttachments(@RequestBody EmailDetails details) {
        return emailService.sendEmailWithAttachments(details);
    }

    @PostMapping("/markReply/{id}")
    public EmailDetails markReply(@PathVariable Long id, @RequestParam boolean gotReply) {
        return emailService.markReplyStatus(id, gotReply);
    }

    @GetMapping("/")
    public List<EmailDetails> getAllEmails() {
        return emailService.getAllEmails();
    }

    @GetMapping("/{id}")
    public EmailDetails getEmailById(@PathVariable Long id) {
        return emailService.getEmailById(id);
    }
}

