package com.example.healthcare.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base/Common/Standard Response For All API request
 * @param <T> The Type of Response Data
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse<T> {

    private T response;

    private String message;

    private int statusCode;

}
