package com.example.healthcare.exception;

public class DoctorAvailabilityNotFound extends RuntimeException {
    public DoctorAvailabilityNotFound(String message) {
        super(message);
    }
}
