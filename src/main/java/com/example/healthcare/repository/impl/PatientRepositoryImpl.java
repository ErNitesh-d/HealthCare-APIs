package com.example.healthcare.repository.impl;

import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.PatientRepository;
import com.lambdista.util.Try;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Repository
public interface PatientRepositoryImpl extends PatientRepository   {

    @Override
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Patient (patient_id, date_of_birth, created_at, " +
            "email, gender, name, phone, updated_at) values " +
            "(:patient_id,:date_of_birth, :created_at, :email, :gender, :name, " +
            ":phone, :updated_at )" ,nativeQuery = true)
    int insertPatient(@Param("patient_id") int patient_id, @Param("date_of_birth") LocalDate date_of_birth,
                            @Param("created_at") LocalDate created_at, @Param("email") String email,
                            @Param("gender")String gender, @Param("name") String name,
                            @Param("updated_at")  LocalDate updated_at, @Param("phone") String phone
    );

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Override
    @Transactional
    @Modifying
    @Query
    int updatePatient(@Param("patient_id") int patient_id, @Param("date_of_birth")LocalDate date_of_birth,
                      @Param("created_at") LocalDate created_at, @Param("email") String email,
                      @Param("gender")String gender,@Param("name")String name,
                      @Param("updated_at") LocalDate updated_at, @Param("phone") String phone);


}

