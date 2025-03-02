package com.company.checkin.common.exception.response;

import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class ValidationResponse {

    private Map<String, String> message;

    public ValidationResponse(Map<String, String> errors) {
        this.message = errors;
    }

}
