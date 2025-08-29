package com.example.healthcare.repository;

import com.example.healthcare.controller.DoctorAvailabilityController;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.model.DoctorAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalTime;

public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailability, Integer> {

    // fetch all availabilities of a doctor by doctor_id
    List<DoctorAvailability> findByDoctorId(Doctor doctorId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor_availability set " +
            "available_days=:available_days," +
            "start_time=:start_time,"+
            "end_time=:end_time,"+
            "doctor_id=:doctor_id where doctor_availability_id=:doctor_availability_id",nativeQuery = true)
    int updateDoctorAvailability(@Param("doctor_availability_id") int doctor_availability_id,
                                 @Param("available_days") String available_days,
                                 @Param("start_time") LocalTime start_time,
                                 @Param("end_time") LocalTime end_time,
                                 @Param("doctor_id") int doctor_id);
}
