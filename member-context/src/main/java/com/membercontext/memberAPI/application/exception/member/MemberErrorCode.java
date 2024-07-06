package com.membercontext.memberAPI.application.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    //Fixme 구조: Exception은 비즈니스 로직에 대해 예외처리를 하는 경우가 많아서 도메인이 적합한것 같습니다.

    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    NOT_EXITING_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),;




    private final HttpStatus httpStatus;
    private final String deatil;
}
