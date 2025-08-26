package com.example.healthcare.service.impl;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.impl.PatientRepositoryImpl;
import com.example.healthcare.service.PatientService;
import com.lambdista.util.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepositoryImpl patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepositoryImpl patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Check Email and phone is unique if not unique return APIResponse with error
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
    public Try<ApiResponse<Patient>> updatePatient(int patient_id,Patient patient) {

        ApiResponse<Patient> apiResponse =new ApiResponse<Patient>();


        boolean existsByPhone=patientRepository.existsByPhone(patient.getPhone());
        boolean existsByEmail=patientRepository.existsByEmail(patient.getEmail());

        if (existsByEmail || existsByPhone) {
            apiResponse.setError("User/Patient already exists with same Email or Phone");
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new Try.Success<>(apiResponse);
        }

        int row= patientRepository.updatePatient(
                patient_id,
                patient.getDateOfBirth(),
                patient.getEmail(),
                patient.getGender().name(),
                patient.getName(),
                patient.getUpdatedAt(),
                patient.getPhone()
        );

        if(row==0){
            apiResponse.setError("Update failed");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new Try.Success<>(apiResponse);
        }
        apiResponse.setError("Patient Updated Successfully");
        apiResponse.setResponse(patient);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        return new Try.Success<>(apiResponse);

    }
    @Override
    public ApiResponse<Void> deletePatient(int patient_id) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();

        Optional<Patient> isFound = patientRepository.findById((long) patient_id);

        if (isFound.isEmpty()) {
            apiResponse.setError("Patient not found or already deleted");
            apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return apiResponse;
        }

        patientRepository.deleteById((long) patient_id);

        apiResponse.setError("Patient deleted successfully with ID: " + patient_id);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }


}


