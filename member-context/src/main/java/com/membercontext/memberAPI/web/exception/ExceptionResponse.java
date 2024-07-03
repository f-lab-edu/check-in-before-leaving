package com.membercontext.memberAPI.web.exception;

import com.membercontext.memberAPI.web.exception.member.MemberErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    private MemberErrorCode memberErrorCode;
}
