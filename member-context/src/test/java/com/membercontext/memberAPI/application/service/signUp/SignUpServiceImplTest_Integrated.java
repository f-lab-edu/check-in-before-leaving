package com.membercontext.memberAPI.application.service.signUp;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //GenerationType.IDENTITY
//fixme: GenerationType.IDENTITY로 하니 ID값을 DB에서 정해준 값만 쓸수 있어서 테스테에 순서가 필요하게 됩니다.
//       테스트는 순서에 상관없이 작성해야 된다고 하던데 이런 경우는 괜찮은 걸까요?
class SignUpServiceImplTest_Integrated {

    @Autowired
    private SignUpService sut;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 성공.")
    @Order(1)
    @Transactional
    void signUp() {
        //given
        Member newMember = MemberTest.createMember( "test@test.com", "test", "test", "test", "test", true, 0L);

        //when
        String result = sut.signUp(newMember);

        //then
        assertEquals("회원가입 성공", result);
    }

    @Test
    @DisplayName("회원 수정 성공.")
    @Transactional
    @Order(2)
    void update() {//Member를 받는다면

        //given
        Member registeredMember = MemberTest.createMember("test@test.com", "test", "test", "test", "test", true, 0L);
        sut.signUp(registeredMember); //fixme: ID값을 따로 안 받아 와도 업데이트가 되는데 JPA라서 그런 걸까요?

        Member updatingMember = MemberTest.createUpdatingMember(registeredMember.getId(), "updated@test.com", "updated", "updated", "updated", "updated", false, 100L);

        //when
        Member updatedMember = sut.update(updatingMember);

        //then
        assertEquals(updatingMember.getEmail(), updatedMember.getEmail());
        assertEquals(updatingMember.getName(), updatedMember.getName());
        assertEquals(updatingMember.getPassword(), updatedMember.getPassword());
        assertEquals(updatingMember.getPhone(), updatedMember.getPhone());
        assertEquals(updatingMember.getLocation(), updatedMember.getLocation());
        assertEquals(updatingMember.getIsLocationServiceEnabled(), updatedMember.getIsLocationServiceEnabled());
        assertEquals(updatingMember.getPoint(), updatedMember.getPoint());


        }

    @Test
    @DisplayName("회원 삭제 성공.")
    @Transactional
    @Order(3)
    void delete() {

        //given
        Member registeredMember = MemberTest.createMember("test@test.com", "test", "test", "test", "test", true, 0L);
        sut.signUp(registeredMember);

        //when
        String result = sut.delete(registeredMember.getId());

        //then
        assertEquals("회원 삭제 성공", result);

    }

}