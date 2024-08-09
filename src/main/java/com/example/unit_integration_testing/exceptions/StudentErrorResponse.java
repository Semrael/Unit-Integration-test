package com.example.unit_integration_testing.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentErrorResponse {
    private int status;
    private String message;
}
