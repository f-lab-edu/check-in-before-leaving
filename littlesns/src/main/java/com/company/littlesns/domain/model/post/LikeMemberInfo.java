package com.company.littlesns.domain.model.post;

import com.company.littlesns.domain.exception.PostException;
import com.company.littlesns.infra.adapter.db.entity.post.LikeMemberInfoEntity;
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

    public static LikeMemberInfo from(LikeMemberInfoEntity likeMemberInfoEntity) {
        return new LikeMemberInfo(likeMemberInfoEntity.getMemberId(), likeMemberInfoEntity.getMemberEmail());

    }
}
