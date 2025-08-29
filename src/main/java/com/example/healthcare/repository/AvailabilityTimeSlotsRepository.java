package com.example.healthcare.repository;

import com.example.healthcare.model.AvailabilityTimeSlots;
import com.example.healthcare.model.DoctorAvailability;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AvailabilityTimeSlotsRepository extends JpaRepository<AvailabilityTimeSlots,Integer> {


    @Query(value = "SELECT t.* " +
            "FROM availability_time_slots t " +
            "JOIN doctor_availability d ON d.doctor_availability_id = t.doctor_availability_id " +
            "WHERE d.doctor_id = :doctor_id AND t.is_booked = false",
            nativeQuery = true)
    List<AvailabilityTimeSlots> availableSlots(@Param("doctor_id") Integer doctor_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM availability_time_slots a WHERE a.doctor_availability_id = :availability_id",nativeQuery = true)
    void deleteByDoctorAvailabilityId(@Param("availability_id") int availability_id);
}
