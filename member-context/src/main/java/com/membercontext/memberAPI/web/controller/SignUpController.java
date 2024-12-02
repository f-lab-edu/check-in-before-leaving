package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.MemberWriteSerivces.MemberWriteService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class SignUpController {

    public static final String MEMBER_SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";
    public static final String MEMBER_UPDATE_SUCCESS_MESSAGE = "회원 수정 성공";
    public static final String MEMBER_DELETE_SUCCESS_MESSAGE = "회원 삭제 성공";

    private final MemberWriteService signUpService;

    @PostMapping
    @NoAuthentication
    public ResponseEntity<DefaultHTTPResponse<String>> signIn(@Validated @RequestBody MemberService.SignUp signUpRequest) {
        String id = signUpService.signUp(Member.from(signUpRequest));
        //fixme: from은 도메인 로직인데 여기서 사용하는 것도 레이어의 경계에 맞지 않게 사용하는게 아닐까요?
        //       이 변환을 도메인의 member.signUp 로직에 추가를 해야하는게 아닐까 생각이 들었습니다.
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_SIGN_UP_SUCCESS_MESSAGE, id));
    }

    @PutMapping
    public ResponseEntity<DefaultHTTPResponse<MemberResponse>> update(@Validated @RequestBody MemberService.Update updateRequest) {
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_UPDATE_SUCCESS_MESSAGE, MemberResponse.from(signUpService.update(Member.from(updateRequest)))));
    }

    @DeleteMapping
    public ResponseEntity<DefaultHTTPResponse<Void>> delete(@RequestParam String id) {
        signUpService.delete(id);
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_DELETE_SUCCESS_MESSAGE));
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class MemberResponse {

        private final String id;

        private final String email;

        private final String password;

        private final String name;

        private final String phone;

        private final String address;

        private final boolean isLocationServiceEnabled;

        private final long point;

        public static MemberResponse from(Member member) {
            return MemberResponse.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .password(member.getPassword())
                    .name(member.getName())
                    .phone(member.getPhone())
                    .address(member.getAddress())
                    .isLocationServiceEnabled(member.isLocationServiceEnabled())
                    .point(member.getPoint())
                    .build();
        }
    }

}
