package com.membercontext.memberAPI.application.service.signUp.integrated;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpServiceImpl;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import com.membercontext.memberAPI.web.controller.fixture.UpdateFormTestFixture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@Disabled
class SignUpServiceTest {

    @Autowired
    private SignUpService signUpService;

    @TestConfiguration
    static class config{
        @Bean
        @Primary
        public SignUpService signUpService_Test(MemberRepository memberRepository) {
            return new SignUpServiceImpl(memberRepository);
        }
    }

    @Test
    @DisplayName("회원가입 성공.")
    void signUp() {
        //given
        Member newMember = MemberTest.createMember(1L, "test@test.com", "test", "test", "test", "test", true, 0L);

        //when
        String result = signUpService.signUp(newMember);

        //then
        assertEquals("회원가입 성공", result);
    }
//
//    @Test
//    @DisplayName("회원가입 - 이미 가입한 회원.")
//    void signUp_ALREADY_REGISTERED_USER_Exception() {
//
//        //given
//        Member registeredMember = mock(Member.class);
//        Member newMember = mock(Member.class);
//        given(memberRepository.findByEmail(newMember.getEmail())).willReturn(Optional.of(registeredMember));
//
//        //when
//
//        //then
//        assertThrows(MemberException.class, () -> signUpService.signUp(newMember));
//    }
//    @Test
//    @DisplayName("회원 수정 성공.")
//    void update() {//Member를 받는다면
//
//        //given
//        UpdateFormTestFixture updateFormTestFixture = new UpdateFormTestFixture();
//        Member registeredMember = spy(Member.class);
//        Member updatingMember = Member.from(updateFormTestFixture.createAllFilledUpdateForm_Mock());
//        given(memberRepository.findById(updatingMember.getId())).willReturn(Optional.of(registeredMember));
//
//        //when
//        Member updatedMember = signUpService.update(updatingMember);
//
//        //then
//        assertEquals(updatingMember.getEmail(), updatedMember.getEmail());
//        assertEquals(updatingMember.getName(), updatedMember.getName());
//        assertEquals(updatingMember.getPassword(), updatedMember.getPassword());
//        assertEquals(updatingMember.getPhone(), updatedMember.getPhone());
//        assertEquals(updatingMember.getLocation(), updatedMember.getLocation());
//        assertEquals(updatingMember.getIsLocationServiceEnabled(), updatedMember.getIsLocationServiceEnabled());
//        assertEquals(updatingMember.getPoint(), updatedMember.getPoint());
//
//        verify(registeredMember, times(1)).update(updatingMember);
//        verify(memberRepository, times(1)).save(registeredMember);
//    }
//
//    @Test
//    @DisplayName("회원 수정 - 존재하지 않는 회원.")
//    void Update_NOT_EXISTING_USER_Exception() {
//
//        //given
//        Member updatingMember = mock(Member.class);
//        given(updatingMember.getId()).willReturn(1L);
//        given(memberRepository.findById(updatingMember.getId())).willReturn(Optional.empty());
//
//        //when
//
//        //then
//        assertThrows(MemberException.class, () -> signUpService.update(updatingMember));
//    }
//
//    @Test
//    @DisplayName("회원 삭제 성공.")
//    void delete() {
//
//        //given
//        Member registeredMember = mock(Member.class);
//        given(memberRepository.findById(registeredMember.getId())).willReturn(Optional.of(registeredMember));
//
//        //when
//        String result = signUpService.delete(registeredMember.getId());
//
//        //then
//        assertEquals("회원 삭제 성공", result);
//    }
//
//    @Test
//    @DisplayName("회원 삭제 - 존재하지 않는 회원.")
//    void delete_NOT_EXISTING_USER_Exception() {
//
//        //given
//        Member registeredMember = mock(Member.class);
//        given(registeredMember.getId()).willReturn(1L);
//        given(memberRepository.findById(registeredMember.getId())).willReturn(Optional.empty());
//
//        //when
//
//        //then
//        assertThrows(MemberException.class, () -> signUpService.delete(registeredMember.getId()));
//    }
}