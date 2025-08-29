package com.example.healthcare.service;

import com.example.healthcare.model.AvailabilityTimeSlots;
import com.example.healthcare.model.DoctorAvailability;

import java.util.List;

public interface AvailabilityTimeSlotsService {

    List<AvailabilityTimeSlots> generateTimeSlots(DoctorAvailability availability);

}
