package com.example.healthcare.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
/**
 * Specialization Model Stores All Specialities in HealthCare
 * PK-specializationId referenced to Doctor Table
 *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int specializationId;

    @NotBlank(message = "Specialization name cannot be blank")
    private String speciality;
}
