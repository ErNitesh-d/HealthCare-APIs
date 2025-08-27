package com.example.healthcare.service;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.model.Patient;
import com.lambdista.util.Try;

import java.util.List;

public interface DoctorService {



        Try<ApiResponse<Doctor>> insertDoctor(Doctor doctor);

        Try<ApiResponse<Doctor>> updateDoctor(int doctor_id, Doctor doctor);

        ApiResponse<Void> deleteDoctor(int doctor_id);

        List<Doctor> findAllDoctors();
}
