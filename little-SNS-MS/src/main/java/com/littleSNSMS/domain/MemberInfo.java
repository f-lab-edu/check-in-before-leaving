package com.littleSNSMS.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public final class MemberInfo {
    private final String memberId;
    private final String MemberEmail;

    public MemberInfo(String memberId, String MemberEmail) {
        this.memberId = memberId;
        this.MemberEmail = MemberEmail;
    }
}
