package com.example.checkinrequestMS.common.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ExceptionResponse {
    public ExceptionResponse(String message) {
        this.message = message;
    }

    private String message;
}

