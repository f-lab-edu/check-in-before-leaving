package com.membercontext.memberAPI.application.service;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.domain.entity.Member;
import com.membercontext.memberAPI.domain.fixture.MemberTestFixture;
import com.membercontext.memberAPI.web.controller.fixture.UpdateFormTestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceTest {

    @InjectMocks
    private SignUpServiceImpl signUpService;

    @Mock
    private MemberRepository memberRepository;

    private final MemberTestFixture memberTestFixture = new MemberTestFixture();


    @Test
    @DisplayName("회원가입 성공.")
    void signUp() {
        //given
        Member newMember = mock(Member.class);
        given(memberRepository.findByEmail(newMember.getEmail())).willReturn(Optional.empty());

        //when
        String result = signUpService.signUp(newMember);

        //then
        assertEquals("회원가입 성공", result);
    }

    @Test
    @DisplayName("회원가입 - 이미 가입한 회원.")
    void signUp_ALREADY_REGISTERED_USER_Exception() {

        //given
        Member registeredMember = mock(Member.class);
        Member newMember = mock(Member.class);
        given(memberRepository.findByEmail(newMember.getEmail())).willReturn(Optional.of(registeredMember));

        //when

        //then
        assertThrows(MemberException.class, () -> signUpService.signUp(newMember));
    }
    @Test
    @DisplayName("회원 수정 성공.")
    void update() {//Member를 받는다면

        //given
        UpdateFormTestFixture updateFormTestFixture = new UpdateFormTestFixture();
        Member registeredMember = spy(Member.class);
        Member updatingMember = Member.from(updateFormTestFixture.createAllFilledUpdateForm_Mock());
        //Fixme 픽스처: 이렇게 MemberFixture 만들지 않고 실제 객체와 섞여서 쓰면 안좋을까요?
        given(memberRepository.findById(updatingMember.getId())).willReturn(Optional.of(registeredMember));

        //when
        Member updatedMember = signUpService.update(updatingMember);

        //then
        assertEquals(updatingMember.getEmail(), updatedMember.getEmail());
        assertEquals(updatingMember.getName(), updatedMember.getName());
        assertEquals(updatingMember.getPassword(), updatedMember.getPassword());
        assertEquals(updatingMember.getPhone(), updatedMember.getPhone());
        assertEquals(updatingMember.getLocation(), updatedMember.getLocation());
        assertEquals(updatingMember.getIsLocationServiceEnabled(), updatedMember.getIsLocationServiceEnabled());
        assertEquals(updatingMember.getPoint(), updatedMember.getPoint());

        verify(registeredMember, times(1)).update(updatingMember);
        verify(memberRepository, times(1)).save(registeredMember);
    }

    @Test
    @DisplayName("회원 수정 - 존재하지 않는 회원.")
    void Update_NOT_EXISTING_USER_Exception() {

        //given
        Member updatingMember = mock(Member.class);
        given(updatingMember.getId()).willReturn(1L);
        given(memberRepository.findById(updatingMember.getId())).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberException.class, () -> signUpService.update(updatingMember));
    }

    @Test
    @DisplayName("회원 삭제 성공.")
    void delete() {

        //given
        Member registeredMember = mock(Member.class);
        given(memberRepository.findById(registeredMember.getId())).willReturn(Optional.of(registeredMember));

        //when
        String result = signUpService.delete(registeredMember.getId());

        //then
        assertEquals("회원 삭제 성공", result);
    }

    @Test
    @DisplayName("회원 삭제 - 존재하지 않는 회원.")
    void delete_NOT_EXISTING_USER_Exception() {

        //given
        Member registeredMember = mock(Member.class);
        given(registeredMember.getId()).willReturn(1L);
        given(memberRepository.findById(registeredMember.getId())).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberException.class, () -> signUpService.delete(registeredMember.getId()));
    }
}