package com.company.checkin.common.exception.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ExceptionResponse {
    public ExceptionResponse(String message) {
        this.message = message;
    }

    private String message;

}

