package com.company.checkin.help.web.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefaultHTTPResponse<T> {
    private String message;
    private T data;

    public DefaultHTTPResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public DefaultHTTPResponse(String message) {
        this.message = message;
    }
}
