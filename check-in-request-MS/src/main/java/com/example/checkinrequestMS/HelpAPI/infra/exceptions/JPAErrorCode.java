package com.example.checkinrequestMS.HelpAPI.infra.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JPAErrorCode {
    ERROR_SAVING("JPA 저장 중 에러");

    private final String detail;
}
