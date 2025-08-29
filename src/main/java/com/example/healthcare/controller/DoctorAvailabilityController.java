package com.example.healthcare.controller;

import com.example.healthcare.model.DoctorAvailability;
import com.example.healthcare.service.DoctorAvailabilityService;
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

    @PostMapping("/add")
    public ResponseEntity<DoctorAvailability> addDoctorAvailability(@Valid @RequestBody DoctorAvailability availability) {

        doctorAvailabilityService.add(availability);

        return new ResponseEntity<>(availability, HttpStatus.OK);

    }

    @PutMapping("/update/{doctorAvailabilityId}")
    public ResponseEntity<DoctorAvailability> updateDoctorAvailability(@PathVariable int doctorAvailabilityId,@Valid @RequestBody DoctorAvailability availability) {
        doctorAvailabilityService.update(doctorAvailabilityId,availability);
        return new ResponseEntity<>(availability, HttpStatus.OK);
    }

}
