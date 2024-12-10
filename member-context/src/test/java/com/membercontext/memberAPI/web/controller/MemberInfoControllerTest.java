package com.membercontext.memberAPI.web.controller;

import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.domain.MemberFixture;
import com.membercontext.memberAPI.application.aop.authentication.AuthenticationAspect;
import com.membercontext.memberAPI.application.service.LogInService;
import com.membercontext.memberAPI.application.service.MemberInfo.MemberInfoService;
import com.membercontext.memberAPI.domain.entity.member.Member;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.mock.mockito.SpyBeans;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.membercontext.common.LogInTestHelper.TEST_COOKIE;
import static com.membercontext.common.LogInTestHelper.TEST_SESSION;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MemberInfoController.class)
@SpyBeans(@SpyBean(AuthenticationAspect.class))
class MemberInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberInfoService memberInfoService;

    private static final String requestUrl = URIInfo.INDIVIDUAL;

    @BeforeEach
    void setUp() throws Exception {
        LogInTestHelper.Login();
    }

    @Test
    @DisplayName("아이디로 회원 정보 가져옴")
    void getMemberInfo() throws Exception {
        //given
        Member memberToSearch = MemberFixture.createMemberWithId("UUID");
        when(memberInfoService.getMemberInfo(anyString())).thenReturn(memberToSearch);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestUrl)
                .cookie(TEST_COOKIE)
                .session(TEST_SESSION)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", memberToSearch.getId()));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("회원 정보 조회 성공"))
                .andExpect(jsonPath("$.data.id").value(memberToSearch.getId()))
                .andExpect(jsonPath("$.data.email").value(memberToSearch.getEmail()))
                .andExpect(jsonPath("$.data.password").value(memberToSearch.getPassword()))
                .andExpect(jsonPath("$.data.name").value(memberToSearch.getName()))
                .andExpect(jsonPath("$.data.phone").value(memberToSearch.getPhone()))
                .andExpect(jsonPath("$.data.address").value(memberToSearch.getAddress()))
                .andExpect(jsonPath("$.data.locationServiceEnabled").value(memberToSearch.isLocationServiceEnabled()))
                .andExpect(jsonPath("$.data.point").value(memberToSearch.getPoint()));
    }
}