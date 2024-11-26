package com.littleSNSMS.infra.db.repository;

import com.littleSNSMS.domain.LikeMemberInfo;
import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.PostReactiveRepository;
import com.littleSNSMS.infra.db.entities.LikeMemberInfoEntity;
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

    @Override
    public Mono<Void> likePost(Long postId, LikeMemberInfo likeMemberInfo) {
        postR2dbcRepository.likePost(postId, likeMemberInfo.getMemberEmail(), likeMemberInfo.getMemberId());
        return Mono.empty(); //todo: 에러 처리.
    }

    public Mono<Post> findById(Long postId) {
        return postR2dbcRepository.findById(postId).flatMap(postEntity -> {
            return postR2dbcRepository.findLikeMemberInfo(postId).collectList().map(likeMemberInfoEntities -> {
                return postEntity.toPost(likeMemberInfoEntities);
            });
        });
    }

}
