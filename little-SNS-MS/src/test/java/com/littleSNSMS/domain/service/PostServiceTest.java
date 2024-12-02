package com.littleSNSMS.domain.service;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    
    private PostRepository postRepository = spy(PostRepository.class);

    @Test
    void post() {
        //given
        Post post = mock(Post.class);

        //when
        Long id = postRepository.save(post);

        //then
        assertNotNull(id);
        verify(postRepository).save(post);
    }
}