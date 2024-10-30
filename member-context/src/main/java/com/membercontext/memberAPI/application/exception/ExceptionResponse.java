package com.membercontext.memberAPI.application.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExceptionResponse<T> {
    private final String message;
    private T error;

    public ExceptionResponse(String message, T error) {
        this.message = message;
        this.error = error;
    }

    public ExceptionResponse(String message) {
        this.message = message;
    }
}
