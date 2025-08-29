package com.example.healthcare.controller;

import com.example.healthcare.model.*;
import com.example.healthcare.service.AppointmentService;
import com.example.healthcare.service.DoctorAvailabilityService;
import com.example.healthcare.service.DoctorService;
import com.lambdista.util.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling appointment-related APIs.
 * Provides endpoints to fetch doctor details.
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DoctorService doctorService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, DoctorAvailabilityService doctorAvailabilityService, DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.doctorAvailabilityService = doctorAvailabilityService;
        this.doctorService = doctorService;
    }

    /**
     * Get all doctors.
     *
     * @return ResponseEntity containing ApiResponse with list of doctors
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Doctor>>> getAllDoctors() {
        return getApiResponseResponseEntity(doctorService);
    }

    /**
     * Utility method to wrap doctor list into ApiResponse.
     *
     * @param doctorService service for doctor operations
     * @return ResponseEntity containing ApiResponse with list of doctors from which patient select doctor for appointmemnt
     */

    static ResponseEntity<ApiResponse<List<Doctor>>> getApiResponseResponseEntity(DoctorService doctorService) {
        List<Doctor> doctors = doctorService.findAllDoctors();

        ApiResponse<List<Doctor>> response = new ApiResponse<>();
        response.setResponse(doctors);
        response.setMessage(null);
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Fetches available time slots for a given doctor.
     *
     * @param doctor_id the ID of the doctor
     * @return list of available slots wrapped in ApiResponse
     */
    @GetMapping("/availableSlots/{doctor_id}")
    public ResponseEntity<ApiResponse<List<AvailabilityTimeSlots>>> availableSlots(@PathVariable Integer doctor_id) {

        List<AvailabilityTimeSlots> slots = appointmentService.availableSlots(doctor_id);

        ApiResponse<List<AvailabilityTimeSlots>> response =
                new ApiResponse<>(slots,
                        "Available slots fetched successfully",
                        HttpStatus.OK.value());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/availableSlots/{doctor_id}/bookAppointment")
    public ResponseEntity<ApiResponse<Appointment>> bookAppointment(@PathVariable Integer doctor_id,@RequestBody Appointment appointment) {

        Try<ApiResponse<Appointment>> apiResponse= appointmentService.bookAppointment(appointment);

        return new ResponseEntity<>(apiResponse.get(),HttpStatus.OK);
    }


}
