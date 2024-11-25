package com.littleSNSMS.infra.db.entities;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.dto.PostDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PostEntityTest {

    @Test
    void post() {
        // Given
        Post post = Post.post(PostDTO.Create.of("Hello world!", "ID", "test@test.com"));

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