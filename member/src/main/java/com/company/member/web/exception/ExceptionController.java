package com.company.member.web.exception;

import com.company.member.common.exception.types.DomainException;
import com.company.member.common.exception.types.TechnicalException;
import com.company.member.domain.exceptions.member.MemberException;
import com.company.member.web.exception.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({
            DomainException.class
    })
    public ResponseEntity<ExceptionResponse<Void>> domainExceptions(final MemberException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponse<>(e.getMessage()));
    }

    public static final String SYSTEM_ERROR = "시스템 오류입니다. 신속히 조치하도록 하겠습니다.";

    //todo: Exception Response 포멧 변경 고려. (error 필드가 필요 없는 경우 안 나올 수 도 있게 고민.)
    @ExceptionHandler({
            TechnicalException.class
    })
    public ResponseEntity<ExceptionResponse<Void>> technicalExceptions() {
        return ResponseEntity.internalServerError().body(new ExceptionResponse<>(SYSTEM_ERROR));
    }

    public static final String MEMBER_INPUT_ERROR = "입력오류";

    @ExceptionHandler({
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ExceptionResponse<Map<String, String>>> interfaceValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(String.valueOf(error.getField()), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(new ExceptionResponse<>(MEMBER_INPUT_ERROR, errors));
    }
}
