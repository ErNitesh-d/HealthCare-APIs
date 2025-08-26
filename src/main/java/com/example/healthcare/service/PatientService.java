package com.example.healthcare.service;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Patient;
import com.lambdista.util.Try;

import java.util.List;


public interface PatientService {

    Try<ApiResponse<Patient>> insertPatient(Patient patient);

    Try<ApiResponse<Patient>> updatePatient(int patient_id, Patient patient);

     ApiResponse<Void> deletePatient(int patient_id);

     List<Patient> findAllPatients();
}
