package com.littleSNSMS.domain;

import reactor.core.publisher.Mono;

public interface PostRepository {
    Mono<Long> save(Post post);

    Mono<Void> likePost(Long postId, LikeMemberInfo likeMemberInfo);
}
