package com.membercontext.memberAPI.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.membercontext.memberAPI.application.service.SignUpService;
import com.membercontext.memberAPI.domain.entity.Member;
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
        //Todo: 언제 any를 쓰고 언제 안 쓰는지 기준을 알아 보기.
    }

    @Test
    @DisplayName("회원 수정 요청 성공.")
    void update_URL() throws Exception {
        //given
        UpdateForm form = updateFormTestFixture.createAllFilledUpdateForm_Mock();

        //Fixme: A. 결합부분
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

        //Fixme 테스트: from으로 변경하는 부분 호출도 테스트 하고 싶고 값도 테스트 하고 싶다 보니까 모킹 객체를 많이 만들게 되는데
        //            Fixture 클래스로 숨겨 놓긴 했지만 과해지는 것같은 느낌이 있어서 지금 방식이 과한지 체크 받고 싶습니다.
        //            Member.from, MemberDto.from이 작동해야 데이터가 잘 변경 되어서 도메인으로 보내 주었다 (input의 문제라고 생각)
        //            DTO로 변경해야 데이터가 잘 변경 되어서 클라이언트에게 보내 주었다 (output의 문제라고 생각)
        //            UpdateForm을 HttpRequest에서 ObjectMapper로 넘기는 것까지만 input으로 보고 "200 성공" 정도만 output으로 보아야 할지 검증의 범위가 고민 됩니다.

        //Fixme 테스트(픽스쳐): 도메인 테스트에서 쓰이는 Fixture와 인터페이스에서 쓰이는 Fixture가 결합된 형태로 쓰여도 괜찮은지 신경 쓰입니다. (*A. 결합부분)

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
