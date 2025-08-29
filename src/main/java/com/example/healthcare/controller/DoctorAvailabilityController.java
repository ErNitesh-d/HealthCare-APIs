package com.example.healthcare.controller;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.DoctorAvailability;
import com.example.healthcare.service.DoctorAvailabilityService;
import com.lambdista.util.Try;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/availabilities")
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService  doctorAvailabilityService;

    @Autowired
    public DoctorAvailabilityController(DoctorAvailabilityService doctorAvailabilityService) {
        this.doctorAvailabilityService = doctorAvailabilityService;
    }

    /**
     * Adds a new doctor availability record.
     *
     * @param availability the availability details of the doctor
     * @return ResponseEntity containing ApiResponse with the created DoctorAvailability
     */
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<DoctorAvailability>> addDoctorAvailability(@Valid @RequestBody DoctorAvailability availability) {

        Try<ApiResponse<DoctorAvailability>> result = doctorAvailabilityService.add(availability);

        return new ResponseEntity<>(result.get(), HttpStatus.OK);

    }

    /**
     * Updates an existing doctor availability by its ID.
     *
     * @param doctorAvailabilityId the ID of the doctor availability to update
     * @param availability         the updated availability details
     * @return ResponseEntity containing ApiResponse with the updated DoctorAvailability
     */
    @PutMapping("/update/{doctorAvailabilityId}")
    public ResponseEntity<ApiResponse<DoctorAvailability>> updateDoctorAvailability(@PathVariable int doctorAvailabilityId,@Valid @RequestBody DoctorAvailability availability) {
        Try<ApiResponse<DoctorAvailability>> result= doctorAvailabilityService.update(doctorAvailabilityId,availability);
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

}
