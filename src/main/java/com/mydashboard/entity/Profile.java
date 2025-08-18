package com.mydashboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Info
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // Personal Details
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;

    // Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String postalCode;

    // Professional Info
    private String occupation;
    private String company;
    private String bio;


    // Audit
    private LocalDate createdAt;
    private LocalDate updatedAt;
}

