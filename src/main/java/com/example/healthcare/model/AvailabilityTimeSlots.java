package com.example.healthcare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AvailabilityTimeSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="doctor_availability_id", nullable=false)
    private DoctorAvailability doctorAvailabilityId;

    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private LocalTime startingTime;

    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private LocalTime endingTime;

    @NotNull(message = "Start time is required")
    @Column(nullable = false)
    private boolean isBooked;


}
