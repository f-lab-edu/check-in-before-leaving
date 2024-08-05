package com.example.checkinrequestMS.HelpAPI.common.exception.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
@AllArgsConstructor
public class ExceptionResponse {
    private HttpStatus status;
    private String message;
}

