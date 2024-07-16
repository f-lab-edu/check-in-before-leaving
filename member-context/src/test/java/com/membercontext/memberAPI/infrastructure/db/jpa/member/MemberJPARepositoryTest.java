package com.membercontext.memberAPI.infrastructure.db.jpa.member;

import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberJPARepositoryTest {

    @Autowired
    MemberJPARepository memberSpringJPARepository;

    @Test
    void save() {
        Member member = MemberTest.createMember(1L, "test@test.com", "password", "name", "010-1234-5678", "location", true, 100L);

        memberSpringJPARepository.save(member);

        Member member2 = memberSpringJPARepository.findById(1L).get();

        assertEquals(member.getEmail(), member2.getEmail());
    }

    @Autowired
    ApplicationContext context;

    @Test
    void test() {
        for (String name : context.getBeanDefinitionNames()) {
            System.out.println(name);
        }
    }
}