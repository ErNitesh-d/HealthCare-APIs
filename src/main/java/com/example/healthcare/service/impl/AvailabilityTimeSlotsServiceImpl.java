package com.example.healthcare.service.impl;

import com.example.healthcare.model.AvailabilityTimeSlots;
import com.example.healthcare.model.DoctorAvailability;
import com.example.healthcare.service.AvailabilityTimeSlotsService;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityTimeSlotsServiceImpl implements AvailabilityTimeSlotsService {

    @Override
    public List<AvailabilityTimeSlots> generateTimeSlots(DoctorAvailability availability) {

        List<AvailabilityTimeSlots> timeSlots = new ArrayList<>();

        LocalTime startTime = availability.getStartTime();
        LocalTime endTime   = availability.getEndTime();

        while (startTime.isBefore(endTime)) {

            LocalTime  slotEnd = startTime.plusMinutes(30);

            if(slotEnd.isAfter(endTime)) {
                slotEnd=endTime;
            }

            AvailabilityTimeSlots slot = new AvailabilityTimeSlots();
            slot.setDoctorAvailabilityId(availability);
            slot.setStartingTime(startTime);
            slot.setEndingTime(slotEnd);
            slot.setBooked(false);

            timeSlots.add(slot);

            startTime = slotEnd;
        }
        return timeSlots;
    }

 }

