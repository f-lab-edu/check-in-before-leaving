package com.littleSNSMS.domain;

import com.littleSNSMS.domain.dto.PostDTO;
import com.littleSNSMS.domain.exception.PostException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class Post {

    @Getter
    private final String contents;

    @Getter
    private final MemberInfo owner;

    @Getter
    private final List<MemberInfo> likes = List.of();

    private final Long postId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public static Post post(PostDTO.Create dto) {
        return Post.builder()
                .contents(dto.getContent())
                .owner(new MemberInfo(dto.getOwner().getMemberId(), dto.getOwner().getMemberName()))
                .build();
    }

    public Long getPostId() {
        if (postId == null) throw new PostException(PostException.POST_NOT_YET_EXIST);
        return postId;
    }

    public LocalDateTime getCreatedAt() {
        if (createdAt == null) throw new PostException(PostException.POST_NOT_YET_EXIST);
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        if (updatedAt == null) throw new PostException(PostException.POST_NOT_YET_EXIST);
        return updatedAt;
    }

}
