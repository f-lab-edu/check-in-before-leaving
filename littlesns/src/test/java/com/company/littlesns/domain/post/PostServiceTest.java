package com.company.littlesns.domain.post;

import com.company.littlesns.domain.model.post.Post;
import com.company.littlesns.domain.model.post.PostRepository;
import com.company.littlesns.domain.model.post.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService sut;

    @Mock
    private PostRepository postRepository;

    @Test
    void post() {
        //given
        PostService.Create create = PostService.Create.of("testContent", "testUUID", "testName");
        when(postRepository.save(any(Post.class))).thenReturn(Mono.just(1L));

        //when
        Mono<Long> id = sut.post(create);

        //then
        assertNotNull(id);
        assertNotNull(id.block().longValue());
        verify(postRepository).save(any(Post.class));
    }

    @Test
    void create() {
        String content = "testContent";
        String memberId = "testUUID";
        String memberName = "testName";

        PostService.Create create = PostService.Create.of(content, memberId, memberName);

        assertEquals(content, create.getContent());
        assertEquals(memberId, create.getOwnerId());
        assertEquals(memberName, create.getOwnerEmail());
    }
}