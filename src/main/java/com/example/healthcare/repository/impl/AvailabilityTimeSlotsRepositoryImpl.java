package com.example.healthcare.repository.impl;

import com.example.healthcare.model.AvailabilityTimeSlots;
import com.example.healthcare.repository.AvailabilityTimeSlotsRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvailabilityTimeSlotsRepositoryImpl extends AvailabilityTimeSlotsRepository {

    @Query(value = "SELECT t.starting_time,t.ending_time,d.available_days " +
            "FROM availability_time_slots t " +
            "JOIN doctor_availability d ON d.doctor_availability_id = t.doctor_availability_id " +
            "WHERE d.doctor_id = :doctor_id AND t.is_booked = false",
            nativeQuery = true)
    List<AvailabilityTimeSlots> availableSlots(@Param("doctor_id") Integer doctor_id);


    @Override
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM availability_time_slots a WHERE a.doctor_availability_id = :availability_id",nativeQuery = true)
    void deleteByDoctorAvailabilityId(@Param("availability_id") int availability_id);
}

