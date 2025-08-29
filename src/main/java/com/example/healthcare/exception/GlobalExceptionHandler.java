package com.example.healthcare.exception;

import com.example.healthcare.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        //Handles Custom  Messages Of @validate
        String errorMessages = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();

        ApiResponse<Object> response = new ApiResponse<>(null, errorMessages, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DoctorNotFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(DoctorNotFound ex) {

        String errorMessage=Objects.requireNonNull(ex.getMessage());

        ApiResponse<Object> response = new ApiResponse<>(null, errorMessage, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DoctorAvailabilityNotFound.class)
    public ResponseEntity<ApiResponse<Object>> handleNotFound(DoctorAvailabilityNotFound ex) {

        String errorMessage=Objects.requireNonNull(ex.getMessage());

        ApiResponse<Object> response = new ApiResponse<>(null, errorMessage, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


}
