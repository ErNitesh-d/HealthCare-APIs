package com.example.healthcare.repository;

import com.example.healthcare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

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

    @Transactional
    @Modifying
    @Query(value="Update patient set date_of_birth=:date_of_birth," +
            "email=:email," +
            "gender=:gender," +
            "name=:name," +
            "updated_at=:updated_at," +
            "phone=:phone where patient_id=:patient_id", nativeQuery = true)
    int updatePatient(@Param("patient_id") int patient_id, @Param("date_of_birth")LocalDate date_of_birth,
                      @Param("email") String email,
                      @Param("gender")String gender,@Param("name")String name,
                      @Param("updated_at") LocalDate updated_at, @Param("phone") String phone

    );

    @Transactional
    @Modifying
    @Query(value="Delete from patient where patient_id=:patient_id",nativeQuery = true)
    void deletePatient(int patient_id);


    @Query(value = "Select * from patient",nativeQuery = true)
    List<Patient> findAllPatients();


}
