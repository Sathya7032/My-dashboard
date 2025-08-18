package com.mydashboard.auth.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

    private final JavaMailSender mailSender;
    private final Map<String, String> otpStorage = new HashMap<>();

    public OtpService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Generate OTP and send via email
    public void generateAndSendOtp(String username, String email) throws MessagingException {
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(username, otp);

        sendOtpEmail(email, otp);
    }

    public boolean validateOtp(String username, String otp) {
        String validOtp = otpStorage.get(username);
        return validOtp != null && validOtp.equals(otp);
    }

    private void sendOtpEmail(String to, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject("Your Dashboard OTP Code");

        String htmlContent = """
                <html>
                <body>
                    <h2>Login OTP</h2>
                    <p>Your OTP for login is:</p>
                    <h1 style="color:blue;">%s</h1>
                    <p>This OTP will expire in 5 minutes.</p>
                </body>
                </html>
                """.formatted(otp);

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}

