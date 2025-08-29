package com.example.healthcare.service;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Appointment;
import com.example.healthcare.model.AvailabilityTimeSlots;
import com.example.healthcare.model.DoctorAvailability;
import com.lambdista.util.Try;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AppointmentService {

    List<AvailabilityTimeSlots> availableSlots(int doctor_id);

    Try<ApiResponse<Appointment>> bookAppointment(Appointment appointment);

}
