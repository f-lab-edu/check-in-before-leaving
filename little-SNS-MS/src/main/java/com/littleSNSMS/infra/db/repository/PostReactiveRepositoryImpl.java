package com.littleSNSMS.infra.db.repository;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.PostReactiveRepository;
import com.littleSNSMS.infra.db.entities.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PostReactiveRepositoryImpl implements PostReactiveRepository {

    private final PostR2dbcRepository postR2dbcRepository;

    @Override
    public Mono<Long> save(Post post) {
        return postR2dbcRepository.save(PostEntity.post(post)).map(PostEntity::getPostId);
    }

}
