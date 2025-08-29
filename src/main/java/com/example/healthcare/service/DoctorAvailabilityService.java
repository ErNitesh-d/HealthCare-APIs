package com.example.healthcare.service;

import com.example.healthcare.model.ApiResponse;
import com.example.healthcare.model.DoctorAvailability;
import com.lambdista.util.Try;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface DoctorAvailabilityService {

    Try<ApiResponse<DoctorAvailability>> add(@RequestBody DoctorAvailability availability);

    Try<ApiResponse<DoctorAvailability>> update(@PathVariable int doctorAvailabilityId,@RequestBody DoctorAvailability availability);
}
