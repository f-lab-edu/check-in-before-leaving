package com.company.littlesns.application.post;

import com.company.littlesns.domain.model.post.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostApplicationTest {

    @Mock
    private PostService postService;

    @InjectMocks
    private PostApplication postApplication;

    @Test
    void post() {
        // Given
        PostService.Create request = mock(PostService.Create.class);
        when(postService.post(any(PostService.Create.class))).thenReturn(Mono.just(1L));

        // When
        Mono<Long> result = postApplication.post(request);

        // Then
        assertEquals(1L, result.block());
    }

}