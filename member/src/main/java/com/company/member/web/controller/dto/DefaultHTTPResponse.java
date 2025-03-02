package com.company.member.web.controller.dto;

import lombok.*;

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
