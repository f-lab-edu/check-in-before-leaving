package com.membercontext.memberAPI.web.controller;

import com.membercontext.memberAPI.application.service.MemberInfo.MemberInfoService;
import com.membercontext.memberAPI.web.dto.MemberDto;
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

    @PostMapping
    public ResponseEntity<MemberDto> getMemberInfo(@RequestParam Long id) {
        return ResponseEntity.ok(MemberDto.from(memberInfoService.getMemberInfo(id)));
    }
}
