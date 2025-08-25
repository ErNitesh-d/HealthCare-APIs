package com.example.healthcare.model;

import com.example.healthcare.utils.Days;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

/**
 * Entity representing a doctor's availability schedule including available day(s) and time .
 * Maps each availability record to a specific doctor.
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DoctorAvailabilityId;

    @NotBlank(message = "Doctor Is Required")
    @ManyToOne
    @JoinColumn(name = "doctorId")
    private Doctor doctorId;

    @NotBlank(message = "Days Of Availability Required")
    @Column(nullable = false)
    private Days availableDays;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;


}
