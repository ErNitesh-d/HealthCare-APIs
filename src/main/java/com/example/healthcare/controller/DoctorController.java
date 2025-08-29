package com.example.healthcare.controller;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.service.DoctorService;
import com.lambdista.util.Try;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.example.healthcare.controller.AppointmentController.getApiResponseResponseEntity;

@RestController
@RequestMapping("/doctor")

public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    /**
     * API to add a new doctor.
     *
     * @param doctor doctor details (validated)
     * @return saved doctor wrapped in ApiResponse with HTTP 200
     */
    @PostMapping("/addDoctor")
    public ResponseEntity<ApiResponse<Doctor>> addPatient(@Valid @RequestBody Doctor doctor) {

        Try<ApiResponse<Doctor>> result = doctorService.insertDoctor(doctor);

        return new ResponseEntity<>(result.get(),HttpStatus.OK);
    }

    /**
     * Update an existing doctor by ID.
     *
     * @param doctor_id doctor ID
     * @param doctor    updated doctor details (validated)
     * @return updated doctor wrapped in ApiResponse with HTTP 200
     */
    @PutMapping("/updateDoctorById/{doctor_id}")
    public ResponseEntity<ApiResponse<Doctor>> updateDoctorById(@PathVariable int doctor_id,@Valid @RequestBody Doctor doctor) {

        Try<ApiResponse<Doctor>> result=doctorService.updateDoctor(doctor_id,doctor);
        return new ResponseEntity<>(result.get(),HttpStatus.OK);
    }

    /**
     * Delete a doctor by ID.
     *
     * @param doctor_id doctor ID
     * @return ApiResponse with status and message
     */
    @DeleteMapping("/deleteDoctorById/{doctor_id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctorById(@PathVariable int doctor_id) {
        ApiResponse<Void> response = doctorService.deleteDoctor(doctor_id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Get all doctors.
     *
     * @return list of doctors wrapped in ApiResponse with HTTP 200
     */
    @GetMapping("/getAllDoctors")
    public ResponseEntity<ApiResponse<List<Doctor>>> getAllDoctors() {
        return getApiResponseResponseEntity(doctorService);
    }

}

