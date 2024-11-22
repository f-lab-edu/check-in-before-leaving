package com.membercontext.memberAPI.domain.exceptions.member;

import com.membercontext.memberAPI.common.exception.types.DomainException;
import lombok.Getter;

@Getter
public class MemberException extends DomainException {
    private MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getDeatil());
        this.memberErrorCode = memberErrorCode;
    }
}

