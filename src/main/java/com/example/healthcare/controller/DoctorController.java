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

@RestController
@RequestMapping("/doctor")

public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/addDoctor")
    public ResponseEntity<ApiResponse<Doctor>> addPatient(@Valid @RequestBody Doctor doctor) {

        Try<ApiResponse<Doctor>> result = doctorService.insertDoctor(doctor);

        return new ResponseEntity<>(result.get(),HttpStatus.OK);
    }

    @PutMapping("/updateDoctorById/{doctor_id}")
    public ResponseEntity<ApiResponse<Doctor>> updateDoctorById(@PathVariable int doctor_id,@Valid @RequestBody Doctor doctor) {

        Try<ApiResponse<Doctor>> result=doctorService.  updateDoctor(doctor_id,doctor);
        return new ResponseEntity<>(result.get(),HttpStatus.OK);
    }

    @DeleteMapping("/deleteDoctorById/{doctor_id}")
    public ResponseEntity<ApiResponse<Void>> deleteDoctorById(@PathVariable int doctor_id) {
        ApiResponse<Void> response = doctorService.deleteDoctor(doctor_id);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/getAllDoctors")
    public ResponseEntity<ApiResponse<List<Doctor>>> getAllPatients() {
        List<Doctor> doctors = doctorService.findAllDoctors();

        ApiResponse<List<Doctor>> response = new ApiResponse<>();
        response.setResponse(doctors);
        response.setMessage(null);
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

