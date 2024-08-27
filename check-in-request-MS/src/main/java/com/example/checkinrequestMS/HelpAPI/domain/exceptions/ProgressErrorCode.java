package com.example.checkinrequestMS.HelpAPI.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgressErrorCode {
    ;

    private final String detail;
}
