package com.company.littlesns.application.post;

import com.company.littlesns.domain.model.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostApplication {

    private final PostService postService;

    @Transactional
    public Mono<Long> post(PostService.Create dto) {
        return postService.post(PostService.Create.of(dto.getContent(), dto.getOwnerId(), dto.getOwnerEmail()));
    }


}
