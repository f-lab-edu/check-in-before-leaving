package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.service.MemberInfo.MemberInfoService;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/info/member")
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    public static final String MEMBER_INFO_SUCCESS_MESSAGE = "회원 정보 조회 성공";

    @PostMapping
    public ResponseEntity<DefaultHTTPResponse<SignUpController.MemberResponse>> getMemberInfo(@RequestParam String id) {
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_INFO_SUCCESS_MESSAGE,
                SignUpController.MemberResponse.from(memberInfoService.getMemberInfo(id))));
    }

}
