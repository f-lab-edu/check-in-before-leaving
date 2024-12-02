package com.littleSNSMS.domain.service;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.PostReactiveRepository;
import com.littleSNSMS.domain.dto.PostDTO;
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
    private PostReactiveRepository postRepository;

    @Test
    void post() {
        //given
        PostDTO.Create create = PostDTO.Create.of("testContent", "testUUID", "testName");
        when(postRepository.save(any(Post.class))).thenReturn(Mono.just(1L));
        
        //when
        Mono<Long> id = sut.post(create);

        //then
        assertNotNull(id);
        assertNotNull(id.block().longValue());
        verify(postRepository).save(any(Post.class));
    }
}