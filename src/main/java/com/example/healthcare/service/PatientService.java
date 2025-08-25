package com.example.healthcare.service;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Patient;
import com.lambdista.util.Try;

public interface PatientService {

    Try<ApiResponse<Patient>> insertPatient(Patient patient);

    Try<ApiResponse<Patient>> updatePatient(Patient patient);
}
