package com.company.member.domain.exceptions.member;

import com.company.member.common.exception.types.DomainException;
import lombok.Getter;

@Getter
public class MemberException extends DomainException {
    private MemberErrorCode memberErrorCode;

    public MemberException(MemberErrorCode memberErrorCode) {
        super(memberErrorCode.getDeatil());
        this.memberErrorCode = memberErrorCode;
    }
}

