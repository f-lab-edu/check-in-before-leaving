package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.common.LogInTestHelper;
import com.membercontext.common.fixture.domain.dto.crud.SignUpFixture;
import com.membercontext.common.fixture.domain.dto.crud.UpdateFixture;
import com.membercontext.memberAPI.application.aop.authentication.AuthenticationAspect;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.domain.entity.member.Member;

import com.membercontext.memberAPI.domain.entity.member.MemberService;
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
import static com.membercontext.memberAPI.web.controller.SignUpController.MEMBER_DELETE_SUCCESS_MESSAGE;
import static com.membercontext.memberAPI.web.controller.SignUpController.MEMBER_SIGN_UP_SUCCESS_MESSAGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
@SpyBeans(@SpyBean(AuthenticationAspect.class))
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private MemberWriteService signUpService;

    @BeforeEach
    void setUp() throws Exception {
        LogInTestHelper.Login();
    }

    private final String requestURL = "/sign-in";

    @Test
    @DisplayName("회원가입 요청 성공.")
    void signUp_Success() throws Exception {

        //given
        String idReturned = "UUID";
        MemberService.SignUp form = SignUpFixture.create();
        when(signUpService.signUp(any(Member.class))).thenReturn(idReturned);

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MEMBER_SIGN_UP_SUCCESS_MESSAGE))
                .andExpect(jsonPath("$.data").value(idReturned));
    }


    @Test
    @DisplayName("회원 수정 요청 성공.")
    void update_URL() throws Exception {
        //given
        MemberService.Update form = UpdateFixture.create();
        Member updatedMember = Member.from(form);
        when(signUpService.update(any(Member.class))).thenReturn(updatedMember);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(TEST_COOKIE)
                .session(TEST_SESSION)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(SignUpController.MEMBER_UPDATE_SUCCESS_MESSAGE))
                .andExpect(jsonPath("$.data.id").value(updatedMember.getId()))
                .andExpect(jsonPath("$.data.email").value(updatedMember.getEmail()))
                .andExpect(jsonPath("$.data.password").value(updatedMember.getPassword()))
                .andExpect(jsonPath("$.data.name").value(updatedMember.getName()))
                .andExpect(jsonPath("$.data.phone").value(updatedMember.getPhone()))
                .andExpect(jsonPath("$.data.address").value(updatedMember.getAddress()))
                .andExpect(jsonPath("$.data.locationServiceEnabled").value(updatedMember.isLocationServiceEnabled()))
                .andExpect(jsonPath("$.data.point").value(updatedMember.getPoint()));
    }

    @Test
    @DisplayName("회원 삭제 요청 성공.")
    void delete_URL() throws Exception {
        //todo: UUID 체크법.
        //given
        String id = "ANY_ID";

        //when
        ResultActions resultActions = mockMvc.perform(delete(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .cookie(TEST_COOKIE)
                .session(TEST_SESSION)
                .param("id", id));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(MEMBER_DELETE_SUCCESS_MESSAGE));
    }

}
