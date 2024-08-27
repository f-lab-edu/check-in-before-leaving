package com.example.checkinrequestMS.HelpAPI.domain.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HelpErrorCode {

    //Help
    NO_HELP_INFO("도움 정보가 존재하지 않습니다."),

    //CheckIn
    NO_CHECK_IN_INFO("체크인 정보가 존재하지 않습니다."),

    //LineUp
    NO_LINE_UP_INFO("라인업 정보가 존재하지 않습니다."),

    //Etc.
    No_ETC_INFO("기타 정보가 존재하지 않습니다.");

    private final String detail;
}
