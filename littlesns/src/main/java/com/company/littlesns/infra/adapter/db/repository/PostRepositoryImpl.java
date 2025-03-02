package com.company.littlesns.infra.adapter.db.repository;

import com.company.littlesns.domain.model.post.LikeMemberInfo;
import com.company.littlesns.domain.model.post.Post;
import com.company.littlesns.domain.model.post.PostRepository;
import com.company.littlesns.infra.adapter.db.entity.post.LikeMemberInfoEntity;
import com.company.littlesns.infra.adapter.db.entity.post.PostEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostR2dbcRepository postR2dbcRepository;
    private final LikeMemberInfoR2dbcRepository likeMemberInfoR2dbcRepository;

    @Override
    public Mono<Long> save(Post post) {
        return postR2dbcRepository.save(PostEntity.post(post)).map(PostEntity::getPostId);
    }

    @Override
    public Mono<Void> likePost(Long postId, LikeMemberInfo likeMemberInfo) {

        likeMemberInfoR2dbcRepository.save(LikeMemberInfoEntity.of(postId, likeMemberInfo));
        return Mono.empty(); //todo: 에러 처리.
    }

    public Mono<Post> findById(Long postId) {
        return postR2dbcRepository.findById(postId).flatMap(postEntity -> {
            return likeMemberInfoR2dbcRepository.findAllById(List.of(postId)).collectList().map(likeMemberInfoEntities -> {
                return postEntity.toPost(likeMemberInfoEntities);
            });
        });
    }

}
