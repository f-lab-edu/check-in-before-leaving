package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.service.MemberInfo.MemberInfoService;
import com.membercontext.memberAPI.web.controller.dto.DefaultHTTPResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberInfoController {

    private final MemberInfoService memberInfoService;

    public static final String MEMBER_INFO_SUCCESS_MESSAGE = "회원 정보 조회 성공";

    @PostMapping("/individual") //fixme: 이부분은 Get으로 id를 URL에 노출하지 않기위해 Post로 쓰려고 합니다.
    public ResponseEntity<DefaultHTTPResponse<SignUpController.MemberResponse>> getMemberInfo(@RequestParam String id) {
        return ResponseEntity.ok(new DefaultHTTPResponse<>(MEMBER_INFO_SUCCESS_MESSAGE,
                SignUpController.MemberResponse.from(memberInfoService.getMemberInfo(id))));
    }

}
