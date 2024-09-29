package com.membercontext.memberAPI.infrastructure.db.jpa.member;

import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberJPARepositoryTest {

    @InjectMocks
    MemberJPARepository sut;

    @Mock
    MemberSpringJPARepository memberSpringJPARepository;

    @Test
    @DisplayName("이메일로 조회 - 성공.")
    void findByEmail() {
        //given
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));

        //when
        sut.findByEmail(member.getEmail());

        //then
        verify(memberSpringJPARepository).findByEmail(member.getEmail());
    }

    @Test
    @DisplayName("이메일로 조회 - 존재하지 않는 회원.")
    void findByEmail_NOT_EXISTING_MEMBER() {
        //given
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findByEmail(member.getEmail())).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberException.class, () -> sut.findByEmail(member.getEmail()));
    }

    @Test
    @DisplayName("회원 ID로 조회 - 성공.")
    void findById() {
        //given
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findById(member.getId())).willReturn(Optional.of(member));

        //when
        sut.findById(member.getId());

        //then
        verify(memberSpringJPARepository).findById(member.getId());
    }

    @Test
    @DisplayName("회원 ID로 조회 - 존재하지 않는 회원.")
    void findById_NOT_EXISTING_MEMBER() {
        //given
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findById(member.getId())).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberException.class, () -> sut.findById(member.getId()));
    }

    @Test
    @DisplayName("회원가입 - 성공.")
    void save() {
        //given
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findByEmail(member.getEmail())).willReturn(Optional.empty());

        sut.save(member);

        verify(memberSpringJPARepository).save(member);

    }

    @Test
    @DisplayName("회원가입 - 이미 가입한 회원.")
    void save_ALREADY_REGISTERED_USER_Exception() {

        //given
        Member registeredMember = mock(Member.class);
        Member newMember = mock(Member.class);
        given(memberSpringJPARepository.findByEmail(newMember.getEmail())).willReturn(Optional.of(registeredMember));

        //when

        //then
        assertThrows(MemberException.class, () -> sut.save(newMember));
    }

    @Test
    void delete() {
        //given
        String id = UUID.randomUUID().toString();
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findById(id)).willReturn(Optional.of(member));

        //when
        sut.delete(id);

        //then
        verify(memberSpringJPARepository).delete(member);
    }

    @Test
    @DisplayName("회원 삭제 - 존재하지 않는 회원.")
    void delete_NOT_EXISTING_USER_Exception() {

        //given
        String id = "NOT_EXIST";
        Member registeredMember = mock(Member.class);
        given(registeredMember.getId()).willReturn(id);
        given(memberSpringJPARepository.findById(registeredMember.getId())).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberException.class, () -> sut.delete(registeredMember.getId()));
    }

    @Test
    void update() {
        //given
        Member updatingMember = mock(Member.class);
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findById(updatingMember.getId())).willReturn(Optional.of(member));

        //when
        sut.update(updatingMember);

        //then
        verify(member).update(updatingMember);
        verify(memberSpringJPARepository).save(member);
    }

    @Test
    @DisplayName("회원 수정 - 존재하지 않는 회원.")
    void Update_NOT_EXISTING_USER_Exception() {

        //given
        String id = "NOT_EXIST";
        Member updatingMember = mock(Member.class);
        given(updatingMember.getId()).willReturn(id);
        given(memberSpringJPARepository.findById(updatingMember.getId())).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberException.class, () -> sut.update(updatingMember));
    }

    @Test
    @DisplayName("회원 수정 - 실패.")
    void Update_FAILED_Exception() {

        //given
        Member updatingMember = mock(Member.class);
        Member member = mock(Member.class);
        given(memberSpringJPARepository.findById(updatingMember.getId())).willReturn(Optional.of(member));
        sut.update(updatingMember);
        given(memberSpringJPARepository.findById(updatingMember.getId())).willReturn(Optional.empty());

        //when

        //then
        assertThrows(MemberException.class, () -> sut.update(updatingMember));
    }
}