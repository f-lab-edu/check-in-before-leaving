package com.membercontext.memberAPI.application.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    //Log-In
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    //Sign-Up
    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    NOT_EXITING_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "업데이트 실패입니다."),
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "회원가입이 실패하였습니다.");




    private final HttpStatus httpStatus;
    private final String deatil;
}
