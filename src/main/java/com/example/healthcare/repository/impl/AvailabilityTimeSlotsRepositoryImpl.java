package com.example.healthcare.repository.impl;

import com.example.healthcare.repository.AvailabilityTimeSlotsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AvailabilityTimeSlotsRepositoryImpl extends AvailabilityTimeSlotsRepository {

    @Override
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM availability_time_slots a WHERE a.doctor_availability_id = :availability_id",nativeQuery = true)
    void deleteByDoctorAvailabilityId(@Param("availability_id") int availability_id);
}

