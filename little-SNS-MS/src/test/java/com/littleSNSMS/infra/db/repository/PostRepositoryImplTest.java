package com.littleSNSMS.infra.db.repository;

import com.littleSNSMS.domain.LikeMemberInfo;
import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.dto.PostDTO;
import com.littleSNSMS.infra.db.entities.LikeMemberInfoEntity;
import com.littleSNSMS.infra.db.entities.PostEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostRepositoryImplTest {

    @InjectMocks
    private PostRepositoryImpl sut;

    @Spy
    private PostR2dbcRepository postR2dbcRepository;

    @Spy
    private LikeMemberInfoR2dbcRepository likeMemberInfoR2dbcRepository;

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

    @Test
    void likePost() {
        // Given
        Long postId = 1L;
        LikeMemberInfo likeMemberInfo = new LikeMemberInfo("memberId", "memberEmail");
        LikeMemberInfoEntity likeMemberInfoEntity = LikeMemberInfoEntity.of(postId, likeMemberInfo);
        when(likeMemberInfoR2dbcRepository.save(any(LikeMemberInfoEntity.class))).thenReturn(Mono.just(likeMemberInfoEntity));

        // When
        Mono<Void> result = sut.likePost(postId, likeMemberInfo);

        // Then
        verify(likeMemberInfoR2dbcRepository).save(any(LikeMemberInfoEntity.class));
        result.block();
    }

    @Test
    void findById() {
        // Given
        Long postId = 1L;
        PostEntity postEntity = PostEntity.postedForTest();
        LikeMemberInfoEntity likeMemberInfoEntity = LikeMemberInfoEntity.of(postId, new LikeMemberInfo("memberId", "memberEmail"));
        LikeMemberInfoEntity likeMemberInfoEntity2 = LikeMemberInfoEntity.of(postId, new LikeMemberInfo("memberId2", "memberEmail2"));
        when(postR2dbcRepository.findById(postId)).thenReturn(Mono.just(postEntity));
        when(likeMemberInfoR2dbcRepository.findAllById(List.of(postId))).thenReturn(Flux.just(likeMemberInfoEntity, likeMemberInfoEntity2));

        // When
        Mono<Post> result = sut.findById(postId);

        // Then
        Post post = result.block();
        assertNotNull(post);
        assertEquals(postId, post.getPostId());
        assertEquals(2, post.getLikes().size());
        assertEquals("memberId", post.getLikes().get(0).getMemberId());
        assertEquals("memberId2", post.getLikes().get(1).getMemberId());
        //todo: 좋아요 2번 금지 기능 이후 구현.
    }

}