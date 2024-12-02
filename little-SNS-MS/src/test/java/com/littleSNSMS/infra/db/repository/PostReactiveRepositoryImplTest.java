package com.littleSNSMS.infra.db.repository;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.dto.PostDTO;
import com.littleSNSMS.infra.db.entities.PostEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostReactiveRepositoryImplTest {

    @InjectMocks
    private PostReactiveRepositoryImpl sut;

    @Spy
    private PostR2dbcRepository postR2dbcRepository;

    @Test
    void save() {
        //given
        PostDTO.Create create = PostDTO.Create.of("Hello world!", "ID", "name");
        Post post = Post.post(create);
        when(postR2dbcRepository.save(any(PostEntity.class))).thenReturn(Mono.just(PostEntity.postedForTest()));

        //when
        Mono<Long> id = sut.save(post);

        //then
        assertNotNull(id);
        assertNotNull(id.block().longValue());
        //todo: stub(?);
    }
}