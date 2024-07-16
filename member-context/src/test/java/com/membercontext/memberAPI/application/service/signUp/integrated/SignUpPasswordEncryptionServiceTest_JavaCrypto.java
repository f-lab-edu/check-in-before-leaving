package com.membercontext.memberAPI.application.service.signUp.integrated;

import com.membercontext.memberAPI.application.repository.MemberRepository;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpPasswordEncryptionServiceImpl_JavaCrypto;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpServiceImpl;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Disabled
class SignUpPasswordEncryptionServiceTest_JavaCrypto {

    @Autowired
    private SignUpService sut;

    @TestConfiguration
    static class config{
        //@Autowired
        //private JavaCryptoAdapter javaCryptoAdapter;
        //fixme (데코레이션 패턴) 테스트2 : DB와 연결되기에 통합테스트가 필요하다고 생각했고 필요시 하나씩 테스트가 가능합니다.
        @Bean
        @Primary
        public SignUpService signUpService_Test(MemberRepository memberRepository) {
            return new SignUpPasswordEncryptionServiceImpl_JavaCrypto(
                    new SignUpServiceImpl(memberRepository));
        }
    }

    @Test
    void signUp() {
        //given
        Member newMember = MemberTest.createMember(1L, "test", "test", "test", "test", "test", true, 0L);

        //when
        String result = sut.signUp(newMember);

        //then
        assertEquals("회원가입 성공", result);
    }
}