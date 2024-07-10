package com.membercontext.memberAPI.application.exception;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            MemberException.class
    })
    public ResponseEntity<ExceptionResponse> memberException(final MemberException e){
        return ResponseEntity.badRequest().body(new ExceptionResponse(e.getMessage(), e.getMemberErrorCode()));
    }
}
