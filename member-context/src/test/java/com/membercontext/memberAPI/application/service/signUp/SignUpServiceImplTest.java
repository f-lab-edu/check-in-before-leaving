package com.membercontext.memberAPI.application.service.signUp;

import com.membercontext.common.stub.JavaCryptoUtilMockStub;
import com.membercontext.common.stub.MemberJPARepositoryStub;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.SignUpServiceImpl;

import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.membercontext.memberAPI.application.exception.member.MemberErrorCode.NOT_EXITING_USER;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class SignUpServiceImplTest {

    @InjectMocks
    private SignUpServiceImpl sut;

    @Spy
    private MemberRepository memberRepository = new MemberJPARepositoryStub();

    @Mock
    private JavaCryptoUtil encryption = new JavaCryptoUtilMockStub();

    @Test
    @DisplayName("회원가입 성공.")
    void signUp() {
        //given
        Member newMember = MemberFixture.create();

        //when
        String id = sut.signUp(newMember);

        //then
        assertNotNull(id);
    }

    @Test
    @DisplayName("회원 수정 성공.")
    void update() {

        //given
        Member originalMember = MemberFixture.create();
        String id = memberRepository.save(originalMember);
        Member updatingMember = MemberFixture.createUpdatingMember(id, "updatedName", "updatedPassword", "updatedname", "010000000", "updatedLoc", false, 10L);

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
        Member registeredMember = MemberFixture.create();
        String id = memberRepository.save(registeredMember);

        //when
        sut.delete(id);

        //then
        Exception exception = assertThrows(MemberException.class, () -> memberRepository.findById(id));
        assertEquals(NOT_EXITING_USER.getDeatil(), exception.getMessage());
    }
}