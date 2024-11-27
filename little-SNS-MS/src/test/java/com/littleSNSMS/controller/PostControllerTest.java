package com.littleSNSMS.controller;

import com.littleSNSMS.application.PostApplication;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(PostController.class)
class PostControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PostApplication postApplication;

    @Test
    void post() {
        // Given
        String request = "{\"content\":\"content\",\"ownerId\":\"ownerId\",\"ownerEmail\":\"ownerEmail\"}";
        Mockito.when(postApplication.post(any(PostController.PostRequest.class))).thenReturn(Mono.just(1L));

        // When & Then
        webTestClient.post()
                .uri("/post")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(PostController.PostResponse.class)
                .value(response -> {
                    assertNotNull(response);
                    assertEquals(PostController.POST_SUCCESS, response.getMessage());
                    assertEquals(1L, response.getPostId());
                });
    }
}

