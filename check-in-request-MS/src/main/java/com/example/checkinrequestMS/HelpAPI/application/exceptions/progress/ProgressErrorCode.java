package com.example.checkinrequestMS.HelpAPI.application.exceptions.progress;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProgressErrorCode {
    NO_PROGRESS("진행 정보가 존재하지 않습니다.");

    private final String detail;
}
