package com.mydashboard.controller;

import com.mydashboard.auth.service.JwtUtil;
import com.mydashboard.auth.service.OtpService;
import com.mydashboard.dto.AuthRequest;
import com.mydashboard.dto.OtpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final OtpService otpService;

    // Single user's email (hardcoded for personal dashboard)
    private final String userEmail = "sathichary581@gmail.com";

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, OtpService otpService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.otpService = otpService;
    }

    // Step 1: Verify username + password, then send OTP
    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) throws MessagingException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (auth.isAuthenticated()) {
            otpService.generateAndSendOtp(request.getUsername(), userEmail);
            return "OTP sent to registered email.";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    // Step 2: Verify OTP, then return JWT
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestBody OtpRequest request) {
        boolean valid = otpService.validateOtp(request.getUsername(), request.getOtp());

        if (valid) {
            return jwtUtil.generateToken(request.getUsername());
        } else {
            throw new RuntimeException("Invalid OTP");
        }
    }
}





