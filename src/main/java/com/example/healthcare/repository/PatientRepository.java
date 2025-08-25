package com.example.healthcare.repository;

import com.example.healthcare.model.Patient;
import com.lambdista.util.Try;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Transactional
    @Modifying
    int insertPatient(@Param("patient_id") int patient_id, @Param("date_of_birth") LocalDate date_of_birth,
                            @Param("created_at") LocalDate created_at, @Param("email") String email,
                            @Param("gender")String gender, @Param("name") String name,
                            @Param("updated_at")  LocalDate updated_at, @Param("phone") String phone
    );

    @Transactional
    @Modifying
    int updatePatient(@Param("patient_id") int patient_id, @Param("date_of_birth")LocalDate date_of_birth,
                      @Param("created_at") LocalDate created_at, @Param("email") String email,
                      @Param("gender")String gender,@Param("name")String name,
                      @Param("updated_at") LocalDate updated_at, @Param("phone") String phone

    );



}
