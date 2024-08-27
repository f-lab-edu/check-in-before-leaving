package com.membercontext.memberAPI.application.aop.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.exception.member.MemberException;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.infrastructure.encryption.db.JavaCryptoIVRepository;
import com.membercontext.memberAPI.web.controller.SignUpController;
import com.membercontext.memberAPI.web.controller.fixture.SignUpFormTestFixture;
import com.membercontext.memberAPI.web.controller.fixture.UpdateFormTestFixture;
import com.membercontext.memberAPI.web.controller.form.SignUpForm;
import com.membercontext.memberAPI.web.dto.MemberDto;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SignUpController.class)
@Import(JavaCryptoUtil.class)
@Disabled
class AuthenticationAspectTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    JavaCryptoUtil encryption;

    @MockBean
    JavaCryptoIVRepository javaCryptoIVRepository;

    @SpyBean
    private AuthenticationAspect authenticationAspect;

    @MockBean
    private SignUpService signUpService;

    private final String requestURL = "/log-in";
    @Autowired
    private JavaCryptoUtil javaCryptoUtil;


    @Test
    @DisplayName("인증 AOP 작동 중")
    void authenticateAOP() throws Exception {
        //given
        SignUpForm form = new SignUpFormTestFixture().createAllFilledSignUpForm_Mock();
        when(signUpService.signUp(any(Member.class))).thenReturn("회원가입 성공");

        final String cookieName = "CKIB4LV";
        String encryptedEmail = encryption.encrypt(form.getEmail());
        String sessionId = UUID.randomUUID().toString();

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(cookieName, encryptedEmail))
                .sessionAttr(encryptedEmail, sessionId)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("회원가입 성공"))
                .andExpect(result -> {
                    //Cookie
                    assertEquals("CKIB4LV", result.getRequest().getCookies()[0].getName());
                    assertEquals(encryptedEmail, result.getRequest().getCookies()[0].getValue());
                    assertEquals(javaCryptoUtil.encrypt("test@test.com"), result.getRequest().getCookies()[0].getValue());

                    //Session
                    assertEquals(sessionId, result.getRequest().getSession().getAttribute(encryptedEmail));
                    assertEquals(sessionId, result.getRequest().getSession().getAttribute(javaCryptoUtil.encrypt("test@test.com")));
                })
                .andExpect(request().sessionAttribute(encryptedEmail, sessionId));
        verify(authenticationAspect, times(1)).authenticate();
    }
    @Test
    @DisplayName("도메인의 로그인 쿠키 없음.")
    void authenticateAOP_Wrong_Cookie() throws Exception {
        //given
        SignUpForm form = new SignUpFormTestFixture().createAllFilledSignUpForm_Mock();
        when(signUpService.signUp(any(Member.class))).thenReturn("회원가입 성공");
        final String cookieName = "CKIB4LV";

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("otherDomain", "otherDomainValue"))
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertNotEquals("CKIB4LV", result.getRequest().getCookies()[0].getName());
                    assertTrue(result.getResolvedException() instanceof MemberException);
                    assertEquals("로그인 정보가 없습니다. 로그인이 필요합니다.", result.getResolvedException().getMessage());
                });

        verify(authenticationAspect, times(1)).authenticate();
    }
    @Test
    @DisplayName("쿠키 없음")
    void authenticateAOP_No_Cookie() throws Exception {
        //given
        SignUpForm form = new SignUpFormTestFixture().createAllFilledSignUpForm_Mock();
        when(signUpService.signUp(any(Member.class))).thenReturn("회원가입 성공");

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertThrows(NullPointerException.class, () -> result.getRequest().getCookies()[0].getValue());
                    assertTrue(result.getResolvedException() instanceof MemberException);
                    assertEquals("이력을 찾을 수 없습니다. 로그인이 필요합니다.", result.getResolvedException().getMessage());
                });
        verify(authenticationAspect, times(1)).authenticate();

    }

    @Test
    @DisplayName("세션 없음")
    void authenticateAOP_No_Session() throws Exception {
        //given
        SignUpForm form = new SignUpFormTestFixture().createAllFilledSignUpForm_Mock();
        when(signUpService.signUp(any(Member.class))).thenReturn("회원가입 성공");

        final String cookieName = "CKIB4LV";
        String encryptedEmail = encryption.encrypt(form.getEmail());

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(cookieName, encryptedEmail))
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertTrue(result.getResolvedException() instanceof MemberException);
                    assertEquals("서버에 세션이 존재하지 않습니다.", result.getResolvedException().getMessage());
                });
        verify(authenticationAspect, times(1)).authenticate();
    }
    @Test
    @DisplayName("세션 ID 없음")
    void authenticateAOP_No_Session_ID() throws Exception {
        //given
        SignUpForm form = new SignUpFormTestFixture().createAllFilledSignUpForm_Mock();
        when(signUpService.signUp(any(Member.class))).thenReturn("회원가입 성공");

        final String cookieName = "CKIB4LV";
        String encryptedEmail = encryption.encrypt(form.getEmail());

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie(cookieName, encryptedEmail))
                .sessionAttr("no session Id", "not UUID")
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertNull(result.getRequest().getSession().getAttribute(encryptedEmail));
                    assertTrue(result.getResolvedException() instanceof MemberException);
                    assertEquals("로그인 정보가 서버에 존재하지 않습니다. 로그인이 필요합니다.", result.getResolvedException().getMessage());
                });
        verify(authenticationAspect, times(1)).authenticate();
    }

}