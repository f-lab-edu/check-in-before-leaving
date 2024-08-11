package com.example.checkinrequestMS.HelpAPI.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelpErrorCode {

    //Help
    NO_HELP_INFO("도움 정보가 존재하지 않습니다."),

    //CheckIn
    CHECK_IN_REGISTER_FAILED("체크인 요청 등록에 실패했습니다."),
    NO_CHECK_IN_INFO("체크인 정보가 존재하지 않습니다.");

    //LineUp


    //Etc.

    private final String detail;
}
