package com.membercontext.memberAPI.web.exception.member;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
    private MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getDeatil());
        this.memberErrorCode = memberErrorCode;
    }
}
