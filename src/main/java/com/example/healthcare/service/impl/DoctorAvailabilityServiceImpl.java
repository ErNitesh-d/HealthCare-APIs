package com.example.healthcare.service.impl;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import com.example.healthcare.exception.DoctorAvailabilityNotFound;
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
import java.util.Optional;

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
    public Try<ApiResponse<DoctorAvailability>> update(int doctorAvailabilityId, DoctorAvailability availability) {

        ApiResponse<DoctorAvailability> apiResponse = new ApiResponse<>();

        // 1. Check doctor exists
        boolean doctorExists = doctorRepository.existsById(availability.getDoctorId().getDoctorId());
        if (!doctorExists) {
            throw new DoctorNotFound("Selected Doctor Not Found");
        }

        // 2. Update availability in DB
        int updated = doctorAvailabilityRepository.updateDoctorAvailability(
                doctorAvailabilityId,
                availability.getAvailableDays().name(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getDoctorId().getDoctorId()
        );

        if (updated == 0) {
            apiResponse.setMessage("Not Updated");
            apiResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setResponse(null);
            return new Try.Failure<>(new RuntimeException("Not Updated"));
        }

        // 3. Fetch the persisted availability (with ID)
        DoctorAvailability persistedAvailability = doctorAvailabilityRepository.findById(doctorAvailabilityId)
                .orElseThrow(() -> new DoctorAvailabilityNotFound("DoctorAvailability not found after update"));

        // 4. Delete old slots
        availabilityTimeSlotsRepository.deleteByDoctorAvailabilityId(doctorAvailabilityId);

        // 5. Generate new slots using the **persisted entity**
        List<AvailabilityTimeSlots> slots = availabilityTimeSlotsService.generateTimeSlots(persistedAvailability);

        // 6. Save new slots
        availabilityTimeSlotsRepository.saveAll(slots);

        // 7. Prepare response
        apiResponse.setStatusCode(HttpStatus.OK.value());
        apiResponse.setResponse(persistedAvailability);
        apiResponse.setMessage("Availability and Time Slots Updated Successfully");

        return new Try.Success<>(apiResponse);
    }

}
