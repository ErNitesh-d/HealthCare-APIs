package com.example.healthcare.repository;

import com.example.healthcare.model.AvailabilityTimeSlots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityTimeSlotsRepository extends JpaRepository<AvailabilityTimeSlots,Integer> {
}
