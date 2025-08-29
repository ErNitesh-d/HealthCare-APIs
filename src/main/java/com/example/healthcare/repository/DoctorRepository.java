package com.example.healthcare.repository;

import com.example.healthcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {


    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO doctor (doctor_name, specialization_id, " +
            "email,phone) values " +
            "(:doctor_name, :specialization_id, :email,"+
            ":phone)" ,nativeQuery = true)
    int insertDoctor( @Param("doctor_name") String doctor_name,
                      @Param("specialization_id") int specialization_id,
                       @Param("email") String email,
                      @Param("phone") String phone

    );

    @Transactional
    @Modifying
    @Query(value="Update doctor set doctor_name=:doctor_name," +
            "email=:email," +
            "specialization_id=:specialization_id," +
            "phone=:phone where doctor.doctor_id=:doctor_id", nativeQuery = true)

    int updateDoctor(@Param("doctor_id") int doctor_id,
                      @Param("doctor_name")String doctor_name,
                      @Param("email") String email,
                      @Param("specialization_id")int specialization_id,
                      @Param("phone") String phone

    );

    @Transactional
    @Modifying
    @Query(value="Delete from doctor where doctor_id=:doctor_id",nativeQuery = true)
    void deleteDoctor(int doctor_id);


    @Query(value = "Select * from Doctor",nativeQuery = true)
    List<Doctor> findAllDoctors();
}
