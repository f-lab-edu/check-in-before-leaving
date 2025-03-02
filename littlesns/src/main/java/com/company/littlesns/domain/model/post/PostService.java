package com.company.littlesns.domain.model.post;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Mono<Long> post(Create dto) {
        return postRepository.save(Post.post(dto));
    }

    @Getter
    @NoArgsConstructor(force = true)
    @RequiredArgsConstructor
    public static final class Create {
        private final String content;
        private final String ownerId;
        private final String ownerEmail;

        public static Create of(String content, String ownerId, String ownerEmail) {
            return new Create(content, ownerId, ownerEmail);
        }
    }
}
