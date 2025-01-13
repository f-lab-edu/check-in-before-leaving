package com.membercontext.memberAPI.application.service.signUp;

import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.stub.MemberJPARepositoryStub;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.Impl.MemberWriteApplication;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.domain.repository.MemberRepository;
import com.membercontext.memberAPI.domain.repository.PasswordEncoder;
import com.membercontext.memberAPI.infrastructure.encryption.bcrypt.BycryptEncoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberWriteApplicationTest {

    @InjectMocks
    private MemberWriteApplication sut;

    @Mock
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 성공.")
    void signUp() {
        //given
        String uuid = "uuid";
        Member newMember = MemberFixture.create();
        when(memberService.signUp(newMember)).thenReturn(uuid);

        //when
        String id = sut.signUp(newMember);

        //then
        assertNotNull(id);
    }

    @Test
    @DisplayName("회원 수정 성공.")
    void update() {

        //given
        String id = "uuid";
        Member updatingMember = MemberFixture.createUpdatingMember(id, "updatedName", "updatedPassword", "updatedname", "010000000", "updatedLoc", false, 10L);
        when(memberService.update(updatingMember)).thenReturn(updatingMember);

        //when
        Member returned = sut.update(updatingMember);

        //then
        assertEquals(updatingMember.getId(), returned.getId());
        assertEquals(updatingMember.getEmail(), returned.getEmail());
        assertEquals(updatingMember.getName(), returned.getName());
        assertEquals(updatingMember.getPassword(), returned.getPassword());
        assertEquals(updatingMember.getPhone(), returned.getPhone());
        assertEquals(updatingMember.getAddress(), returned.getAddress());
        assertEquals(updatingMember.isLocationServiceEnabled(), returned.isLocationServiceEnabled());
        assertEquals(updatingMember.getPoint(), returned.getPoint());
    }

    @Test
    @DisplayName("회원 삭제 성공.")
    void delete() {
        //given
        String id = "uuid";

        //when
        sut.delete(id);

        //then
        verify(memberService).delete(id);
    }


}