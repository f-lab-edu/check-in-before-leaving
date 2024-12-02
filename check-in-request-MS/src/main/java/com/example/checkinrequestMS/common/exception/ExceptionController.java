package com.example.checkinrequestMS.common.exception;

import com.example.checkinrequestMS.common.exception.response.ExceptionResponse;

import com.example.checkinrequestMS.common.exception.response.ValidationResponse;
import com.example.checkinrequestMS.common.exception.types.DomainException;
import com.example.checkinrequestMS.common.exception.types.InfraException;
import com.example.checkinrequestMS.common.exception.types.WebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({
            DomainException.class
    })
    public ResponseEntity<ExceptionResponse> domainException(final DomainException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler({
            InfraException.class,
            NullPointerException.class
    })
    public ResponseEntity<ExceptionResponse> infraException(final InfraException e) {
        log.error("InfraException: {}", (Object) e.getStackTrace());
        return ResponseEntity.internalServerError().body(new ExceptionResponse(e.getMessage()));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ValidationResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(new ValidationResponse(errors));
    }
}