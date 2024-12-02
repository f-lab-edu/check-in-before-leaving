package com.littleSNSMS.infra.db.repository.integ;

import com.littleSNSMS.domain.Post;
import com.littleSNSMS.domain.service.PostService;
import com.littleSNSMS.infra.db.entities.PostEntity;
import com.littleSNSMS.infra.db.repository.PostR2dbcRepository;
import com.littleSNSMS.infra.db.repository.PostRepositoryImpl;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class PostRepositoryImplIntegratedTest {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String DATABASE_NAME = "mysql_testcontainer";

    @ClassRule
    @Container
    public static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withUsername(USERNAME)
            .withPassword(PASSWORD)
            .withDatabaseName(DATABASE_NAME);

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry dynamicPropertyRegistry) {

        dynamicPropertyRegistry.add("spring.datasource.url", () -> mySQLContainer.getJdbcUrl());
        dynamicPropertyRegistry.add("spring.datasource.username", () -> USERNAME);
        dynamicPropertyRegistry.add("spring.datasource.password", () -> PASSWORD);
        dynamicPropertyRegistry.add("spring.jpa.hibernate.ddl-auto", () -> "create");
    }

    @Autowired
    private PostRepositoryImpl sut;

    @Autowired
    private PostR2dbcRepository postR2dbcRepository;

    @Test
    void save() {
        //given
        PostService.Create create = PostService.Create.of("Hello world!", "ID", "name");
        Post post = Post.post(create);

        //when
        Mono<Long> id = sut.save(post);

        //then
        assertNotNull(id);
        PostEntity ent = id.flatMap(savedId -> postR2dbcRepository.findById(savedId)).block();
        assertNotNull(ent);
        assertNotNull(ent.getPostId());
        assertNotNull(ent.getCreatedAt());
        assertNotNull(ent.getUpdatedAt());
        assertEquals(post.getContents(), ent.getContents());
        assertEquals(post.getOwnerId(), ent.getOwnerId());
        assertEquals(post.getOwnerEmail(), ent.getOwnerEmail());
        //assertEquals(post.getLikes().size(), ent.getLikes().size()); // ent 에는 likes 가 없음.
        //todo: 이후 findById로 조회시
    }
}

