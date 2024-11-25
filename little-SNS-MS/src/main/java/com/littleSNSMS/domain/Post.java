package com.littleSNSMS.domain;

import com.littleSNSMS.domain.dto.PostDTO;
import com.littleSNSMS.domain.exception.PostException;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public final class Post {

    @Getter
    private final String contents;

    @Getter
    private final MemberInfo owner;

    @Getter
    private List<MemberInfo> likes = List.of();

    private final Long postId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private Post(String contents, MemberInfo owner, List<MemberInfo> likes, boolean isCreating) {
        if (isCreating) {
            this.contents = contents;
            this.owner = owner;
            this.likes = likes;
            this.createdAt = null;
            this.updatedAt = null;
            this.postId = null;
        } else {
            throw new PostException(PostException.NOT_POSTING_REQUEST);
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Post(String contents, MemberInfo owner, Long postId, LocalDateTime createdAt, LocalDateTime updatedAt, List<MemberInfo> likes) {
        if (contents == null) throw new PostException(PostException.NO_CONTENT_VALUE);
        if (owner == null) throw new PostException(PostException.NO_OWNER_VALUE);
        if (postId == null) throw new PostException(PostException.NO_POST_ID_VALUE);
        if (createdAt == null) throw new PostException(PostException.NO_CREATED_AT_VALUE);
        if (updatedAt == null) throw new PostException(PostException.NO_UPDATED_AT_VALUE);
        if (likes == null) throw new PostException(PostException.NO_LIKES_VALUE);

        this.contents = contents;
        this.owner = owner;
        this.postId = postId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.likes = likes;
    }

    public static Post post(PostDTO.Create dto) {
        return new Post(dto.getContent(), new MemberInfo(dto.getOwner().getMemberId(), dto.getOwner().getMemberEmail()), List.of(), true);
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
