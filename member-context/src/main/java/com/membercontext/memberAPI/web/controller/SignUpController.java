package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.aop.authentication.NoAuthentication;
import com.membercontext.memberAPI.application.service.SignUpSerivces.SignUpService;
import com.membercontext.memberAPI.domain.entity.member.Member;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/sign-in")
public class SignUpController {

    private final SignUpService signUpService;

    public static final String MEMBER_SIGN_UP_SUCCESS_MESSAGE = "회원가입 성공";
    public static final String MEMBER_UPDATE_SUCCESS_MESSAGE = "회원 수정 성공";
    public static final String MEMBER_DELETE_SUCCESS_MESSAGE = "회원 삭제 성공";

    @PostMapping
    @NoAuthentication
    public ResponseEntity<DefaultHTTPResponse<String>> signIn(@Validated @RequestBody SignUpRequest signUpRequest) {
        String id = signUpService.signUp(Member.from(signUpRequest));
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_SIGN_UP_SUCCESS_MESSAGE, id));
    }

    @PutMapping
    public ResponseEntity<DefaultHTTPResponse<MemberResponse>> update(@Validated @RequestBody UpdateRequest updateRequest) {
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_UPDATE_SUCCESS_MESSAGE, MemberResponse.from(signUpService.update(Member.from(updateRequest)))));
    }

    @DeleteMapping
    public ResponseEntity<DefaultHTTPResponse<Void>> delete(@RequestParam String id) {
        signUpService.delete(id);
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_DELETE_SUCCESS_MESSAGE));
    }

    public static final String EMAIL_FORMAT_VALIDATION_MESSAGE = "이메일 형식에 맞게 입력해주세요";
    public static final String EMAIL_VALIDATION_MESSAGE = "이메일을 입력해주세요";
    public static final String PASSWORD_VALIDATION_MESSAGE = "비밀번호를 입력해주세요";
    public static final String NAME_VALIDATION_MESSAGE = "이름을 입력해주세요";
    public static final String PHONE_VALIDATION_MESSAGE = "휴대폰 번호를 입력해주세요";
    public static final String LOCATION_VALIDATION_MESSAGE = "동을 입력해주세요";
    public static final String LOCATION_SERVICE_VALIDATION_MESSAGE = "위치 서비스 사용 여부를 입력해주세요";

    //fixme: Request와 Response는 final을 붙여서 불변으로 만드는게 좋을것 같은데 어떨까요?
    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpRequest {

        @Email(message = EMAIL_FORMAT_VALIDATION_MESSAGE)
        @NotBlank(message = EMAIL_VALIDATION_MESSAGE)
        private String email;

        @NotBlank(message = PASSWORD_VALIDATION_MESSAGE)
        private String password;

        @NotBlank(message = NAME_VALIDATION_MESSAGE)
        private String name;

        @NotBlank(message = PHONE_VALIDATION_MESSAGE)
        private String phone;

        @NotBlank(message = LOCATION_VALIDATION_MESSAGE)
        private String address;

        @NotNull(message = LOCATION_SERVICE_VALIDATION_MESSAGE)
        private Boolean isLocationServiceEnabled;

        private Long point;
    }


    @Getter
    @Builder(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class UpdateRequest {

        public static final String MEMBER_ID_VALIDATION_MESSAGE = "회원번호를 입력해주세요";
        public static final String POINT_UPDATE_VALIDATION_MESSAGE = "현재 포인트를 입력해주세요";

        @NotNull(message = MEMBER_ID_VALIDATION_MESSAGE)
        private String id;

        @Email(message = EMAIL_FORMAT_VALIDATION_MESSAGE)
        @NotBlank(message = EMAIL_VALIDATION_MESSAGE)
        private String email;

        @NotBlank(message = PASSWORD_VALIDATION_MESSAGE)
        private String password;

        @NotBlank(message = NAME_VALIDATION_MESSAGE)
        private String name;

        @NotBlank(message = PHONE_VALIDATION_MESSAGE)
        private String phone;

        @NotBlank(message = LOCATION_VALIDATION_MESSAGE)
        private String location;

        @NotNull(message = LOCATION_SERVICE_VALIDATION_MESSAGE)
        private Boolean isLocationServiceEnabled;

        @NotNull(message = POINT_UPDATE_VALIDATION_MESSAGE)
        private Long point;
    }

    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    public static class MemberResponse {

        private String id;

        private String email;

        private String password;

        private String name;

        private String phone;

        private String address;

        private Boolean isLocationServiceEnabled;

        private Long point;

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
