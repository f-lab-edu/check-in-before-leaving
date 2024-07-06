package com.membercontext.memberAPI.web.exception.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {

    ALREADY_REGISTERED_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다.");




    private final HttpStatus httpStatus;
    private final String deatil;
}
