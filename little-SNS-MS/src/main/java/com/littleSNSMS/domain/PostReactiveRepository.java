package com.littleSNSMS.domain;

import reactor.core.publisher.Mono;

public interface PostReactiveRepository {
    Mono<Long> save(Post post);
}
