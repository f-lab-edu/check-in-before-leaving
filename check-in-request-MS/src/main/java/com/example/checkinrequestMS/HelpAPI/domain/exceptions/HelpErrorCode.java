package com.example.checkinrequestMS.HelpAPI.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelpErrorCode {

    CHECK_IN_REGISTER_FAILED("체크인 요청 등록에 실패했습니다.");

    private final String detail;
}
