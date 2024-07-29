//package com.membercontext.memberAPI.application.service.signUp.deco;
//
//import com.membercontext.memberAPI.application.repository.MemberRepository;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.decoration_deprecated.SignUpEmailServiceImpl;
//import com.membercontext.memberAPI.application.service.SignUpSerivces.Impl.SignUpServiceImpl;
//import com.membercontext.memberAPI.domain.entity.member.Member;
//import com.membercontext.memberAPI.domain.entity.member.testFixture.MemberTest;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//public class SignUpEmailServiceImplTest {
//
//    private SignUpEmailServiceImpl sut;
//
//    @InjectMocks
//    private SignUpServiceImpl signUpServiceImpl;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @BeforeEach
//    void setUp() {
//        sut = new SignUpEmailServiceImpl(signUpServiceImpl);
//    }
//
//    @Test
//    @DisplayName("회원가입 성공.")
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
