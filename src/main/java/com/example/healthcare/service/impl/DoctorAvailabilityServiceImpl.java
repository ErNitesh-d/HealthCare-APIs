package com.example.healthcare.service.impl;

import com.example.healthcare.exception.DoctorNotFound;
import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.AvailabilityTimeSlots;
import com.example.healthcare.model.DoctorAvailability;
import com.example.healthcare.repository.AvailabilityTimeSlotsRepository;
import com.example.healthcare.repository.DoctorAvailabilityRepository;
import com.example.healthcare.repository.DoctorRepository;
import com.example.healthcare.service.AvailabilityTimeSlotsService;
import com.example.healthcare.service.DoctorAvailabilityService;
import com.lambdista.util.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;
    private final AvailabilityTimeSlotsRepository availabilityTimeSlotsRepository;

    private final AvailabilityTimeSlotsService availabilityTimeSlotsService;
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorAvailabilityServiceImpl(DoctorAvailabilityRepository doctorAvailabilityRepository, AvailabilityTimeSlotsRepository availabilityTimeSlotsRepository, AvailabilityTimeSlotsService availabilityTimeSlotsService, DoctorRepository doctorRepository) {
        this.doctorAvailabilityRepository = doctorAvailabilityRepository;
        this.availabilityTimeSlotsRepository = availabilityTimeSlotsRepository;
        this.availabilityTimeSlotsService = availabilityTimeSlotsService;
        this.doctorRepository = doctorRepository;
    }


    @Override
    public Try<ApiResponse<DoctorAvailability>> add(DoctorAvailability availability) {

        ApiResponse<DoctorAvailability> apiResponse = new ApiResponse<>();

        DoctorAvailability doctorAvailability = doctorAvailabilityRepository.save(availability);

        // 2. Generate 30-min slots
        List<AvailabilityTimeSlots> slots = availabilityTimeSlotsService.generateTimeSlots(doctorAvailability);

        // 3. Save all slots
        availabilityTimeSlotsRepository.saveAll(slots);

        apiResponse.setMessage("Inserted Successfully");
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setResponse(doctorAvailability);

        if (doctorAvailability == null) {
            apiResponse.setMessage("Not Inserted");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setResponse(null);
            return new Try.Failure<>(new RuntimeException(""));
        }
        return new Try.Success<>(apiResponse);
    }

    @Override
    public Try<ApiResponse<DoctorAvailability>> update(int doctorAvailabilityId,DoctorAvailability availability) {


        ApiResponse<DoctorAvailability> apiResponse=new ApiResponse<>();

        boolean doctor=doctorRepository.existsById(availability.getDoctorId().getDoctorId());
        if(!doctor){
            throw  new DoctorNotFound("Selected Doctor Not Found");
        }

        int updated=doctorAvailabilityRepository.updateDoctorAvailability(
                doctorAvailabilityId,
                availability.getAvailableDays().name(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getDoctorId().getDoctorId()
        );

        if(updated==0) {
            apiResponse.setMessage("Not Updated");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setResponse(null);
            return new Try.Failure<>(new RuntimeException("Not Updated"));

        }
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setResponse(availability);
        apiResponse.setMessage("Availability Updated Successfully");

        return new Try.Success<>(apiResponse);

    }
}
