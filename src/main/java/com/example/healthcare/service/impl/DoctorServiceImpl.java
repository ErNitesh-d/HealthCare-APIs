package com.example.healthcare.service.impl;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.impl.DoctorRepositoryImpl;
import com.example.healthcare.service.DoctorService;
import com.lambdista.util.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepositoryImpl doctorRepository;

    @Autowired
    DoctorServiceImpl(DoctorRepositoryImpl doctorRepository) {
        this.doctorRepository = doctorRepository;
    }



    @Override
    public Try<ApiResponse<Doctor>> insertDoctor(Doctor doctor) {
        ApiResponse<Doctor> apiResponse = new ApiResponse<>();

        boolean existsByPhone=doctorRepository.existsByPhone(doctor.getPhone());
        boolean existsByEmail=doctorRepository.existsByEmail(doctor.getEmail());

        if (existsByEmail || existsByPhone) {
            apiResponse.setError("User/Doctor already exists with same Email or Phone");
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new Try.Success<>(apiResponse);
        }

        int rows = doctorRepository.insertDoctor(
                doctor.getDoctorName(),
                doctor.getSpecializationId().getSpecializationId(),
                doctor.getEmail(),
                doctor.getPhone()
        );

        if (rows==0) {
            apiResponse.setError("Insert failed");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new Try.Failure<>(new RuntimeException("Insert failed"));
        }
        apiResponse.setError("Doctor Registered Successfully");
        apiResponse.setResponse(doctor);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        return new Try.Success<>(apiResponse);
    }

    @Override
    public Try<ApiResponse<Doctor>> updateDoctor(int doctor_id,Doctor doctor) {

        ApiResponse<Doctor> apiResponse =new ApiResponse<Doctor>();



        boolean existsByEmail=doctorRepository.existsByEmail(doctor.getEmail());
        boolean existsByPhone=doctorRepository.existsByPhone(doctor.getPhone());

        if (existsByEmail || existsByPhone) {
            apiResponse.setError("User/Doctor already exists with same Email or Phone");
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new Try.Success<>(apiResponse);
        }

        int row= doctorRepository.updateDoctor(
                doctor_id,
                doctor.getDoctorName(),
                doctor.getEmail(),
                doctor.getSpecializationId().getSpecializationId(),
                doctor.getPhone()
        );

        if(row==0){
            apiResponse.setError("Update failed");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new Try.Success<>(apiResponse);
        }
        apiResponse.setError("Patient Updated Successfully");
        apiResponse.setResponse(doctor);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        return new Try.Success<>(apiResponse);

    }

    @Override
    public ApiResponse<Void> deleteDoctor(int doctor_id) {
        ApiResponse<Void> apiResponse = new ApiResponse<>();

        Optional<Doctor> isFound = doctorRepository.findById(doctor_id);

        if (isFound.isEmpty()) {
            apiResponse.setError("Doctor not found or already deleted");
            apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return apiResponse;
        }

        doctorRepository.deleteById(doctor_id);

        apiResponse.setError("Patient deleted successfully with ID: " + doctor_id);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        return apiResponse;
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

}
