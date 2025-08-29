package com.example.healthcare.model;

import com.example.healthcare.utils.AppointmentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int AppointmentId;

    @ManyToOne
    @JoinColumn(name = "patientId", nullable = false)
    private Patient patientId;

    @NotNull(message = "Doctor is required")
    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor_id;

    @NotNull(message = "Doctor Availabillity is required")
    @ManyToOne
    @JoinColumn(name = "doctorAvailability", nullable = false)
    private DoctorAvailability doctor_availability_id;


    private AppointmentStatus  status;

    private LocalDate appointmentDate;

}
