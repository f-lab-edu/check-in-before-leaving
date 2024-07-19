//package com.membercontext.memberAPI.application.service.signUp.deco.integrated;
//
//import com.membercontext.memberAPI.application.repository.MemberRepository;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.decoration_deprecated.SignUpPasswordEncryptionServiceImpl_JavaCrypto;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.SignUpServiceImpl;
//import com.membercontext.memberAPI.domain.entity.member.Member;
//import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//@SpringBootTest
//@Disabled
//class SignUpPasswordEncryptionServiceTest_JavaCrypto {
//
//    @Autowired
//    private SignUpService sut;
//
//    @TestConfiguration
//    static class config{
//        //@Autowired
//        //private JavaCryptoAdapter javaCryptoAdapter;
//        @Bean
//        @Primary
//        public SignUpService signUpService_Test(MemberRepository memberRepository) {
//            return new SignUpPasswordEncryptionServiceImpl_JavaCrypto(
//                    new SignUpServiceImpl(memberRepository));
//        }
//    }
//
//    @Test
//    void signUp() {
//        //given
//        Member newMember = MemberTest.createMember(1L, "test", "test", "test", "test", "test", true, 0L);
//
//        //when
//        String result = sut.signUp(newMember);
//
//        //then
//        assertEquals("회원가입 성공", result);
//    }
//}