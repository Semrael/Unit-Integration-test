package com.example.unit_integration_testing.exceptions;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(StudentException studentException){
        StudentErrorResponse response=new StudentErrorResponse(studentException.getHttpStatus().value(),studentException.getMessage());

        return  new ResponseEntity<>(response,studentException.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException(Exception exception){
        StudentErrorResponse response=new StudentErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);



    }
}
