package com.example.healthcare.service.impl;

import com.example.healthcare.exception.UserAlreadyExists;
import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.impl.PatientRepositoryImpl;
import com.example.healthcare.service.PatientService;
import com.lambdista.util.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepositoryImpl patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepositoryImpl patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Check Email and phone is unique or not if not unique return APIResponse with error
     * @param patient
     * @return APIResponse with Response OBJECT, statusCode and, Message if inserted
     */
    @Override
    public Try<ApiResponse<Patient>> insertPatient(Patient patient) {
        ApiResponse<Patient> apiResponse = new ApiResponse<>();

        boolean existsByEmail=patientRepository.existsByEmail(patient.getEmail());
        boolean existsByPhone=patientRepository.existsByPhone(patient.getPhone());

        if (existsByEmail || existsByPhone) {
            apiResponse.setError("User/Patient already exists with same Email or Phone");
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new Try.Success<>(apiResponse);
        }

       int rows = patientRepository.insertPatient(
                patient.getPatientId(),
                patient.getDateOfBirth(),
                patient.getCreatedAt(),
                patient.getEmail(),
                patient.getGender().name(),
                patient.getName(),
                patient.getUpdatedAt(),
                patient.getPhone()
        );
/*        if (rows.isFailure()) {
            *//*log.error("Failed to insert patient", maybePatient.failed().get());
            apiResponse.setError("Failed to insert patient");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new Try.Failure<>(new RuntimeException("Failed to insert patient"));*//*
        }*/
        if (rows==0) {
            apiResponse.setError("Insert failed");
            log.error("Insert failed");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new Try.Failure<>(new RuntimeException("Insert failed"));
        }
        apiResponse.setError("Patient Registered Successfully");
        apiResponse.setResponse(patient);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        return new Try.Success<>(apiResponse);
    }

    @Override
    public Try<ApiResponse<Patient>> updatePatient(Patient patient) {

        return new Try.Success<>(new ApiResponse<>());
    }
}


