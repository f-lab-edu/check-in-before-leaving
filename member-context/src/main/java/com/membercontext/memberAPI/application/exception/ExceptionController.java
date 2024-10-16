package com.membercontext.memberAPI.application.exception;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            MemberException.class
    })
    public ResponseEntity<ExceptionResponse<Void>> memberException(final MemberException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse<>(e.getMessage()));
    }

    public static final String MEMBER_INPUT_ERROR = "입력오류";

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ExceptionResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        AtomicInteger i = new AtomicInteger(1);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(String.valueOf(i.getAndIncrement()), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(new ExceptionResponse<>(MEMBER_INPUT_ERROR, errors));
    }
}
