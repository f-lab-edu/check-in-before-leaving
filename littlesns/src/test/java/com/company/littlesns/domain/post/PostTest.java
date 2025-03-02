package com.company.littlesns.domain.post;

import com.company.littlesns.domain.exception.PostException;
import com.company.littlesns.domain.model.post.LikeMemberInfo;
import com.company.littlesns.domain.model.post.Post;
import com.company.littlesns.domain.model.post.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Nested
    @DisplayName("Post 생성")
    class post {
        @Test
        @DisplayName("Post 생성 성공")
        void postSuccessful() {
            //given
            String contents = "testContent";
            String memberId = "testUUID";
            String memberName = "testName";

            PostService.Create create = PostService.Create.of(contents, memberId, memberName);

            //when
            Post post = Post.post(create);

            //then
            assertEquals(contents, post.getContents());
            assertEquals(memberId, post.getOwnerId());
            assertEquals(memberName, post.getOwnerEmail());
        }

        @Test
        @DisplayName("Post 생성 아닌 경우")
        void postNotTheCase() {
            try {
                //given
                Class<Post> clazz = Post.class;
                Constructor<Post> constructorForPosting = clazz.getDeclaredConstructor(String.class, LikeMemberInfo.class, List.class, boolean.class);
                constructorForPosting.setAccessible(true);

                //when
                Post post = constructorForPosting.newInstance("testContent", new LikeMemberInfo("testUUID", "testName"), List.of(), true);
                Exception ex = assertThrows(InvocationTargetException.class, () -> {
                    constructorForPosting.newInstance("testContent", new LikeMemberInfo("testUUID", "testName"), List.of(), false);
                });

                //then;
                assertTrue(ex.getCause() instanceof PostException);
                Exception result = (PostException) ex.getCause();
                assertEquals(PostException.NOT_POSTING_REQUEST, result.getMessage());

            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    @Nested
    @DisplayName("Getters")
    class getters {
        @Test
        @DisplayName("Post ID 반환 실패")
        void getPostId() {
            //given
            Post post = Post.post(PostService.Create.of("testContent", "testUUID", "testName"));

            //when
            Exception ex = assertThrows(PostException.class, post::getPostId);

            //then
            assertEquals(PostException.POST_NOT_YET_EXIST, ex.getMessage());
        }

        @Test
        @DisplayName("생성일 반환 실패")
        void getCreatedAt() {
            //given
            Post post = Post.post(PostService.Create.of("testContent", "testUUID", "testName"));

            //when
            Exception ex = assertThrows(PostException.class, post::getCreatedAt);

            //then
            assertEquals(PostException.POST_NOT_YET_EXIST, ex.getMessage());
        }

        @Test
        @DisplayName("수정일 반환 실패")
        void getUpdatedAt() {
            //given
            Post post = Post.post(PostService.Create.of("testContent", "testUUID", "testName"));

            //when
            Exception ex = assertThrows(PostException.class, post::getUpdatedAt);

            //then
            assertEquals(PostException.POST_NOT_YET_EXIST, ex.getMessage());
        }

        @Test
        @DisplayName("좋아요 0개")
        void getLikes() {
            //given
            Post post = Post.post(PostService.Create.of("testContent", "testUUID", "testName"));

            //then
            assertEquals(0, post.getLikes().size());
        }
    }
}