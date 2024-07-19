package com.membercontext.memberAPI.application.service.signUp.deco;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.deco.SignUpPasswordEncryptionServiceImpl_JavaCrypto;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpServiceImpl;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SignUpPasswordEncryptionServiceImplTest_JavaCrypto {

    private SignUpPasswordEncryptionServiceImpl_JavaCrypto sut;

    @InjectMocks
    private SignUpServiceImpl signUpServiceImpl;

   // @Mock
   // JavaCryptoEncryption encryption;

    @Mock
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        //fixme (데코레이션 패턴) 테스트 3: 이렇게 하나씩 유닛 테스트를 할 수 있습니다.
        sut = new SignUpPasswordEncryptionServiceImpl_JavaCrypto(signUpServiceImpl);
        //sut = new SignUpPasswordEncryptionServiceImpl_JavaCrypto(signUpServiceImpl, encryption);
    }

    @Test
    void signUp() {
        //given
        Member newMember = MemberTest.createMember(1L, "test", "test", "test", "test", "test", true, 0L);
        //when(encryption.encrypt(newMember)).thenReturn("encryptedPassword");

        //when
        String result = sut.signUp(newMember);

        //then
        assertEquals("회원가입 성공", result);
    }
}