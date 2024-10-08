package com.example.checkinrequestMS.common.exception;

import com.example.checkinrequestMS.common.exception.response.ExceptionResponse;

import com.example.checkinrequestMS.common.exception.types.DomainException;
import com.example.checkinrequestMS.common.exception.types.InfraException;
import com.example.checkinrequestMS.common.exception.types.WebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
            DomainException.class
    })
    public ResponseEntity<ExceptionResponse> domainException(final DomainException e){
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler({
        InfraException.class
    })
    public ResponseEntity<ExceptionResponse> infraException(final InfraException e){
        log.error("InfraException: {}", e.getStackTrace());
        return ResponseEntity.internalServerError().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler({
        WebException.class
    })
    public ResponseEntity<ExceptionResponse> webException(final WebException e){
        log.error("WebException: {}", e.getStackTrace());
        return ResponseEntity.internalServerError().body(new ExceptionResponse(e.getMessage()));
    }
}