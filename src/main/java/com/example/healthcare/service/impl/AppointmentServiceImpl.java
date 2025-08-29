package com.example.healthcare.service.impl;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.Appointment;
import com.example.healthcare.model.AvailabilityTimeSlots;
import com.example.healthcare.model.DoctorAvailability;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.AvailabilityTimeSlotsRepository;
import com.example.healthcare.repository.DoctorAvailabilityRepository;
import com.example.healthcare.repository.impl.AvailabilityTimeSlotsRepositoryImpl;
import com.example.healthcare.service.AppointmentService;
import com.lambdista.util.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityTimeSlotsRepository  availabilityTimeSlotsRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, AvailabilityTimeSlotsRepository availabilityTimeSlotsRepository) {
        this.appointmentRepository = appointmentRepository;

        this.availabilityTimeSlotsRepository = availabilityTimeSlotsRepository;
    }

    @Override
    public List<AvailabilityTimeSlots> availableSlots(int doctor_id) {
        return availabilityTimeSlotsRepository.availableSlots(doctor_id);
    }

    @Override
    public Try<ApiResponse<Appointment>> bookAppointment(Appointment appointment){

        ApiResponse<Appointment> apiResponse =new ApiResponse<>();

        Appointment booked= appointmentRepository.save(appointment);

        if(booked == null){
            apiResponse.setResponse(null);
            apiResponse.setMessage("Something want wrong! Appointment not saved");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

            return new Try.Success<>(apiResponse);
        }

        apiResponse.setResponse(booked);
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setMessage("Appointment booked successfully");

        return new  Try.Success<>(apiResponse);

    }

}
