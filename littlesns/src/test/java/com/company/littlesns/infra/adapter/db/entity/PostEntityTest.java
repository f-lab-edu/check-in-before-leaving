package com.company.littlesns.infra.adapter.db.entity;

import com.company.littlesns.domain.model.post.Post;

import com.company.littlesns.domain.model.post.PostService;
import com.company.littlesns.infra.adapter.db.entity.post.PostEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostEntityTest {

    @Test
    void post() {
        // Given
        Post post = Post.post(PostService.Create.of("Hello world!", "ID", "test@test.com"));

        // When
        PostEntity result = PostEntity.post(post);

        // Then
        assertEquals(post.getContents(), result.getContents());
        assertEquals(post.getOwnerId(), result.getOwnerId());
        assertEquals(post.getOwnerEmail(), result.getOwnerEmail());
        assertEquals(post.getLikes().size(), result.getLikes().size());
    }
    //todo: null체크 테스트.
}