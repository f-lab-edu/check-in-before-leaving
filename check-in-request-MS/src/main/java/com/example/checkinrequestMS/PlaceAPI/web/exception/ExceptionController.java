package com.example.checkinrequestMS.PlaceAPI.web.exception;

import com.example.checkinrequestMS.PlaceAPI.common.exception.DomainException;
import com.example.checkinrequestMS.PlaceAPI.common.exception.WebException;
import com.example.checkinrequestMS.PlaceAPI.common.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            DomainException.class
    })
    public ResponseEntity<ExceptionResponse> domainException(final DomainException e){
        return ResponseEntity.badRequest().body(new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    @ExceptionHandler({
        WebException.class
    })
    public ResponseEntity<ExceptionResponse> webException(final WebException e){
        return ResponseEntity.badRequest().body(new ExceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}