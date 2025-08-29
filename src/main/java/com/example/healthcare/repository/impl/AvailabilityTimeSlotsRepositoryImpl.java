package com.example.healthcare.repository.impl;

import com.example.healthcare.repository.AvailabilityTimeSlotsRepository;

public interface AvailabilityTimeSlotsRepositoryImpl extends AvailabilityTimeSlotsRepository {
    @Override
    void deleteByDoctorAvailabilityId(int doctorAvailabilityId);
}
