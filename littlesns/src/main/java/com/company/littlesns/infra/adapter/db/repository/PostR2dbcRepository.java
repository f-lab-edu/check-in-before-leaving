package com.company.littlesns.infra.adapter.db.repository;

import com.company.littlesns.infra.adapter.db.entity.post.LikeMemberInfoEntity;
import com.company.littlesns.infra.adapter.db.entity.post.PostEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostR2dbcRepository extends R2dbcRepository<PostEntity, Long> {

    @Query("INSERT INTO like_member_info (post_id, member_id, member_email) VALUES (:postId, :memberId, :memberEmail); " +
            "SELECT * FROM like_member_info WHERE post_id = :postId AND member_id = :memberId")
    Mono<LikeMemberInfoEntity> likePost(Long postId, String memberId, String memberEmail);

    @Query("SELECT * FROM like_member_info WHERE post_id = :postId")
    Flux<LikeMemberInfoEntity> findLikeMemberInfo(Long postId);
}

