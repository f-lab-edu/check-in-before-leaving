package com.littleSNSMS.domain;

import com.littleSNSMS.domain.exception.PostException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class LikeMemberInfo {
    private final String memberId;
    private final String MemberEmail;

    public LikeMemberInfo(String memberId, String MemberEmail) {
        if (memberId == null) throw new PostException(PostException.NO_MEMBER_ID_VALUE);
        if (MemberEmail == null) throw new PostException(PostException.NO_MEMBER_EMAIL_VALUE);

        this.memberId = memberId;
        this.MemberEmail = MemberEmail;
    }
}
