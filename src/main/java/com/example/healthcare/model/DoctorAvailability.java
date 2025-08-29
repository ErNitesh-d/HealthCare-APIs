package com.example.healthcare.model;

import com.example.healthcare.utils.Days;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private int doctorAvailabilityId;

    @NotNull(message = "Doctor is required")
    @ManyToOne
    @JoinColumn(name = "doctorId", nullable = false)
    private Doctor doctorId;

    @NotNull(message = "Days of availability required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Days availableDays;

    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    @Column(nullable = false)
    private LocalTime endTime;

}
