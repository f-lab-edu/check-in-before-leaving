package com.littleSNSMS.infra.db.entities;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.infra.db.exceptions.PostEntityException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;

import static com.littleSNSMS.infra.db.exceptions.PostEntityException.*;

@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Getter
public class PostEntity {
    @Id
    private Long postId;

    private String contents;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Transient
    private List<LikeMemberInfoEntity> likes;

    private String ownerId;

    private String ownerEmail;
    //todo: final 불가 이유

    private PostEntity(String contents, String ownerId, String ownerEmail, boolean isPost) {
        if (isPost == true) {
            this.contents = contents;
            this.ownerId = ownerId;
            this.ownerEmail = ownerEmail;
            this.likes = List.of();
        } else {
            throw new PostEntityException(NOT_POSTING_REQUEST);
        }
    }

    @Builder(access = AccessLevel.PRIVATE)
    private PostEntity(Long postId, String contents, LocalDateTime createdAt, LocalDateTime updatedAt, String ownerId, String ownerEmail, List<LikeMemberInfoEntity> likes) {
        if (postId == null) throw new PostEntityException(NO_POST_ENTITY_ID_VALUE);
        if (contents == null) throw new PostEntityException(NO_POST_ENTITY_CONTENTS_VALUE);
        if (createdAt == null) throw new PostEntityException(NO_POST_ENTITY_CREATED_AT_VALUE);
        if (updatedAt == null) throw new PostEntityException(NO_POST_ENTITY_UPDATED_AT_VALUE);
        if (ownerId == null) throw new PostEntityException(NO_POST_ENTITY_OWNER_ID_VALUE);
        if (ownerEmail == null) throw new PostEntityException(NO_POST_ENTITY_OWNER_EMAIL_VALUE);
        if (likes == null) throw new PostEntityException(NO_POST_ENTITY_LIKES_VALUE);

        this.postId = postId;
        this.contents = contents;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ownerId = ownerId;
        this.ownerEmail = ownerEmail;
        this.likes = likes;
    }

    public static PostEntity post(Post post) {
        return new PostEntity(post.getContents(), post.getOwnerId(), post.getOwnerEmail(), true);
    }

    public Post toPost(List<LikeMemberInfoEntity> likeMemberInfoEntities) {
        return Post.of(this, likeMemberInfoEntities);
    }

    //test
    public static PostEntity postedForTest() {
        return PostEntity.builder()
                .postId(1L)
                .contents("Hello world!")
                .ownerId("ID")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .likes(List.of())
                .ownerEmail("test@test.com")
                .build();
    }
}
