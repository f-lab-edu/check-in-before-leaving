package com.membercontext.memberAPI.application.service;


import com.membercontext.memberAPI.domain.entity.member.MemberService;
import com.membercontext.memberAPI.web.controller.TrackController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrackService {

    private final MemberService memberService;

    @Transactional
    public void startLocationTracking(TrackController.TrackRequest request, String memberId) {
        memberService.startLocationTracking(memberId, request);
    }

    @Transactional
    public void enablePushAlarm(String token, String memberId) {
        memberService.enablePushAlarm(token, memberId);
    }
}
