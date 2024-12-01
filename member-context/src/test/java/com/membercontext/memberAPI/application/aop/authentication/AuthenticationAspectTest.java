package com.membercontext.memberAPI.application.aop.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.common.fixture.domain.dto.crud.UpdateFixture;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.domain.exceptions.member.MemberErrorCode;
import com.membercontext.memberAPI.domain.exceptions.member.MemberException;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.infrastructure.encryption.JavaCryptoUtil;
import com.membercontext.memberAPI.web.controller.SignUpController;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static com.membercontext.memberAPI.web.controller.LogInController.COOKIE_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({SignUpController.class})
class AuthenticationAspectTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    JavaCryptoUtil encryption;

    @SpyBean
    private AuthenticationAspect authenticationAspect; //이게 빈으로 있어야 AOP 작동.

    @MockBean
    private MemberWriteService signUpService;

    private final String requestURL = "/sign-in";

    @Test
    @DisplayName("인증 AOP 작동 중")
    void authenticateAOP() throws Exception {
        //given
        MemberService.Update req = UpdateFixture.create();
        when(signUpService.update(any(Member.class))).thenReturn(MemberFixture.createMemberWithId("UUID"));

        MvcResult result = LogInTestHelper.Login();
        Cookie cookie = result.getResponse().getCookie(COOKIE_NAME);
        MockHttpSession session = (MockHttpSession) result.getRequest().getSession();

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .session(session)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SignUpController.MEMBER_UPDATE_SUCCESS_MESSAGE))
                .andExpect(resultReturned -> {
                    assertEquals(COOKIE_NAME, resultReturned.getRequest().getCookies()[0].getName());
                    assertTrue(Collections.list(session.getAttributeNames()).contains(cookie.getValue()));
                });
        verify(authenticationAspect, times(1)).authenticate();
    }

    @Test
    @DisplayName("도메인의 로그인 쿠키 없음.")
    void authenticateAOP_Wrong_Cookie() throws Exception {
        //given
        MemberService.Update req = UpdateFixture.create();
        when(signUpService.update(any(Member.class))).thenReturn(MemberFixture.createMemberWithId("UUID"));

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(new Cookie("otherDomain", "otherDomainValue"))
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(result -> {
                    assertNotEquals(COOKIE_NAME, result.getRequest().getCookies()[0].getName());
                    assertInstanceOf(MemberException.class, result.getResolvedException());
                    assertEquals(MemberErrorCode.NOT_LOGGED_IN.getDeatil(), result.getResolvedException().getMessage());
                });

        verify(authenticationAspect, times(1)).authenticate();
    }

    @Test
    @DisplayName("쿠키 없음")
    void authenticateAOP_No_Cookie() throws Exception {
        //given
        MemberService.Update req = UpdateFixture.create();
        when(signUpService.update(any(Member.class))).thenReturn(MemberFixture.createMemberWithId("UUID"));

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest());
        resultActions.andExpect(result -> {
            assertThrows(NullPointerException.class, () -> result.getRequest().getCookies()[0].getValue());
            assertInstanceOf(MemberException.class, result.getResolvedException());
            assertEquals(MemberErrorCode.NO_COOKIE.getDeatil(), result.getResolvedException().getMessage());
        });
        verify(authenticationAspect, times(1)).authenticate();

    }

    @Test
    @DisplayName("세션 없음")
    void authenticateAOP_No_Session() throws Exception {
        //given
        MemberService.Update req = UpdateFixture.create();
        when(signUpService.update(any(Member.class))).thenReturn(MemberFixture.createMemberWithId("UUID"));

        MvcResult result = LogInTestHelper.Login();
        Cookie cookie = result.getResponse().getCookie(COOKIE_NAME);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(resultReturned -> {
                    assertInstanceOf(MemberException.class, resultReturned.getResolvedException());
                    assertEquals(MemberErrorCode.NO_SESSION.getDeatil(), resultReturned.getResolvedException().getMessage());
                });
        verify(authenticationAspect, times(1)).authenticate();
    }

    @Test
    @DisplayName("세션 ID 없음")
    void authenticateAOP_No_Session_ID() throws Exception {
        //given
        MemberService.Update req = UpdateFixture.create();
        when(signUpService.update(any(Member.class))).thenReturn(MemberFixture.createMemberWithId("UUID"));

        MvcResult result = LogInTestHelper.Login();
        Cookie cookie = result.getResponse().getCookie(COOKIE_NAME);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(cookie)
                .sessionAttr("no session Id", "not UUID")
                .content(mapper.writeValueAsString(req)));

        //then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(resultReturned -> {
                    assertInstanceOf(MemberException.class, resultReturned.getResolvedException());
                    assertEquals(MemberErrorCode.NO_SESSION_ID.getDeatil(), resultReturned.getResolvedException().getMessage());
                });
        verify(authenticationAspect, times(1)).authenticate();
    }

}