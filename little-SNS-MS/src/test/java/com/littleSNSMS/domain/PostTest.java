package com.littleSNSMS.domain;

import com.littleSNSMS.domain.dto.PostDTO;
import com.littleSNSMS.domain.exception.PostException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    @DisplayName("Post 생성")
    void post() {
        //given
        String contents = "testContent";
        String memberId = "testUUID";
        String memberName = "testName";

        PostDTO.Create create = PostDTO.Create.of(contents, memberId, memberName);

        //when
        Post post = Post.post(create);

        //then
        assertEquals(contents, post.getContents());
        assertEquals(memberId, post.getOwner().getMemberId());
        assertEquals(memberName, post.getOwner().getMemberEmail());
    }

    @Nested
    @DisplayName("Getters")
    class getters {
        @Test
        @DisplayName("Post ID 반환 실패")
        void getPostId() {
            //given
            String contents = "testContent";
            String memberId = "testUUID";
            String memberName = "testName";
            Post post = Post.post(PostDTO.Create.of("testContent", "testUUID", "testName"));

            //when
            Exception ex = assertThrows(PostException.class, () -> post.getPostId());

            //then
            assertEquals(PostException.POST_NOT_YET_EXIST, ex.getMessage());
        }

        @Test
        @DisplayName("생성일 반환 실패")
        void getCreatedAt() {
            //given
            String contents = "testContent";
            String memberId = "testUUID";
            String memberName = "testName";
            Post post = Post.post(PostDTO.Create.of("testContent", "testUUID", "testName"));

            //when
            Exception ex = assertThrows(PostException.class, () -> post.getCreatedAt());

            //then
            assertEquals(PostException.POST_NOT_YET_EXIST, ex.getMessage());
        }

        @Test
        @DisplayName("수정일 반환 실패")
        void getUpdatedAt() {
            //given
            String contents = "testContent";
            String memberId = "testUUID";
            String memberName = "testName";
            Post post = Post.post(PostDTO.Create.of("testContent", "testUUID", "testName"));

            //when
            Exception ex = assertThrows(PostException.class, () -> post.getUpdatedAt());

            //then
            assertEquals(PostException.POST_NOT_YET_EXIST, ex.getMessage());
        }

        @Test
        @DisplayName("좋아요 0개")
        void getLikes() {
            //given
            String contents = "testContent";
            String memberId = "testUUID";
            String memberName = "testName";
            Post post = Post.post(PostDTO.Create.of("testContent", "testUUID", "testName"));

            //then
            assertEquals(0, post.getLikes().size());
        }
    }
}