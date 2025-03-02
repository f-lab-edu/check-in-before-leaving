package com.company.littlesns.web.controller.post;

import com.company.littlesns.application.post.PostApplication;
import com.company.littlesns.domain.model.post.PostService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostApplication postApplication;

    public static final String POST_SUCCESS = "포스트 되었습니다.";

    @PostMapping("/post")
    public Mono<PostResponse> post(PostService.Create req) {
        return postApplication.post(req).map(id -> {
            return PostResponse.from(POST_SUCCESS, id);
        });
    }

    @Getter
    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class PostResponse {
        private final String message;
        private final Long postId;

        public static PostResponse from(String message, Long postId) {
            return new PostResponse(message, postId);
        }
    }


}
