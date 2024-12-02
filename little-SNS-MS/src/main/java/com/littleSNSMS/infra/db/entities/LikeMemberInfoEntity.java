package com.littleSNSMS.infra.db.entities;


import com.littleSNSMS.domain.LikeMemberInfo;
import com.littleSNSMS.infra.db.exceptions.PostEntityException;
import lombok.*;
import org.springframework.data.relational.core.mapping.Table;

@Table("like_member_info")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LikeMemberInfoEntity {
    private Long postId;
    private String memberId;
    private String memberEmail;

    public LikeMemberInfoEntity(Long postId, String memberId, String memberEmail) {
        if (postId == null) throw new PostEntityException(PostEntityException.NO_POST_ID_VALUE_FOR_LIKE_MEMBER_INFO);
        if (memberId == null)
            throw new PostEntityException(PostEntityException.NO_LIKE_MEMBER_INFO_ENTITY_MEMBER_ID_VALUE);
        if (memberEmail == null)
            throw new PostEntityException(PostEntityException.NO_LIKE_MEMBER_INFO_ENTITY_MEMBER_EMAIL_VALUE);
        this.postId = postId;
        this.memberId = memberId;
        this.memberEmail = memberEmail;
    }

    public static LikeMemberInfoEntity of(Long postId, LikeMemberInfo memberInfo) {
        return LikeMemberInfoEntity.builder()
                .postId(postId)
                .memberId(memberInfo.getMemberId())
                .memberEmail(memberInfo.getMemberEmail())
                .build();
    }
}
