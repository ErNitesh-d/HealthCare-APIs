package com.example.healthcare.model;
import com.example.healthcare.utils.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.aspectj.bridge.IMessage;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

/**
 * Patient Model Represent The Details Of The Patient
 * This Model Includes Following Columns:
 * patientId, name, email, phone, gender, birthDate, createdAt, updatedAt
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int patientId;

    @NotBlank(message = "Name Is Mandatory")
    @Size(min = 3,max = 50,message = "Name must be between 3 and 50 characters")
    private String  name;

    @Email(message = "Email Format Is Not Valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender Is Mandatory")
    private Gender gender;

    @Past(message = "Invalid Date Of Birth")
    private LocalDate dateOfBirth;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDate createdAt;

    @Column
    @UpdateTimestamp
    private LocalDate updatedAt;

}
