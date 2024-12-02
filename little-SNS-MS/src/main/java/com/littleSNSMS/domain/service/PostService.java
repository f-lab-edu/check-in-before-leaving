package com.littleSNSMS.domain.service;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.PostReactiveRepository;
import com.littleSNSMS.domain.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostReactiveRepository postRepository;

    public Mono<Long> post(PostDTO.Create dto) {
        return postRepository.save(Post.post(dto));
    }
}
