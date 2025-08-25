package com.example.healthcare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Doctor Model Represents Details Of Doctor
 * This Model Includes Following Columns:
 * doctorId, doctorName, specializationId-(FK)(Specialization Class), email, phone
 * specializationId-(FK) Because Two Doctors Have Same Speciality
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int doctorId;

    @NotBlank(message = "Name Is Required")
    private String doctorName;

    @ManyToOne
    @JoinColumn(name = "specializationId", nullable = false)
    private Specialization specializationId;

    @Email(message = "Invalid Email Format")

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Mobile Number")
    private String phone;



}
