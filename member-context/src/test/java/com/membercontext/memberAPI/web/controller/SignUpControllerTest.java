package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.aop.authentication.AuthenticationAspect;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.fixture.MemberTestFixture;
import com.membercontext.memberAPI.web.controller.fixture.MemberDtoTextFixture;
import com.membercontext.memberAPI.web.controller.fixture.SignUpFormTestFixture;
import com.membercontext.memberAPI.web.controller.fixture.UpdateFormTestFixture;
import com.membercontext.memberAPI.web.controller.form.SignUpForm;
import com.membercontext.memberAPI.web.controller.form.UpdateForm;
import com.membercontext.memberAPI.web.dto.MemberDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private SignUpService signUpService;

    private static MockedStatic<Member> member;
    private static MockedStatic<MemberDto> memberDto;

    private final String requestURL = "/sign-in";
    private final SignUpFormTestFixture signUpFormTestFixture = new SignUpFormTestFixture();
    private final UpdateFormTestFixture updateFormTestFixture = new UpdateFormTestFixture();

    @BeforeAll
    static void setUp() {
        member = mockStatic(Member.class);
        memberDto = mockStatic(MemberDto.class);
    }
    @AfterAll
    static void tearDown() {
        member.close();
        memberDto.close();
    }

    @Test
    @DisplayName("회원가입 요청 성공.")
    void signUp_URL() throws Exception {
        //given
        SignUpForm form = signUpFormTestFixture.createAllFilledSignUpForm_Mock();

        when(Member.from(any(SignUpForm.class))).thenReturn(mock(Member.class));
        when(signUpService.signUp(any(Member.class))).thenReturn("회원가입 성공");

        //when
        ResultActions resultActions = mockMvc.perform(post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("회원가입 성공"));
        member.verify(() -> Member.from(any(SignUpForm.class)), times(1));
        verify(signUpService, times(1)).signUp(any(Member.class));
        //check: 언제 any를 쓰고 언제 안 쓰는지 기준을 알아 보기.
    }

    @Test
    @DisplayName("회원 수정 요청 성공.")
    void update_URL() throws Exception {
        //given
        UpdateForm form = updateFormTestFixture.createAllFilledUpdateForm_Mock();

        MemberTestFixture memberTestFixture = new MemberTestFixture();
        Member updatedMember = memberTestFixture.from_Mock(form);

        MemberDtoTextFixture memberDtoTextFixture = new MemberDtoTextFixture();
        MemberDto expectedDto = memberDtoTextFixture.from_Mock(updatedMember);

        when(Member.from(any(UpdateForm.class))).thenReturn(updatedMember);
        when(signUpService.update(any(Member.class))).thenReturn(updatedMember);
        when(MemberDto.from(any(Member.class))).thenReturn(expectedDto);

        //when
        ResultActions resultActions = mockMvc.perform(put(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(form)));

       //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedDto.getId()))
                .andExpect(jsonPath("$.email").value(expectedDto.getEmail()))
                .andExpect(jsonPath("$.password").value(expectedDto.getPassword()))
                .andExpect(jsonPath("$.name").value(expectedDto.getName()))
                .andExpect(jsonPath("$.phone").value(expectedDto.getPhone()))
                .andExpect(jsonPath("$.location").value(expectedDto.getLocation()))
                .andExpect(jsonPath("$.isLocationServiceEnabled").value(expectedDto.getIsLocationServiceEnabled()))
                .andExpect(jsonPath("$.point").value(expectedDto.getPoint()));
        member.verify(() -> Member.from(any(UpdateForm.class)), times(1));
        memberDto.verify(() -> MemberDto.from(any(Member.class)), times(1));
        verify(signUpService, times(1)).update(any(Member.class));


    }


    @Test
    @DisplayName("회원 삭제 요청 성공.")
    void delete_URL() throws Exception {
        //given
        when(signUpService.delete(anyLong())).thenReturn("회원 삭제 성공");

        //when
        ResultActions resultActions = mockMvc.perform(delete(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", "1"));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$").value("회원 삭제 성공"));
        verify(signUpService, times(1)).delete(1L);
    }


}
