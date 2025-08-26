package com.example.healthcare.controller;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Patient;
import com.example.healthcare.service.PatientService;
import com.lambdista.util.Try;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/patient")

public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    /**
     * @Valid Validate fields are properly available in request
     * @param patient calls service
     * @return APIResponse, which is service layer returned
     */

        @PostMapping("/addPatient")
        public ResponseEntity<ApiResponse<Patient>> addPatient(@Valid @RequestBody Patient patient) {

            Try<ApiResponse<Patient>> result = patientService.insertPatient(patient);

        /*  if(result.isFailure()){

              return new ResponseEntity<>(result.get(), HttpStatus.INTERNAL_SERVER_ERROR);

          }*/
          return new ResponseEntity<>(result.get(),HttpStatus.OK);
        }

        @PutMapping("/updatePatientById/{patient_id}")
        public ResponseEntity<ApiResponse<Patient>> updatePatientById(@PathVariable int patient_id,@Valid @RequestBody Patient patient) {

            Try<ApiResponse<Patient>> result=patientService.updatePatient(patient_id,patient);
            return new ResponseEntity<>(result.get(),HttpStatus.OK);
        }

        @DeleteMapping("/deletePatientById/{patient_id}")
        public ResponseEntity<ApiResponse<Void>> deletePatientById(@PathVariable int patient_id) {
            ApiResponse<Void> response = patientService.deletePatient(patient_id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

    @GetMapping("/getAllPatients")
    public ResponseEntity<ApiResponse<List<Patient>>> getAllPatients() {
        List<Patient> patients = patientService.findAllPatients();

        ApiResponse<List<Patient>> response = new ApiResponse<>();
        response.setResponse(patients);
        response.setError(null);
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
