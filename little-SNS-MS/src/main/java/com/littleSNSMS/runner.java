package com.littleSNSMS;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.service.PostService;
import com.littleSNSMS.infra.db.entities.LikeMemberInfoEntity;
import com.littleSNSMS.infra.db.entities.PostEntity;
import com.littleSNSMS.infra.db.repository.PostR2dbcRepository;
import com.littleSNSMS.infra.db.repository.PostRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
// 임시적으로 테스트를 위해 있는 클래스 입니다. 추후 삭제 예정입니다.
public class runner implements CommandLineRunner {

    private final PostR2dbcRepository postRepository;
    //private final EntRepo entRepo;

    private final PostRepositoryImpl reactiveRepo;

    @Override
    //   @Transactional
    public void run(String... args) throws Exception {
        PostService.Create create = PostService.Create.of("Hello world!", "ID", "name");
        Post post = Post.post(create);
        PostEntity postEntity = PostEntity.post(post);
        Long id = postRepository.save(postEntity).block().getPostId();
        System.out.println("saved");
        System.out.println("id = " + id);

        LikeMemberInfoEntity like = postRepository.likePost(id, "memberId", "memberEmail2").block();
        System.out.println("liked");
        System.out.println(like.getMemberEmail());

        LikeMemberInfoEntity like2 = postRepository.likePost(id, "memberId2", "memberEmail3").block();
        System.out.println("liked2");
        System.out.println(like2.getMemberEmail());

        List<LikeMemberInfoEntity> list = postRepository.findLikeMemberInfo(id).collectList().block();

        System.out.println("find");
        for (LikeMemberInfoEntity likeMemberInfoEntity : list) {
            System.out.println(likeMemberInfoEntity.getMemberEmail());
        }

        Post post2 = reactiveRepo.findById(id).block();
        System.out.println("find2");
        System.out.println(post2.getPostId());
        System.out.println(post2.getContents());
        System.out.println(post2.getOwnerId());
        System.out.println(post2.getOwnerEmail());
        System.out.println(post2.getCreatedAt());
        System.out.println(post2.getUpdatedAt());
        for (int i = 0; i < post2.getLikes().size(); i++) {
            System.out.println(post2.getLikes().get(i).getMemberEmail());
        }

    }
}
