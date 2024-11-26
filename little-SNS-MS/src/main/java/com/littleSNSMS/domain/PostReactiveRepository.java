package com.littleSNSMS.domain;

import com.littleSNSMS.domain.dto.PostDTO;
import reactor.core.publisher.Mono;

public interface PostReactiveRepository {
    Mono<Long> save(Post post);

    Mono<Void> likePost(Long postId, LikeMemberInfo likeMemberInfo);
}
