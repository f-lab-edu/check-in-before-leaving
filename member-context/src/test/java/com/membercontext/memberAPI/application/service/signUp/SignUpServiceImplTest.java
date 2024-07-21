package com.membercontext.memberAPI.application.service.signUp;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.SignUpServiceImpl;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.web.controller.fixture.UpdateFormTestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignUpServiceImplTest {

    @InjectMocks
    private SignUpServiceImpl sut;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JavaCryptoUtil encryption;

    @Test
    @DisplayName("회원가입 성공.")
    void signUp() {
        //given
        Member newMember = MemberTest.createMember( "test@test.com", "test", "test", "test", "test", true, 0L);

        //when
        String result = sut.signUp(newMember);

        //then
        assertEquals("회원가입 성공", result);
        verify(memberRepository, times(1)).save(newMember);
    }

    @Test
    @DisplayName("회원 수정 성공.")
    void update() {//Member를 받는다면

        //given
        Member updatingMember = mock(Member.class);

        //when
        sut.update(updatingMember);

        //then
        verify(memberRepository, times(1)).update(updatingMember);

    }

    @Test
    @DisplayName("회원 삭제 성공.")
    void delete() {
        //given
        Member registeredMember = mock(Member.class);

        //when
        String result = sut.delete(registeredMember.getId());

        //then
        assertEquals("회원 삭제 성공", result);
        verify(memberRepository, times(1)).delete(registeredMember.getId());
    }


}