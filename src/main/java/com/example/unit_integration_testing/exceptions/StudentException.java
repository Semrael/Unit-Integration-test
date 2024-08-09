package com.example.unit_integration_testing.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class StudentException extends RuntimeException{
    private HttpStatus httpStatus;
    public StudentException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }
}
