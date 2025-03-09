package com.company.checkin.help.domain.exceptions.help;

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
    NO_ETC_INFO("기타 정보가 존재하지 않습니다."),

    NO_HELPER_ID("도움을 주기로한 사람의 정보가 없습니다."),
    NO_PHOTO("사진이 없습니다."),
    NOT_COMPLETED("완료되지 않은 요청입니다."),


    HELP_RESPONSE_CREATION_ERROR("도움 요청 응답 생성 중 장애가 발생하였습니다.");

    private final String detail;
}
