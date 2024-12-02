package com.littleSNSMS.application;

import com.littleSNSMS.controller.PostController;
import com.littleSNSMS.domain.dto.PostDTO;
import com.littleSNSMS.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostApplication {

    private final PostService postService;

    public Mono<Long> post(PostController.PostRequest req) {
        return postService.post(PostDTO.Create.of(req.getContent(), req.getOwnerId(), req.getOwnerEmail()));
    }
}
