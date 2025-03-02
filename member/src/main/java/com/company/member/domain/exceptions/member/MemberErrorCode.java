package com.company.member.domain.exceptions.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    //Log-In
    LOGIN_REQUIRED(HttpStatus.BAD_REQUEST, "로그인이 필요합니다."),

    NO_COOKIE(HttpStatus.BAD_REQUEST, "이력을 찾을 수 없습니다. 로그인이 필요합니다."),
    NOT_LOGGED_IN(HttpStatus.BAD_REQUEST, "로그인 정보가 없습니다. 로그인이 필요합니다."),
    NO_SESSION(HttpStatus.BAD_REQUEST, "서버에 세션이 존재하지 않습니다."),
    NO_SESSION_ID(HttpStatus.BAD_REQUEST, "로그인 정보가 서버에 존재하지 않습니다. 로그인이 필요합니다."),

    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    //Sign-Up
    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    NOT_EXITING_USER(HttpStatus.BAD_REQUEST, "존재하지 않는 유저입니다."),
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "업데이트 실패입니다."),
    SIGNUP_FAILED(HttpStatus.BAD_REQUEST, "회원가입이 실패하였습니다."),

    //Location
    LOCATION_SERVICE_NOT_PERMITTED(HttpStatus.BAD_REQUEST, "위치정보 서비스가 허용되지 않았습니다."),
    NO_VALUE(HttpStatus.BAD_REQUEST, "값이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String deatil;
}
